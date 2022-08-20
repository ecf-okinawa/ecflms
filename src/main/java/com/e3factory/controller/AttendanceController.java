package com.e3factory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e3factory.dto.Attendance;
import com.e3factory.dto.Course;
import com.e3factory.form.AttendanceForm;
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
		List<AttendanceForm> attendanceList = attendanceService.findByCourseId(id);
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

	//評価画面表示
	@RequestMapping("/attendance/evaluate/update/{id}")
	public String viewEvaluate(Model model, @PathVariable int id) {
		AttendanceForm form = attendanceService.viewEvaluate(id);
		model.addAttribute("attendanceForm", form);
		//ビュー指定
		return "attendance/evaluate";
	}

	//評価更新処理
	@RequestMapping("/attendance/evaluate/doupdate")
	public String doEvaluate(Model model, @Validated AttendanceForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		//バリデーションチェック(EvaluateFormに設定済)
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "/user/insert";
		}
		//更新処理
		attendanceService.updateEvaluate(form);
		//メッセージ登録
		String message = Utility.getMessage(messageSource, "i.attendance.002", form.getUserName()) ;
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/attendance/view/" + form.getCourseId();
	}
}
