package com.softdive.madpiggy.ui.client.widget.carousel;

import com.google.gwt.user.client.ui.Widget;

public interface CarouselPageAdapter {
	
	Widget getWidget(int index);
	
	int getItemCount();
	
	void onItemSelected(int index);
	
}
