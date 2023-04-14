package com.example.demo.common;

public class MaxCapacityReachedException extends Exception {
	public MaxCapacityReachedException() {
	}

	public MaxCapacityReachedException(String msg) {
		super(msg);
	}

	public MaxCapacityReachedException(Throwable th) {
		super(th);
	}

	public MaxCapacityReachedException(String msg, Throwable th) {
		super(msg, th);
	}
}