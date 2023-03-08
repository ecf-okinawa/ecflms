package com.e3factory.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e3factory.common.dto.Attendance;
import com.e3factory.common.security.AuthEntity;
import com.e3factory.common.util.AppMessage;
import com.e3factory.common.util.MessageUtil;
import com.e3factory.user.service.AttendanceService;

@Controller
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private MessageUtil msgUtil;

	//受講登録画面表示
	@RequestMapping("/u/attendance/insert/{id}")
	public String attendanceInsert(Model model, @PathVariable int id) {
		//画面表示情報の取得
		attendanceService.attendanceInsert(model, id);
		//ビュー指定
		return "user/attendance/insert";
	}

	//受講登録
	@RequestMapping("/u/attendance/doinsert")
	public String doAttendanceInsert(Model model, @ModelAttribute Attendance attendance,
			@AuthenticationPrincipal AuthEntity principal, RedirectAttributes redirectAttributes) {
		//受講登録を実行
		attendanceService.doAttendanceInsert(model, attendance.getCourseId(), principal.getUsername());
		//結果メッセージの設定
		AppMessage message =  msgUtil.getAppMessage("atnd.i01", attendance.getCourseName());
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//画面遷移
		return "redirect:/u/home/" + principal.getUsername() + "/attended";
	}
}
