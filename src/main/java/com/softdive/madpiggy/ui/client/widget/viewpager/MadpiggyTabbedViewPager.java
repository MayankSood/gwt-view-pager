package com.softdive.madpiggy.ui.client.widget.viewpager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.softdive.madpiggy.ui.client.jsinterop.core.Browser;
import com.softdive.madpiggy.ui.client.jsinterop.core.EventListener;
import com.softdive.madpiggy.ui.client.jsinterop.core.JsObject;
import com.softdive.madpiggy.ui.client.jsinterop.polymer.paper.tab.PaperTabElement;
import com.softdive.madpiggy.ui.client.jsinterop.polymer.paper.tab.PaperTabsElement;
import com.softdive.madpiggy.ui.client.widget.carousel.Carousel;
import com.softdive.madpiggy.ui.client.widget.carousel.CarouselPageAdapter;

public class MadpiggyTabbedViewPager extends Composite implements CarouselPageAdapter {

	@UiField
	FlexPanel parentContainer;

	private Carousel carousel;
	private MadpiggyViewPagerAdapter adapter;
	private PaperTabsElement paperTabs;

	private static MadpiggyTabbedViewPagerUiBinder uiBinder = GWT.create(MadpiggyTabbedViewPagerUiBinder.class);

	interface MadpiggyTabbedViewPagerUiBinder extends UiBinder<Widget, MadpiggyTabbedViewPager> {
	}

	public MadpiggyTabbedViewPager(MadpiggyViewPagerAdapter adapter) {
		this.adapter = adapter;
		initWidget(uiBinder.createAndBindUi(this));

		initTabs();
		initCarousel();
	}

	private void initTabs() {
		PaperTabElement[] tabElements = new PaperTabElement[adapter.getTabs().length];
		int i = -1;
		int selectedIndex = 0;
		for (final Tab tab : adapter.getTabs()) {
			PaperTabElement tabElement = PaperTabElement.create(tab.getInnerHTML());
			tabElement.addEventListener(EventListener.TAP,
					EventListener.Static.newInstance(new EventListener<JsObject>() {

						public void onEvent(JsObject event) {
							new Timer() {

								@Override
								public void run() {
									carousel.setSelectedPage(tab.getIndex());
								}
							}.schedule(1);
						};
					}));

			tabElements[++i] = tabElement;

		}

		paperTabs = PaperTabsElement.create(selectedIndex, tabElements);
		paperTabs.setScrollable(true);
		paperTabs.hideScrollButton(true);

		FlowPanel tabsContainer = new FlowPanel();
		parentContainer.add(tabsContainer);

		Browser.append(tabsContainer.getElement(), paperTabs.getElement());
	}

	private void initCarousel() {
		carousel = new Carousel();
		carousel.setWidth("100%");
		carousel.setHeight(Window.getClientHeight() + "px");
		carousel.setShowCarouselIndicator(false);
		carousel.setAdapter(this);
		parentContainer.add(carousel);
	}

	@Override
	public Widget getWidget(int index) {
		return adapter.getWidget(index);
	}

	@Override
	public int getItemCount() {
		return adapter.getItemCount();
	}

	@Override
	public void onItemSelected(int index) {
		paperTabs.setSelectedIndex(index);
		adapter.onItemSelected(index);
	}

	public void setShowCarouselIndicator(boolean b) {
		carousel.setShowCarouselIndicator(b);
	}

	public void setSelectedPage(int index) {
		carousel.setSelectedPage(index, false);
	}

}
