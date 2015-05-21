package com.softdive.gwt.viewpager.ui.client.jsinterop.polymer.paper.tab;

import com.softdive.gwt.viewpager.ui.client.jsinterop.core.Browser;
import com.softdive.gwt.viewpager.ui.client.jsinterop.core.HTMLElement;

public class PaperTabsElement {

	private HTMLElement element;

	protected PaperTabsElement() {
	}

	public static PaperTabsElement create(int selectedIndex, PaperTabElement... tabs) {
		PaperTabsElement paperTabsElement = new PaperTabsElement();
		paperTabsElement.element = Browser.getDocument().createElement("paper-tabs");
		paperTabsElement.element.setAttribute("noink", "");
		paperTabsElement.element.setAttribute("style", "background: #D50F25;color: #fff;");
		paperTabsElement.element.setAttribute("selected", String.valueOf(selectedIndex));
		for (PaperTabElement paperTabElement : tabs) {
			paperTabElement.getElement().setAttribute("style", "border-right-color:#E2D6D6; border-right-width: 2px;");
			paperTabsElement.element.appendChild(paperTabElement.getElement());
		}
		return paperTabsElement;
	}

	public void setSelectedIndex(int index) {
		element.setAttribute("selected", String.valueOf(index));
	}
	
	public void setScrollable(boolean value) {
		element.setAttribute("scrollable", String.valueOf(value));
	}
	
	public void hideScrollButton(boolean value) {
		element.setAttribute("hideScrollButton", String.valueOf(value));
	}

	public int getSelected() {
		return Integer.valueOf(element.getAttribute("selected"));
	}

	public HTMLElement getElement() {
		return element;
	}
}
