package com.web.install;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.web.entity.Privilege;
import com.web.entity.Role;
import com.web.entity.User;

@Component
public class Installer {

	@Resource
	private EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		System.out.println("开始安装  系统初始化");
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();

		System.out.println("安装成功");
	}

	private void install() {
		EntityManager em = entityManagerFactory.createEntityManager();
		// ====================================================
		// 角色初始化
		Role role1 = new Role();
		role1.setDescription("管理员");
		role1.setRoleCode("ADMIN");
		Role role2 = new Role();
		role2.setDescription("经理");
		role2.setRoleCode("MANAGER");
		Role role3 = new Role();
		role3.setDescription("职工");
		role3.setRoleCode("COMMON");
		em.persist(role1);
		em.persist(role2);
		em.persist(role3);
		// ===================================================
		// 超级管理员
		User user = new User();
		user.setUserID("admin");
		user.setPassword("admin");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		user.setRoles(roles);
		em.persist(user);
		// ======================================================
		// 资源初始化
		Privilege menu, menu1, menu2, menu3, menu4;
		// String name, String url, Privilege parent

		// COMMON=================================================
		menu = new Privilege("个人管理", null, null);
		menu1 = new Privilege("个人信息", "/userManage/editUI", menu);
		menu2 = new Privilege("修改密码", "/userManage/editpUI", menu);
		menu3 = new Privilege("个人工资表", "/userManage/getSalary", menu);

		em.persist(menu);
		em.persist(menu1);
		em.persist(menu2);
		em.persist(menu3);
		// MANAGER=============================================
		menu = new Privilege("员工管理", null, null);
		menu1 = new Privilege("部门工资管理", "/salaryManage/getSalaryUI", menu);

		em.persist(menu);
		em.persist(menu1);
		// ADMIN=================================================
		menu = new Privilege("系统管理", null, null);
		menu1 = new Privilege("部门管理", "/departmentManage/list", menu);
		menu2 = new Privilege("员工管理", "/adminManage/list", menu);

		em.persist(menu);
		em.persist(menu1);
		em.persist(menu2);

	}
}
