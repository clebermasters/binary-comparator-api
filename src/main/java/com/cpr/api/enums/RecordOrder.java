package com.cpr.api.enums;

public enum RecordOrder {
	LEFT, RIGHT;
	
	@Override
	public String toString() {
		switch (this) {
		case RIGHT:
			return "RIGHT";
		case LEFT:
			return "LEFT";
		}
		throw new RuntimeException("The side not exists.");
	}
}