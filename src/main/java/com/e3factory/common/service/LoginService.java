package com.e3factory.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e3factory.common.dto.User;
import com.e3factory.common.repository.UserRepository;
import com.e3factory.common.security.AuthEntity;

@Service
public class LoginService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("test");
		User user = userRepository.findById(username);
		if(user == null) {
			throw new UsernameNotFoundException(username + " is not found");
		}
		return new AuthEntity(user);
	}
}
