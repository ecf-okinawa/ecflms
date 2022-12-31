package com.e3factory.manager.controller;

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

import com.e3factory.common.dto.User;
import com.e3factory.common.form.UserForm;
import com.e3factory.common.util.Utility;
import com.e3factory.manager.service.UserService;

/**
 * ユーザー管理
 * @author ecf_pc
 *
 */
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

	/**
	 * ユーザー一覧表示
	 * @param model
	 * @param map
	 * @return
	 */
	@RequestMapping("/m/user/view")
	public String userView(Model model, ModelMap map) {
		//ユーザー情報取得
		userService.userView(model);
		//リダイレクトセッションからメッセージを取得
		model.addAttribute("message", map.get("message"));
		//ビュー指定
		return "manager/user/view";
	}

	/**
	 * ユーザー登録画面表示
	 * @return
	 */
	@RequestMapping("/m/user/insert")
	public String userInsert() {
		//ビュー表示のみ
		return "manager/user/insert";
	}

	/**
	 * ユーザー登録実行
	 * @param model
	 * @param form
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/m/user/doinsert")
	public String doUserInsert(Model model, @Validated UserForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		//バリデーションチェック(Userに設定済)
		//パスワード確認チェック
		if( !form.getPassword().equals(form.getPasswordConfirm()) ) {
			result.reject("errors.invalid.paswordConfirm");
		}
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "manager/user/insert";
		}
		//登録処理
		userService.doUserInsert(form);
		//結果メッセージの設定
		String message = messageSource.getMessage("i.user.001", new String[] {}, Locale.getDefault());
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/m/user/view";
	}

	/**
	 * ユーザー情報更新画面表示
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/user/update/{id}")
	public String userUpdate(Model model, @PathVariable String id) {
		//ユーザー情報取得
		userService.userUpdate(model, id);
		return "manager/user/update";
	}

	/**
	 * ユーザー更新実行
	 * @param model
	 * @param form
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/m/user/doupdate")
	public String doUserUpdate(Model model,  @Validated UserForm form,
			BindingResult result, RedirectAttributes redirectAttributes) {
		//バリデーションチェック(UserFormに設定済)
		//パスワード確認チェック
		if( !form.getPassword().equals(form.getPasswordConfirm()) ) {
			result.reject("errors.invalid.paswordConfirm");
		}
		if( result.hasErrors() ) {
			//バリデーションエラー
			return "manager/user/update";
		}
		//更新処理
		userService.doUserUpdate(form);
		//結果メッセージの設定
		String message = Utility.getMessage(messageSource,"i.user.002");
		//メッセージをリダイレクト先へ送るセッションに入れる
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/m/user/view";
	}

	/**
	 * ユーザー削除画面表示
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/m/user/delete/{id}")
	public String userDelete(Model model, @PathVariable String id) {
		//ユーザー情報取得
		User user = userService.userDelete(id);
		model.addAttribute("user", user);

		return "manager/user/delete";
	}

	/**
	 * ユーザー削除実行
	 * @param model
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/m/user/dodelete")
	public String doUserDelete(Model model, User user, RedirectAttributes redirectAttributes) {
		//削除処理
		userService.doUserDelete(user.getId());
		//メッセージをリダイレクト先へ送るセッションに入れる
		String message = Utility.getMessage(messageSource, "i.user.003");
		redirectAttributes.addFlashAttribute("message", message);
		//一覧へリダイレクト
		return "redirect:/m/user/view";
	}
}