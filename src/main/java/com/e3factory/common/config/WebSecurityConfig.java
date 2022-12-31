package com.e3factory.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//認可設定(一旦全許可)
		http
		.authorizeRequests()
		//全権限で許可
		.antMatchers("/u/**","/css/**","/js/**","/img/**","/").permitAll()
		//TEACHER権限のアクセス
		.antMatchers("/m/course/**","/m/attendance/view/**","/m/attendance/evaluate/**").hasAnyRole("ADMIN","TEACHER")
		//ADMIN権限のユーザーのみが管理者画面(/**)にアクセスできる
		.antMatchers("/**").hasRole("ADMIN")
		//その他の閲覧はダメ
		.anyRequest().denyAll();

		//簡易化のためCSRF対策false
		http.csrf().disable();

		//ログイン設定
		http
		.formLogin()
		.loginPage("/login")
		.permitAll();

		//ログアウト設定
		http.logout().permitAll();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}

			@Override
			public String encode(CharSequence rawPassword) {
				return (String)rawPassword;
			}
		};
	}
}
