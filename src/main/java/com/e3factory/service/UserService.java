package com.e3factory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e3factory.dto.User;
import com.e3factory.form.UserForm;
import com.e3factory.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll(){
		//DBからユーザー情報取得
		List<User> list = userRepository.findAll();
		return list;
	}

	public User findById(String id) {
		return userRepository.findById(id);
	}

	@Transactional
	public void insert(UserForm form) {
		//FormからDtoへコピー
		User user = new User();
		user.setId(form.getUserid());
		user.setName(form.getName());
		user.setPassword(form.getPassword());
		user.setPrivilege(form.getPrivilege());

		//DBへ登録
		userRepository.insert(user);
	}

	@Transactional
	public void update(UserForm form) {
		//FormからDtoへコピー
		User user = new User();
		user.setId(form.getUserid());
		user.setName(form.getName());
		user.setPassword(form.getPassword());
		user.setPrivilege(form.getPrivilege());

		//DB更新
		userRepository.update(user);
	}

	@Transactional
	public void delete(String id) {
		userRepository.delete(id);
	}
}
