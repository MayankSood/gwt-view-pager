package com.softdive.madpiggy.ui.client.widget.viewpager;

public class Tab {

	private String name;
	private TabType tabType;
	private String innerHTML;
	private int index;
	
	public Tab(String name, TabType tabType, int index) {
		this.name = name;
		this.tabType = tabType;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TabType getTabType() {
		return tabType;
	}

	public void setTabType(TabType tabType) {
		this.tabType = tabType;
	}

	public static enum TabType {
		DEALS, OUTLETS, MALLS, CATEGORIES
	}
	
	public String getInnerHTML() {
		return innerHTML != null? innerHTML : getName();
	}

	public void setInnerHTML(String innerHTML) {
		this.innerHTML = innerHTML;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
