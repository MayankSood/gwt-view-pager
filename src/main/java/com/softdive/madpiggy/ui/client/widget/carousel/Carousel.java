/*
 * Copyright 2012 Daniel Kurka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.softdive.madpiggy.ui.client.widget.carousel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.collection.shared.LightArrayInt;
import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeEvent;
import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Justification;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollEndEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollMoveEvent;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollRefreshEvent;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;
import com.softdive.madpiggy.ui.client.widget.carousel.CarouselAppearance.CarouselCss;

/**
 * A carousel renders its children in a row. A user can select a different child
 * by swiping between them.
 *
 */
public class Carousel extends Composite implements HasSelectionHandlers<Integer>, SelectionHandler<Integer> {

	private CarouselPageAdapter carouselPageAdapter;
	private int numberOfOffscreenViews = 2;
	
	private static class CarouselIndicatorContainer extends Composite {
		private FlexPanel main;
		private final CarouselCss css;
		private ArrayList<CarouselIndicator> indicators;
		private int selectedIndex;

		public CarouselIndicatorContainer(CarouselCss css, int numberOfPages) {
			if (numberOfPages < 0) {
				throw new IllegalArgumentException();
			}
			this.css = css;
			main = new FlexPanel();
			initWidget(main);
			main.setOrientation(Orientation.HORIZONTAL);
			main.setJustification(Justification.CENTER);

			main.addStyleName(this.css.indicatorMain());

			FlexPanel container = new FlexPanel();
			container.addStyleName(this.css.indicatorContainer());
			container.setOrientation(Orientation.HORIZONTAL);
			main.add(container);

			indicators = new ArrayList<Carousel.CarouselIndicator>(numberOfPages);
			selectedIndex = 0;

			for (int i = 0; i < numberOfPages; i++) {
				CarouselIndicator indicator = new CarouselIndicator(css);
				indicators.add(indicator);
				container.add(indicator);
			}

			setSelectedIndex(selectedIndex);
		}

		public void setSelectedIndex(int index) {
			if (indicators.isEmpty()) {
				selectedIndex = -1;
				return;
			}
			if (selectedIndex != -1) {
				indicators.get(selectedIndex).setActive(false);
			}
			selectedIndex = index;
			if (selectedIndex != -1) {
				indicators.get(selectedIndex).setActive(true);

			}
		}
	}

	private static class CarouselIndicator extends TouchWidget {
		private final CarouselCss css;

		public CarouselIndicator(CarouselCss css) {
			this.css = css;
			setElement(Document.get().createDivElement());

			addStyleName(css.indicator());

		}

		public void setActive(boolean active) {
			if (active) {
				addStyleName(css.indicatorActive());
			} else {
				removeStyleName(css.indicatorActive());
			}
		}
	}

	@UiField
	public FlexPanel main;
	@UiField
	public ScrollPanel scrollPanel;
	@UiField
	public FlowPanel container;
	private CarouselIndicatorContainer carouselIndicatorContainer;
	private boolean isVisibleCarouselIndicator = true;

	private int currentPage;

	private Map<Integer, FlowPanel> indexToWidget;
	private HandlerRegistration refreshHandler;

	private static final CarouselImpl IMPL = GWT.create(CarouselImpl.class);

	private static final CarouselAppearance DEFAULT_APPEARANCE = GWT.create(CarouselAppearance.class);
	private final CarouselAppearance appearance;
	private boolean hasScollData;

	public Carousel() {
		this(DEFAULT_APPEARANCE);
	}

