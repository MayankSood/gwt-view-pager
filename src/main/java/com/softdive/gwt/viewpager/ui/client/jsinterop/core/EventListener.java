package com.softdive.gwt.viewpager.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsType;

@JsType
public interface EventListener<E extends JsObject> {
	
	public static final String TAP = "tap";
	public static final String DRAG_END = "dragend";
	

    void onEvent(E event);
    
    public static class Static {

        public static native EventListener<?> newInstance(EventListener<?> listener)/*-{
            return function(evt){
                listener.onEvent(evt);
            }
        }-*/;
    }
}
