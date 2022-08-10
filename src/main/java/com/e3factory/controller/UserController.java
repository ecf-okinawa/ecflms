package com.e3factory.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e3factory.dto.User;
import com.e3factory.form.UserForm;
import com.e3factory.service.UserService;
import com.e3factory.util.Utility;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messageSource;

	@ModelAttribute
	public UserForm createForm() {
		return new UserForm();
	}

	//ユーザー管理
	@RequestMapping("/user/view")
	public String userView(Model model, ModelMap map) {
		//ユーザー情報取得
		List<User> list = userService.findAll();
		//セッション格納
		model.addAttribute("userList", list);
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "user/view";
	}

	@RequestMapping("/user/insert")
	public String userInsert() {
		return "user/insert";
	}

	@RequestMapping("/user/doinsert")
	public String doUserInsert(Model model, @Validated UserForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		//バリデーションチェック(UserFormに設定済)
		//パスワード確認チェック
		if( !form.getPassword().equals(form.getPasswordConfirm()) ) {
			result.reject("errors.invalid.paswordConfirm");
		}
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "/user/insert";
		}
		//登録処理
		userService.insert(form);
		String message = messageSource.getMessage("i.user.001", new String[] {}, Locale.getDefault());
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/user/view";
	}

	@RequestMapping("/user/update/{id}")
	public String userUpdate(Model model, @PathVariable String id) {
		//ユーザー情報取得
		User user = userService.findById(id);
		UserForm form = new UserForm();

		//formへコピー
		form.setUserid(user.getId());
		form.setName(user.getName());
		form.setPrivilege(user.getPrivilege());

		model.addAttribute("userForm", form);

		return "user/update";
	}

	@RequestMapping("/user/doupdate")
	public String doUserUpdate(Model model,  @Validated UserForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		//バリデーションチェック(UserFormに設定済)
		//パスワード確認チェック
		if( !form.getPassword().equals(form.getPasswordConfirm()) ) {
			result.reject("errors.invalid.paswordConfirm");
		}
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "/user/update";
		}
		//登録処理
		userService.update(form);
		String message = Utility.getMessage(messageSource,"i.user.002");
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/user/view";
	}

	@RequestMapping("/user/delete/{id}")
	public String userDelete(Model model, @PathVariable String id) {
		//ユーザー情報取得
		User user = userService.findById(id);

		model.addAttribute("user", user);

		return "user/delete";
	}

	@RequestMapping("/user/dodelete/{id}")
	public String doUserDelete(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
		//削除処理
		userService.delete(id);
		//メッセージをリダイレクト先へ送るセッションに入れる
		String message = Utility.getMessage(messageSource, "i.user.003");
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/user/view";
	}
}