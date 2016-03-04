package com.web.entity.viewhelp;

import com.web.entity.Department;
import com.web.entity.Role;
import com.web.entity.User;

public class UDRList {
	private User user;
	private Department department;
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
