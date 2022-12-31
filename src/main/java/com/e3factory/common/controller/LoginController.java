package com.e3factory.common.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3factory.common.security.AuthEntity;

@Controller
public class LoginController {
	/**
	 * ログインページ
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "common/login";
	}

	/**
	 * ホーム画面 認証情報に基づく振り分け
	 * @return
	 */
	@RequestMapping("/")
	public String home( @AuthenticationPrincipal AuthEntity principal ) {
		//認証情報が無い
		if( principal == null) {
			return "manager/login";
		}
		//認証情報がある
		List<String> roles = AuthEntity.getRoleNames(principal);
		if( roles.contains("ROLE_ADMIN") ) {
			return "redirect:/m/user/view";
		}
		if( roles.contains("ROLE_TEACHER") ) {
			return "redirect:/m/course/view";
		}
		//一般ユーザー
		return "redirect:/u/home/" + principal.getUsername() + "/attended";
	}

	public String logout() {
		return "manager/login";
	}
}
