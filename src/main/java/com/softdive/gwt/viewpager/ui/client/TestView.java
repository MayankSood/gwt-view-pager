package com.softdive.gwt.viewpager.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.softdive.gwt.viewpager.ui.client.widget.viewpager.MadpiggyTabbedViewPager;
import com.softdive.gwt.viewpager.ui.client.widget.viewpager.MadpiggyViewPagerAdapter;
import com.softdive.gwt.viewpager.ui.client.widget.viewpager.Tab;
import com.softdive.gwt.viewpager.ui.client.widget.viewpager.Tab.TabType;

public class TestView extends Composite implements MadpiggyViewPagerAdapter  {
	
	@UiField FlowPanel container;
	private MadpiggyTabbedViewPager viewPager;
	
	public static final int COUNT = 20;
	
	private static final Tab[] tabs = new Tab[COUNT];
	static {
		for (int i = 0; i<COUNT; i++) {
			tabs[i] = new Tab("Name-"+i, TabType.DEALS, i);
		}
	}
	
	private static TestViewUiBinder uiBinder = GWT.create(TestViewUiBinder.class);

	interface TestViewUiBinder extends UiBinder<Widget, TestView> {
	}

	public TestView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		initCarousel();
		
		new Object().equals(null);
	}

	private void initCarousel() {

		viewPager = new MadpiggyTabbedViewPager(this);
		viewPager.setWidth("100%");
		viewPager.setHeight(Window.getClientHeight()+"px");
		viewPager.setShowCarouselIndicator(false);
		container.add(viewPager);
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

}
