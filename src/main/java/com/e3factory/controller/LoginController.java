package com.e3factory.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3factory.security.AuthEntity;

@Controller
public class LoginController {
	/**
	 * ログインページ
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "login/login";
	}

	/**
	 * ホーム画面 認証情報に基づく振り分け
	 * @return
	 */
	@RequestMapping("/")
	public String home( @AuthenticationPrincipal AuthEntity principal ) {
		//認証情報が無い
		if( principal == null) {
			return "login/login";
		}
		//認証情報がある
		List<String> roles = AuthEntity.getRoleNames(principal);
		if( roles.contains("ROLE_ADMIN") ) {
			return "redirect:/user/view";
		}
		if( roles.contains("ROLE_TEACHER") ) {
			return "redirect:/course/view";
		}
		//一般ユーザー
		return "redirect:/common/home/" + principal.getUsername() + "/attended";
	}

	public String logout() {
		return "login/login";
	}
}
