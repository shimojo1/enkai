package com.example.demo.common;

import jakarta.validation.groups.Default;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ValidationGroups {
	public interface Create extends Default {};
	public interface Replace extends Default {};
	public interface Update extends Default {};
	public interface Delete extends Default {};
}