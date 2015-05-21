package com.softdive.gwt.viewpager.ui.client.jsinterop.polymer.paper.tab;

import com.softdive.gwt.viewpager.ui.client.jsinterop.core.Browser;
import com.softdive.gwt.viewpager.ui.client.jsinterop.core.EventListener;
import com.softdive.gwt.viewpager.ui.client.jsinterop.core.HTMLElement;
import com.softdive.gwt.viewpager.ui.client.jsinterop.core.JsObject;

public final class PaperTabElement {
	
	private HTMLElement element;

	protected PaperTabElement() {
	}
	
	public static PaperTabElement create(String innerHTML) {
		PaperTabElement paper = new PaperTabElement();
		paper.element = Browser.getDocument().createElement("paper-tab");
		paper.element.setInnerHTML(innerHTML);
		return paper;
	}
	
	public HTMLElement getElement() {
		return element;
	}
	
	public void addEventListener(String event, EventListener<? extends JsObject> eventListener) {
		element.addEventListener(event, eventListener);
	}
	
	public void setIndex(int index) {
		element.setAttribute("index", String.valueOf(index));
	}
	
	public int getIndex() {
		return Integer.valueOf(element.getAttribute("index"));
	}
	
	public final native void click() /*-{ this.element.click(); }-*/;  
}
