package com.softdive.madpiggy.ui.client.jsinterop.core;

import com.google.gwt.dom.client.Element;

public class Browser {

	public static native JsObject newObject() /*-{
		return {};
	}-*/;

	public static native Document getDocument() /*-{
		return $doc;
	}-*/;

	public static native void append(Element parent, HTMLElement child) /*-{
		parent.appendChild(child);
	}-*/;

	public static native void removeChild(Element parent, HTMLElement child) /*-{
		parent.removeChild(child);
	}-*/;
	
}
