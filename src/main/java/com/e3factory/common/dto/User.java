package com.e3factory.common.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	//ユーザーID
	@NotEmpty
	@Size (min = 4, max = 20)
	private String id;

	//パスワード
	@NotEmpty
	@Size (min = 4, max = 20)
	private String password;

	//権限
	@NotEmpty
	private String privilege;

	//ユーザー名
	@NotEmpty
	@Size (min = 1, max = 20)
	private String name;
}
