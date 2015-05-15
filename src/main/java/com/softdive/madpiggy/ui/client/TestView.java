package com.softdive.madpiggy.ui.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.softdive.madpiggy.ui.client.widget.viewpager.MadpiggyTabbedViewPager;
import com.softdive.madpiggy.ui.client.widget.viewpager.MadpiggyViewPagerAdapter;
import com.softdive.madpiggy.ui.client.widget.viewpager.Tab;
import com.softdive.madpiggy.ui.client.widget.viewpager.Tab.TabType;

public class TestView extends Composite implements MadpiggyViewPagerAdapter  {
	
	@UiField FlowPanel container;
	private MadpiggyTabbedViewPager carousel;
	
	public static final int COUNT = 20;
	
	private static final Tab[] tabs = new Tab[10];
	static {
		for (int i = 0; i<COUNT; i++) {
			tabs[i] = new Tab("Name-"+i, TabType.DEALS, i);
		}
	}
	
	private final static Logger logger = Logger.getLogger("TestView");

	private static TestViewUiBinder uiBinder = GWT.create(TestViewUiBinder.class);

	interface TestViewUiBinder extends UiBinder<Widget, TestView> {
	}

	public TestView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		initCarousel();
	}

	private void initCarousel() {

		carousel = new MadpiggyTabbedViewPager(this);
		carousel.setWidth("100%");
		carousel.setHeight(Window.getClientHeight()+"px");
		carousel.setShowCarouselIndicator(false);
		container.add(carousel);
	}

	@Override
	public Widget getWidget(int index) {
		FlexPanel flexPanel = new FlexPanel();
		flexPanel.setWidth("100%");
		flexPanel.setOrientation(Orientation.VERTICAL);
		for (int j = 0; j<200; j++) {
			flexPanel.add(new HTMLPanel("Page-"+(index)+" : "+j));
		}
		return flexPanel;
	}

	@Override
	public int getItemCount() {
		return tabs.length;
	}

	@Override
	public void onItemSelected(int index) {
	}

	@Override
	public Tab[] getTabs() {
		return tabs;
	}

	@Override
	public void onTabSelection(Tab tab) {
		carousel.setSelectedPage(tab.getIndex());
	}

}
