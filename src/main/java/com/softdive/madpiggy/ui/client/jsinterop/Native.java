package com.softdive.madpiggy.ui.client.jsinterop;

import com.google.gwt.user.client.Window;

public class Native {

	public static int getWindowWidth() {
		return Window.getClientWidth();
	}

	public static int getWindowHeight() {
		return Window.getClientHeight();
	}
	
	public static int getSmallestDimension() {
		if (getWindowHeight() < getWindowWidth()) {
			return getWindowHeight();
		}
		return getWindowWidth();
	}
}
