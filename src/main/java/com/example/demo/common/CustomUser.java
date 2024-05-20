package com.example.demo.common;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
	private final com.example.demo.entity.User user;

	public CustomUser(String username, String password, com.example.demo.entity.User user) {
		super(username, password, true, true, true, true, AuthorityUtils.createAuthorityList("ADMIN"));
		this.user = user;
	}

	public com.example.demo.entity.User getUser() {
		return this.user;
	}
}