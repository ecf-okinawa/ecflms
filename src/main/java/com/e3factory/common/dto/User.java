package com.e3factory.common.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	//ユーザーID
	@NotEmpty(message="{valid.w01}")
	@Size (min = 4, max = 20, message="{valid.w03}")
	private String id;

	//パスワード
	@NotEmpty(message="{valid.w01}")
	@Size (min = 4, max = 20, message="{valid.w03}")
	private String password;

	//権限
	@NotEmpty(message="{valid.w01}")
	private String privilege;

	//ユーザー名
	@NotEmpty(message="{valid.w01}")
	@Size (min = 1, max = 20, message="{valid.w03}")
	private String name;
}
