package com.e3factory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e3factory.dto.Attendance;
import com.e3factory.dto.Course;
import com.e3factory.service.AttendanceService;
import com.e3factory.service.CourseService;
import com.e3factory.util.Utility;

@Controller
public class AttendanceController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private MessageSource messageSource;

	//受講情報一覧
	@RequestMapping("/attendance/view/{id}")
	public String courseView(Model model, ModelMap map, @PathVariable String id) {
		//講座情報取得
		Course course = courseService.findById(id);
		//講座受講者情報取得
		List<Attendance> attendanceList = attendanceService.findByCourseId(id);
		//セッション格納
		model.addAttribute("course",course);
		model.addAttribute("attendanceList", attendanceList);
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "attendance/view";
	}

	//受講削除
	@RequestMapping("/attendance/delete/{id}")
	public String attendanceDelete(Model model, @PathVariable String id) {
		//ユーザー情報取得
		Attendance attendance = attendanceService.findById(id);

		model.addAttribute("attendance", attendance);

		return "attendance/delete";
	}

	//受講削除実行
	@RequestMapping("/attendance/dodelete/{id}/{courseId}")
	public String doAttendanceDelete(Model model, @PathVariable String id, @PathVariable String courseId,
			RedirectAttributes redirectAttributes) {
		//削除処理
		attendanceService.delete(Integer.parseInt(id));
		//メッセージをリダイレクト先へ送るセッションに入れる
		String message = Utility.getMessage(messageSource, "i.attendance.003");
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/attendance/view/" + courseId;
	}
}