	public Carousel(CarouselAppearance appearance) {

		this.appearance = appearance;
		initWidget(this.appearance.carouselBinder().createAndBindUi(this));
		indexToWidget = new HashMap<Integer, FlowPanel>();

		scrollPanel.setSnap(true);
		scrollPanel.setSnapThreshold(50);
		scrollPanel.setMomentum(false);
		scrollPanel.setShowVerticalScrollBar(false);
		scrollPanel.setShowHorizontalScrollBar(false);
		scrollPanel.setScrollingEnabledY(true);
		scrollPanel.setAutoHandleResize(false);

		currentPage = 0;

		scrollPanel.addScrollEndHandler(new ScrollEndEvent.Handler() {

			@Override
			public void onScrollEnd(ScrollEndEvent event) {
				int page = scrollPanel.getCurrentPageX();

				carouselIndicatorContainer.setSelectedIndex(page);
				SelectionEvent.fire(Carousel.this, page);

			}
		});

		scrollPanel.addScrollMoveHandler(new ScrollMoveEvent.Handler() {

			@Override
			public void onScrollMove(ScrollMoveEvent event) {
				TouchMoveEvent moveEvent = event.getEvent();
				moveEvent.stopPropagation();
				moveEvent.preventDefault();
			}
		});

		MGWT.addOrientationChangeHandler(new OrientationChangeHandler() {

			@Override
			public void onOrientationChanged(OrientationChangeEvent event) {
				refresh();
			}
		});

		addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				carouselIndicatorContainer.setSelectedIndex(currentPage);
			}
		});

		if (MGWT.getOsDetection().isDesktop()) {
			Window.addResizeHandler(new ResizeHandler() {

				@Override
				public void onResize(ResizeEvent event) {
					refresh();
				}
			});
		}
		
		addHandler(this, SelectionEvent.getType());

	}

	public void clear() {
		container.clear();
		indexToWidget.clear();
	}

	public Iterator<FlowPanel> iterator() {
		Collection<FlowPanel> values = indexToWidget.values();
		return values.iterator();
	}

	public boolean remove(Widget w) {
		Widget holder = indexToWidget.remove(w);
		if (holder != null) {
			return container.remove(holder);
		}
		return false;

	}

	@Override
	protected void onLoad() {
		refresh();
	}

	/**
	 * refresh the carousel widget, this is necessary after changing child
	 * elements
	 */
	public void refresh() {
		hasScollData = false;
		final int delay = MGWT.getOsDetection().isAndroid() ? 200 : 1;
		IMPL.adjust(main, container);
		// allow layout to happen..
		new Timer() {

			@Override
			public void run() {
				IMPL.adjust(main, container);

				scrollPanel.setScrollingEnabledX(true);
				scrollPanel.setScrollingEnabledY(true);

				scrollPanel.setShowVerticalScrollBar(false);
				scrollPanel.setShowHorizontalScrollBar(false);

				if (carouselIndicatorContainer != null) {
					carouselIndicatorContainer.removeFromParent();

				}

				int widgetCount = container.getWidgetCount();

				carouselIndicatorContainer = new CarouselIndicatorContainer(appearance.cssCarousel(), widgetCount);

				if (isVisibleCarouselIndicator) {
					main.add(carouselIndicatorContainer);
				}

				if (currentPage >= widgetCount) {
					currentPage = widgetCount - 1;
				}

				carouselIndicatorContainer.setSelectedIndex(currentPage);
				
				refreshHandler = scrollPanel.addScrollRefreshHandler(new ScrollRefreshEvent.Handler() {

					@Override
					public void onScrollRefresh(ScrollRefreshEvent event) {
						refreshHandler.removeHandler();
						refreshHandler = null;
						LightArrayInt pagesX = scrollPanel.getPagesX();
						if (currentPage < 0) {
							currentPage = 0;
						} else if (currentPage >= pagesX.length()) {
							currentPage = pagesX.length() - 1;
						}
						scrollPanel.scrollToPage(currentPage, 0, 0);
						hasScollData = true;
					}
				});
				scrollPanel.refresh();
			}

		}.schedule(delay);

	}

	public void setSelectedPage(int index) {
		setSelectedPage(index, true);
	}

	public void setSelectedPage(int index, boolean issueEvent) {
		if (isAttached() && hasScollData) {
			LightArrayInt pagesX = scrollPanel.getPagesX();
			if (index < 0 || index >= pagesX.length()) {
				throw new IllegalArgumentException("invalid value for index: " + index);
			}
			currentPage = index;
			scrollPanel.scrollToPage(index, 0, 300, issueEvent);
		} else {
			currentPage = index;
		}
	}

	public int getSelectedPage() {
		return currentPage;
	}

	@Override
	public com.google.gwt.event.shared.HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
		return null;
	}

	interface CarouselImpl {
		void adjust(Widget main, FlowPanel container);
	}

	// GWT rebinding
	@SuppressWarnings("unused")
	private static class CarouselImplSafari implements CarouselImpl {

		@Override
		public void adjust(Widget main, FlowPanel container) {
			int widgetCount = container.getWidgetCount();

			double scaleFactor = 100d / widgetCount;

			for (int i = 0; i < widgetCount; i++) {
				Widget w = container.getWidget(i);
				w.setWidth(scaleFactor + "%");
				w.getElement().getStyle().setLeft(i * scaleFactor, Unit.PCT);
			}

			container.setWidth((widgetCount * 100) + "%");
			container.getElement().getStyle().setHeight(main.getOffsetHeight(), Unit.PX);
		}

	}

	// GWT rebinding
	@SuppressWarnings("unused")
	private static class CarouselImplGecko implements CarouselImpl {

		@Override
		public void adjust(Widget main, FlowPanel container) {
			int widgetCount = container.getWidgetCount();
			int offsetWidth = main.getOffsetWidth();

			container.setWidth(widgetCount * offsetWidth + "px");

			for (int i = 0; i < widgetCount; i++) {
				container.getWidget(i).setWidth(offsetWidth + "px");
			}
		}
	}

	/**
	 * Set if carousel indicator is displayed.
	 */
	public void setShowCarouselIndicator(boolean isVisibleCarouselIndicator) {
		if (!isVisibleCarouselIndicator && carouselIndicatorContainer != null) {
			carouselIndicatorContainer.removeFromParent();
		}
		this.isVisibleCarouselIndicator = isVisibleCarouselIndicator;
	}

	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}
	
	public void setAdapter(CarouselPageAdapter adapter) {
		this.carouselPageAdapter = adapter;
		
		for (int count = 0; count < carouselPageAdapter.getItemCount(); count++) {
			FlowPanel widgetHolder = new FlowPanel();
			widgetHolder.addStyleName(this.appearance.cssCarousel().carouselHolder());
			
			indexToWidget.put(count, widgetHolder);

			container.add(widgetHolder);

			IMPL.adjust(main, container);
		}
	}

	@UiFactory
	public CarouselAppearance getAppearance() {
		return appearance;
	}

	@Override
	public void onSelection(SelectionEvent<Integer> event) {
		passEventToContainer(event);
		
		handleOffscreenViews(event.getSelectedItem());
	}
	
	public void setOffscreenViews(int offscreenViews) {
		this.numberOfOffscreenViews = offscreenViews;
	}

	private void handleOffscreenViews(int selectedItem) {
		for (Map.Entry<Integer, FlowPanel> entrySet : indexToWidget.entrySet()) {
			int entryIndex = entrySet.getKey();
			if (!(entryIndex == selectedItem || entryIndex == selectedItem-(numberOfOffscreenViews/2) || entryIndex == selectedItem+(numberOfOffscreenViews/2))) {
				entrySet.getValue().getElement().removeAllChildren();
			} else {
				if (entrySet.getValue().getElement().getChildCount() == 0) {
					entrySet.getValue().add(carouselPageAdapter.getWidget(entryIndex));
				}
			}
		}
		
		//TODO remove in future, just for testing
		for (Map.Entry<Integer, FlowPanel> w : indexToWidget.entrySet()) {
			if (w.getValue().getElement().getChildCount() != 0) {
				//Window.alert(w.getKey()+"---"+w.getValue().toString());
			}
		}
	}

	private void passEventToContainer(SelectionEvent<Integer> event) {
		if (event.getSelectedItem() != currentPage) {
			carouselPageAdapter.onItemSelected(event.getSelectedItem());
		}
		currentPage = event.getSelectedItem();

	}
	
}
