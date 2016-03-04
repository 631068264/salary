package com.web.realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.web.entity.Role;
import com.web.entity.User;
import com.web.service.UserService;

@Repository("realmService")
public class RealmService implements UserDetailsService {

	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
		User user = userService.getAccount(userID);

		UserDetails userDetails = null;
		if (user == null) {
			throw new AuthenticationServiceException("用户不存在");
		}

		userDetails = new org.springframework.security.core.userdetails.User(user.getUserID(),
				user.getPassword(), true, true, true, true, findUserAuthorities(user));

		return userDetails;
	}

	/**
	 * 获取用户权限
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Collection<? extends GrantedAuthority> findUserAuthorities(User user) {
		List<GrantedAuthority> autthorities = new ArrayList<GrantedAuthority>();
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			autthorities.add(new GrantedAuthorityImpl(role.getRoleCode()));
		}
		return autthorities;
	}

}
