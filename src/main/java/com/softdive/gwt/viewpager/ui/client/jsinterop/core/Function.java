package com.softdive.gwt.viewpager.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsType;

@JsType
public interface Function<T, E> {
    
    E f(T changed );

}


