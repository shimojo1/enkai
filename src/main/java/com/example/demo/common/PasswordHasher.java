package com.example.demo.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHasher {
	private static PasswordEncoder encoder = new BCryptPasswordEncoder();

	// 暗号化した文字列を返す
	public static String create(String baseString) {
		return encoder.encode(baseString);
	}

	// パスワードが一致するかどうかの判定処理
	public static boolean matches(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}
}