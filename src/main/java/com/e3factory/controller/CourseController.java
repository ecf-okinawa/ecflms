package com.e3factory.controller;

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

import com.e3factory.dto.Course;
import com.e3factory.form.CourseForm;
import com.e3factory.security.AuthEntity;
import com.e3factory.service.CourseService;
import com.e3factory.util.Utility;

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

	//講座管理
	@RequestMapping("/course/view")
	public String courseView(Model model, ModelMap map, @AuthenticationPrincipal AuthEntity principal) {
		//講座情報取得
		List<String> auths = AuthEntity.getRoleNames(principal);
		List<Course> list = null;
		if( auths.contains("ROLE_ADMIN") ) {
			list = courseService.findAll();
		} else if( auths.contains("ROLE_TEACHER") ) {
			list = courseService.findByUserId(principal.getUsername());
		}
		//セッション格納
		model.addAttribute("courseList", list);
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "course/view";
	}

	@RequestMapping("/course/insert")
	public String courseInsert() {
		return "course/insert";
	}

	@RequestMapping("/course/doinsert")
	public String doCourseInsert(Model model, @Validated CourseForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		//バリデーションチェック(CourseFormに設定済)
		//日付形式チェック
		if( !Utility.checkDateFormat(form.getStartDate()) ) {
			result.reject("errors.invalid.startDate");
		}
		if( !Utility.checkDateFormat(form.getEndDate()) ) {
			result.reject("errors.invalid.endDate");
		}
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "/course/insert";
		}
		//登録処理
		courseService.insert(form);
		String message = messageSource.getMessage("i.course.001", new String[] {}, Locale.getDefault());
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/course/view";
	}

	@RequestMapping("/course/update/{id}")
	public String courseUpdate(Model model, @PathVariable String id) {
		//ユーザー情報取得
		Course course = courseService.findById(id);
		CourseForm form = new CourseForm();

		//formへコピー
		form.setId(course.getId());
		form.setName(course.getName());
		form.setUserId(course.getUserId());
		form.setStartDate(course.getStartDate());
		form.setEndDate(course.getEndDate());
		form.setCapacity(course.getCapacity());
		form.setLocation(course.getLocation());

		model.addAttribute("courseForm", form);

		return "course/update";
	}

	@RequestMapping("/course/doupdate")
	public String doCourseUpdate(Model model,  @Validated CourseForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		//バリデーションチェック(CourseFormに設定済)
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "/course/update";
		}
		//登録処理
		courseService.update(form);
		String message = Utility.getMessage(messageSource,"i.course.002");
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/course/view";
	}

	@RequestMapping("/course/delete/{id}")
	public String courseDelete(Model model, @PathVariable String id) {
		//ユーザー情報取得
		Course course = courseService.findById(id);

		model.addAttribute("course", course);

		return "course/delete";
	}

	@RequestMapping("/course/dodelete/{id}")
	public String doCourseDelete(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
		//削除処理
		courseService.delete(Integer.parseInt(id));
		//メッセージをリダイレクト先へ送るセッションに入れる
		String message = Utility.getMessage(messageSource, "i.course.003");
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/course/view";
	}
}
