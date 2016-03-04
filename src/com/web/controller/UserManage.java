package com.web.controller;

import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;

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
import com.web.utils.DateUtils;
import com.web.utils.Md5Utils;

@Controller
@RequestMapping("/userManage")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('COMMON')")
public class UserManage extends BaseAction {

	/**
	 * 验证密码JSON
	 * 
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/password")
	@ResponseBody
	public boolean password(String password, HttpSession session) {
		User user = (User) session.getAttribute("user");
		user = userService.getAccount(user.getUserID());

		boolean flag = false;
		if (user.getPassword().equals(Md5Utils.md5(password, user.getUserID()))) {
			flag = true;// 验证成功
		}
		return flag;
	}

	/**
	 * 获取个信息JSON
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public Map<String, Object> getUser(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = (User) session.getAttribute("user");

		map.put("user", user);
		map.put("department", user.getDepartment());
		return map;
	}

	/**
	 * 查看个人信息
	 */
	@RequestMapping(value = "/editUI")
	public String editUI() {
		return "userManage/editUI";
	}

	/**
	 * 修改个人信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(User user, Long uid, HttpSession session) {
		User u = userService.getById(uid);

		u.setUserName(user.getUserName());
		u.setUserID(user.getUserID());

		userService.update(u);

		session.setAttribute("user", u);
		return "redirect:/userManage/editUI";
	}

	/**
	 * 修改密码界面
	 * 
	 * @param uid
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/editpUI")
	public String editpUI() {
		return "userManage/editpUI";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/editp")
	public String editp(String password, String newpassword, Long uid, HttpSession session) {
		User user = userService.getById(uid);

		user.setPassword(Md5Utils.md5(newpassword, user.getUserID()));
		userService.update(user);

		session.setAttribute("error", "修改密码成功");

		return "redirect:/userManage/editpUI";
	}

	@RequestMapping(value = "/getSalary")
	@ResponseBody
	public Map<String, Object> user(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");

		map.put("user", user);
		map.put("salary", user.getSalary());
		return map;
	}

	/**
	 * 获取个人工资表
	 * 
	 * @param uid
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getSalaryUI")
	public String getSalary() {
		return "userManage/salaryList";
	}

	/**
	 * 打印工资表
	 * 
	 * @param uid
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{uid}/print")
	public ModelAndView print(@PathVariable Long uid, HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		BufferedOutputStream out = null;
		try {
			String fileName = DateUtils.getDateYM() + "个人工资表";
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

			String[] titles = { "总收入", "实际收入", "罚金", "基本工资", "福利", "奖金" };

			// 表头
			for (int i = 0; i < titles.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(titles[i]);
				sheet.setColumnWidth(i, 8000);
			}
			// 数据

			Salary salary = userService.getSalaryList(uid);

			row = sheet.createRow(1);
			row.createCell(0).setCellValue(salary.getTotal());
			row.createCell(1).setCellValue(salary.getPay());
			row.createCell(2).setCellValue(salary.getDeduct());
			row.createCell(3).setCellValue(salary.getBasePay());
			row.createCell(4).setCellValue(salary.getWelfare());
			row.createCell(5).setCellValue(salary.getBonus());

			// ByteArrayOutputStream boas = new ByteArrayOutputStream();
			out = new BufferedOutputStream(response.getOutputStream());
			wb.write(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;

	}

}
