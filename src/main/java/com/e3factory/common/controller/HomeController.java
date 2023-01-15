package com.e3factory.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3factory.common.form.HomeInfo;
import com.e3factory.common.service.HomeService;

@Controller
public class HomeController {
	@Autowired
	private HomeService homeService;

	//ホーム画面表示
	@RequestMapping({"/u/home","/u/home/{userId}","/u/home/{userId}/{attended}"})
	public String mainView(Model model, ModelMap map,
			@PathVariable(name="userId", required = false) String userId,
			@PathVariable(name="attended", required = false) String attended) {
		//講座情報取得
		List<HomeInfo> list = homeService.getHomeInfo(userId, attended != null);
		//セッション格納
		model.addAttribute("homeInfoList",list);
		//登録済み表示かそうでないか
		if(attended != null) {
			model.addAttribute("attended", attended != null);
		}
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "user/home";
	}

	//受講登録画面表示
	@RequestMapping("/u/attend/{courseId}")
	public String attendView(Model model, @PathVariable int courseId) {
		//講座情報取得
		HomeInfo info = homeService.getHomeInfo(courseId);
		model.addAttribute("homeInfo", info);
		//ビュー指定
		return "user/attendance";
	}
}
