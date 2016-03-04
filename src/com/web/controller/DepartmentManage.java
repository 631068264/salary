package com.web.controller;

import java.io.BufferedOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.base.BaseAction;
import com.web.entity.Department;
import com.web.entity.Salary;
import com.web.entity.User;
import com.web.entity.viewhelp.USList;
import com.web.page.HqlHelper;
import com.web.page.Pager;
import com.web.utils.DateUtils;

@Controller
@RequestMapping("/departmentManage")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentManage extends BaseAction {

	/**
	 * 获取所有部门分页信息JSON
	 * 
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/getDepartmentList")
	@ResponseBody
	public Pager list(Integer currentPage) {
		return new HqlHelper(Department.class, "u").pushToJSON(currentPage, departmentService);
	}

	/**
	 * 部门列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String list() {
		return "departmentManage/list";
	}

	/**
	 * 删除部门
	 * 
	 * @param did
	 * @return
	 */
	@RequestMapping(value = "/{did}/del")
	public String del(@PathVariable Long did) {
		departmentService.delete(did);
		return "redirect:/departmentManage/list";
	}

	/**
	 * 增加部门界面
	 * 
	 * @return
	 */
	@RequestMapping("/addUI")
	public String addUI() {

		return "departmentManage/addUI";
	}

	/**
	 * 获取部门内容JSON
	 * 
	 * @return
	 */
	@RequestMapping("{did}/getDepartment")
	@ResponseBody
	public Department editUI(@PathVariable Long did) {

		return departmentService.getById(did);
	}

	/**
	 * 修改部门界面
	 * 
	 * @return
	 */
	@RequestMapping("{did}/editUI")
	public String editUI() {

		return "departmentManage/editUI";
	}

	/**
	 * 添加部门
	 * 
	 * @param department
	 * @return
	 */
	@RequestMapping("/add")
	public String addDepartment(Department department) {
		departmentService.save(department);
		return "redirect:/departmentManage/list";
	}

	/**
	 * 修改部门
	 * 
	 * @param department
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Department department, Long did) {
		Department d = departmentService.getById(did);
		d.setName(department.getName());
		d.setDescription(department.getDescription());
		departmentService.update(d);
		return "redirect:/departmentManage/list";
	}

	/**
	 * 修改工资表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/{did}/{sid}/edits")
	public String edit(Salary salary, @PathVariable Long sid, @PathVariable Long did) {

		Salary s = salaryService.getById(sid);

		s.setDeduct(salary.getDeduct());
		s.setBasePay(salary.getBasePay());
		s.setWelfare(salary.getWelfare());
		s.setBonus(salary.getBonus());

		salaryService.update(s);

		return "redirect:/departmentManage/" + did + "/getSalaryUI";
	}

	/**
	 * 首页获取部门全员工资JSON
	 * 
	 * @param did
	 * @return
	 */
	@RequestMapping("/{did}/getDepartmentInfo")
	@ResponseBody
	public Map<String, Object> getSalary(@PathVariable Long did) {
		Map<String, Object> map = new HashMap<String, Object>();

		Department department = departmentService.getById(did);
		Double avg = salaryService.avgSalary(did);

		map.put("department", department);
		map.put("avg", avg);
		return map;
	}

	/**
	 * 首页获取部门全员工资JSON
	 * 
	 * @param did
	 * @return
	 */
	@RequestMapping("/{did}/getSalary")
	@ResponseBody
	public Pager getSalary(@PathVariable Long did, Integer currentPage) {

		Pager pager = new HqlHelper(User.class, "u").addCondition("u.department.id = ?", did)
				.pushToJSON(currentPage, userService);
		List<USList> usLists = new ArrayList<USList>();

		@SuppressWarnings("unchecked")
		List<User> users = pager.getRecordList();
		for (User user : users) {
			if (user != null) {
				USList usList = new USList();
				usList.setUser(user);
				usList.setSalary(user.getSalary());
				usLists.add(usList);
			}
		}

		pager.setRecordList(usLists);
		return pager;
	}

	/**
	 * 获取部门平均工资JSON
	 * 
	 * @param did
	 * @return
	 */
	@RequestMapping("/{did}/getAvg")
	@ResponseBody
	public Double getAvg(@PathVariable Long did) {

		return salaryService.avgSalary(did);
	}

	/**
	 * 部门工资界面
	 * 
	 * @param did
	 * @param model
	 * @return
	 */
	@RequestMapping("/{did}/getSalaryUI")
	public String getSalaryUI(@PathVariable Long did, Model model) {

		return "/departmentManage/getSalaryUI";
	}

	/**
	 * 打印部门工资表
	 * 
	 * @param did
	 * @param departmentName
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{did}/print/{departmentName}")
	public ModelAndView print(@PathVariable Long did, @PathVariable String departmentName,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		BufferedOutputStream out = null;
		try {

			String fileName = DateUtils.getDateYM() + URLDecoder.decode(departmentName, "UTF-8")
					+ "工资总表";
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

			List<User> userList = userService.getSalary(did);

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
