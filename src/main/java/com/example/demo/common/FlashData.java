package com.example.demo.common;

import lombok.Data;

@Data
public class FlashData {
	private static final String SUCCESS = "success";
	private static final String DANGER = "danger";
	private static final String WARNING = "warning";
	private static final String INFO = "info";

	private String key;
	private String message;

	public FlashData() {
		this.setKey(INFO);
	}

	public FlashData success(String message) {
		this.setKey(SUCCESS);
		this.setMessage(message);
		return this;
	}

	public FlashData danger(String message) {
		this.setKey(DANGER);
		this.setMessage(message);
		return this;
	}

	public FlashData warning(String message) {
		this.setKey(WARNING);
		this.setMessage(message);
		return this;
	}  

	public FlashData info(String message) {
		this.setKey(INFO);
		this.setMessage(message);
		return this;
	}
}