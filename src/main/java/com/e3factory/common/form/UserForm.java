package com.e3factory.common.form;

import javax.validation.constraints.NotEmpty;

import com.e3factory.common.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm extends User{
	@NotEmpty(message="{valid.w01}")
	private String passwordConfirm;
}
