package com.softdive.gwt.viewpager.ui.client.widget.carousel;

import com.google.gwt.user.client.ui.Widget;

public interface CarouselPageAdapter {
	
	Widget getWidget(int index);
	
	int getItemCount();
	
	void onItemSelected(int index);
	
}
