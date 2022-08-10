package com.e3factory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e3factory.form.HomeInfo;
import com.e3factory.security.AuthEntity;
import com.e3factory.service.AttendanceService;
import com.e3factory.service.HomeService;
import com.e3factory.util.AppMessage;

@Controller
public class HomeController {
	@Autowired
	private HomeService homeService;
	@Autowired
	private AttendanceService attendanceService;

	//ホーム画面表示
	@RequestMapping({"/common/home","/common/home/{userId}","/common/home/{userId}/{attended}"})
	public String mainView(Model model, ModelMap map,
			@PathVariable(name="userId", required = false) String userId,
			@PathVariable(name="attended", required = false) String attended) {
		//講座情報取得
		List<HomeInfo> list = homeService.getHomeInfo(userId, attended != null);
		//セッション格納
		model.addAttribute("homeInfoList",list);
		//リダイレクト元からメッセージあればセット
		model.addAttribute("messages", map.get("messages"));
		//登録済み表示かそうでないか
		if(attended != null) {
			model.addAttribute("attended", attended != null);
		}
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "common/home";
	}

	//受講登録画面表示
	@RequestMapping("/common/attend/{courseId}")
	public String attendView(Model model, @PathVariable int courseId) {
		//講座情報取得
		HomeInfo info = homeService.getHomeInfo(courseId);
		model.addAttribute("homeInfo", info);
		//ビュー指定
		return "common/attendance";
	}

	//受講登録処理
	@RequestMapping("/common/doattend/{courseId}/{userId}")
	public String doAttend(Model model, @PathVariable int courseId, @PathVariable String userId,
			@AuthenticationPrincipal AuthEntity principal, RedirectAttributes redirectAttributes) {
		//受講登録
		List<AppMessage> messages = attendanceService.attend(courseId, userId);
		//セット
		redirectAttributes.addFlashAttribute("messages", messages);
		//ビュー指定
		return "redirect:/common/home/" + userId;
	}
}
