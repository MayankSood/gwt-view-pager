package com.softdive.madpiggy.ui.client.jsinterop;

import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeEvent.ORIENTATION;

public class MediaQuery {
	
	private static int width = 0;
	private static int height = 0;
	
	private static void init() {
		if(width == 0) {
			width = Native.getWindowWidth();
			height = Native.getWindowHeight();
			if(width > height) {
				int tmp = width;
				width = height;
				height = tmp;
			}
		}
	}
	
	public static int getWindowWidth() {
		init();
		return width;
	}
	
	public static int getWindowHeight() {
		init();
		return height;
	}
	
	public static ORIENTATION getDeviceOrientation() {
		if(Native.getWindowWidth() > Native.getWindowHeight()) {
			return ORIENTATION.LANDSCAPE;
		}
		return ORIENTATION.PORTRAIT;
	}
}
