package com.web.controller;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.base.BaseAction;
import com.web.entity.Salary;
import com.web.entity.User;
import com.web.entity.viewhelp.USList;
import com.web.page.HqlHelper;
import com.web.page.Pager;
import com.web.utils.DateUtils;

/**
 * 部门工资管理
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/salaryManage")
@PreAuthorize("hasRole('MANAGER')")
public class SalaryManage extends BaseAction {
	/**
	 * 获取平均工资JSON
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/getDepartmentInfo")
	@ResponseBody
	public Double getSalary(HttpSession session) {
		User user = (User) session.getAttribute("user");

		return salaryService.avgSalary(user.getDepartment().getId());
	}

	/**
	 * 获取平均工资JSON
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/getSalary")
	@ResponseBody
	public Pager getSalary(Integer currentPage, HttpSession session) {
		User u = (User) session.getAttribute("user");

		Pager pager = new HqlHelper(User.class, "u")
				.addCondition("u.department.id = ?", u.getDepartment().getId())
				.addCondition("u.id != ?", u.getId()).pushToJSON(currentPage, userService);

		@SuppressWarnings("unchecked")
		List<User> users = pager.getRecordList();
		List<USList> usLists = new ArrayList<USList>();

		for (User user : users) {
			if (user != null) {
				USList usList = new USList();
				usList.setSalary(user.getSalary());
				usList.setUser(user);
				usLists.add(usList);
			}
		}

		pager.setRecordList(usLists);
		return pager;
	}

	/**
	 * 获取部门工资
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSalaryUI")
	public String getSalaryUI() {

		return "salaryManage/getSalaryUI";

	}

	/**
	 * 修改工资表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/{sid}/edit")
	public String edit(Salary salary, @PathVariable Long sid) {

		Salary s = salaryService.getById(sid);

		s.setDeduct(salary.getDeduct());
		s.setBasePay(salary.getBasePay());
		s.setWelfare(salary.getWelfare());
		s.setBonus(salary.getBonus());

		salaryService.update(s);
		return "redirect:/salaryManage/getSalaryUI";
	}

	@RequestMapping(value = "/print")
	public ModelAndView print(HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		BufferedOutputStream out = null;
		try {
			String fileName = DateUtils.getDateYM() + "部门工资表";
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();

			HSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);

			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			String[] titles = { "员工编号", "员工姓名", "总收入", "实际收入", "罚金", "基本工资", "福利", "奖金" };

			// 表头
			for (int i = 0; i < titles.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(titles[i]);
				sheet.setColumnWidth(i, 8000);
			}
			// 数据

			User u = (User) request.getSession().getAttribute("user");
			List<User> userList = userService.getDepartmentSalary(u);

			int i = 0;
			for (User user : userList) {
				i++;
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(user.getUserID());
				row.createCell(1).setCellValue(user.getUserName());

				row.createCell(2).setCellValue(user.getSalary().getTotal());
				row.createCell(3).setCellValue(user.getSalary().getPay());
				row.createCell(4).setCellValue(user.getSalary().getDeduct());
				row.createCell(5).setCellValue(user.getSalary().getBasePay());
				row.createCell(6).setCellValue(user.getSalary().getWelfare());
				row.createCell(7).setCellValue(user.getSalary().getBonus());
			}

			out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;

	}

}
