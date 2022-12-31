package com.e3factory.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.e3factory.common.dto.User;

public class AuthEntity implements UserDetails{

	private User user;

	public AuthEntity(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_" + user.getPrivilege());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static List<String> getRoleNames(AuthEntity entity){
		Collection<? extends GrantedAuthority> authorities = entity.getAuthorities();
		ArrayList<String> list = new ArrayList<String>();
		for( GrantedAuthority auth : authorities ) {
			list.add(auth.getAuthority());
		}
		return list;
	}

}
