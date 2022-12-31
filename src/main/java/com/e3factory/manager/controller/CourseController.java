package com.e3factory.manager.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e3factory.common.dto.Course;
import com.e3factory.common.form.CourseForm;
import com.e3factory.common.security.AuthEntity;
import com.e3factory.common.util.Utility;
import com.e3factory.manager.service.CourseService;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private MessageSource messageSource;

	@ModelAttribute
	public CourseForm createForm() {
		return new CourseForm();
	}

	/**
	 * 講座一覧表示
	 * @param model
	 * @param map
	 * @param principal
	 * @return
	 */
	@RequestMapping("/m/course/view")
	public String courseView(Model model, ModelMap map, @AuthenticationPrincipal AuthEntity principal) {
		//処理ロジック
		List<Course> list = courseService.courseView(principal);
		//セッション格納
		model.addAttribute("courseList", list);
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "manager/course/view";
	}

	/**
	 * 講座登録画面
	 * @return
	 */
	@RequestMapping("/m/course/insert")
	public String courseInsert() {
		//ビュー指定
		return "manager/course/insert";
	}

	/**
	 * 講座登録実行
	 * @param model
	 * @param form
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/m/course/doinsert")
	public String doCourseInsert(Model model, @Validated Course course, BindingResult result,
			RedirectAttributes redirectAttributes) {
		//バリデーションチェック(CourseFormに設定済)
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "manager/course/insert";
		}
		//処理ロジック
		courseService.doCourseInsert(course);
		String message = messageSource.getMessage("i.course.001", new String[] {}, Locale.getDefault());
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/m/course/view";
	}

	/**
	 * 講座情報更新画面表示
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/course/update/{id}")
	public String courseUpdate(Model model, @PathVariable String id) {
		//処理ロジック
		Course course = courseService.courseUpdate(id);
		model.addAttribute("courseForm", course);
		//ビュー指定
		return "manager/course/update";
	}

	/**
	 * 講座情報更新
	 * @param model
	 * @param form
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/m/course/doupdate")
	public String doCourseUpdate(Model model,  @Validated Course course,
			BindingResult result, RedirectAttributes redirectAttributes) {
		//バリデーションチェック(Courseに設定済)
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "manager/course/update";
		}
		//処理ロジック
		courseService.doCourseUpdate(course);
		String message = Utility.getMessage(messageSource,"i.course.002");
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/m/course/view";
	}

	/**
	 * 講座情報削除画面表示
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/course/delete/{id}")
	public String courseDelete(Model model, @PathVariable String id) {
		//処理ロジック
		Course course = courseService.courseDelete(id);
		model.addAttribute("course", course);
		//ビュー指定
		return "manager/course/delete";
	}

	/**
	 * 講座情報削除
	 * @param model
	 * @param course
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/m/course/dodelete")
	public String doCourseDelete(Model model, Course course, RedirectAttributes redirectAttributes) {
		//処理ロジック
		courseService.doCourseDelete(course.getId());
		//メッセージをリダイレクト先へ送るセッションに入れる
		String message = Utility.getMessage(messageSource, "i.course.003");
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/m/course/view";
	}
}
