package com.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.web.utils.StringUtils;

public class ValidateCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private boolean postOnly = true;
	private boolean allowEmptyValidateCode = false;
	private String sessionvalidateCodeField = "imageCode";// 来自服务器
	private String validateCodeParameter = "code";// 来自表单
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	public static final String VALIDATE_CODE_FAILED_MSG_KEY = "validateCode.notEquals";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: "
					+ request.getMethod());
		}

		String username = StringUtils.trimToEmpty(obtainUsername(request));
		String password = obtainPassword(request);
		if (password == null) {
			password = StringUtils.EMPTY;
		}

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession(false);

		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY,
					TextEscapeUtils.escapeEntities(username));
		}
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		// check validate code
		if (!isAllowEmptyValidateCode())
			checkValidateCode(request);
		return this.getAuthenticationManager().authenticate(authRequest);

	}

	protected void checkValidateCode(HttpServletRequest request) {
		String sessionValidateCode = obtainSessionValidateCode(request);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter)
				|| !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException("验证码错误！");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		return request.getParameter(validateCodeParameter);// 来自表单
	}

	protected String obtainSessionValidateCode(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(sessionvalidateCodeField);// 来自服务器
		return null == obj ? "" : obj.toString();
	}

	// ===========================================================================================
	// getset方法
	public boolean isPostOnly() {
		return postOnly;
	}

	public void setPostOnly(boolean postOnly) {
		super.setPostOnly(postOnly);
	}

	public boolean isAllowEmptyValidateCode() {
		return allowEmptyValidateCode;
	}

	public void setAllowEmptyValidateCode(boolean allowEmptyValidateCode) {
		this.allowEmptyValidateCode = allowEmptyValidateCode;
	}

	public String getSessionvalidateCodeField() {
		return sessionvalidateCodeField;
	}

	public void setSessionvalidateCodeField(String sessionvalidateCodeField) {
		this.sessionvalidateCodeField = sessionvalidateCodeField;
	}

}
