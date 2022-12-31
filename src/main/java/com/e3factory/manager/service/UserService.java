package com.e3factory.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.e3factory.common.dto.User;
import com.e3factory.common.form.UserForm;
import com.e3factory.common.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー一覧表示処理
	 * @param model
	 */
	public void userView(Model model) {
		//DBからユーザー情報取得
		List<User> list = userRepository.findAll();
		//セッション格納
		model.addAttribute("userList", list);
	}

	/**
	 * ユーザー情報更新画面表示処理
	 * @param model
	 * @param id
	 */
	public void userUpdate(Model model, String id) {
		User user = userRepository.findById(id);
		//フォームDTOへコピー
		UserForm form = new UserForm();
		form.setId(user.getId());
		form.setName(user.getName());
		form.setPrivilege(user.getPrivilege());
		model.addAttribute("userForm", form);
	}

	/**
	 * ユーザー登録実行処理
	 * @param form
	 */
	@Transactional
	public void doUserInsert(UserForm user) {
		//DBへ登録
		userRepository.insert(user);
	}

	/**
	 * ユーザー更新実行処理
	 * @param form
	 */
	@Transactional
	public void doUserUpdate(UserForm form) {
		//DB更新
		userRepository.update(form);
	}

	/**
	 * ユーザー情報削除画面表示
	 * @param id
	 */
	public User userDelete(String id) {
		return userRepository.findById(id);
	}

	/**
	 * ユーザー削除実行
	 * @param id
	 */
	@Transactional
	public void doUserDelete(String id) {
		userRepository.delete(id);
	}
}
