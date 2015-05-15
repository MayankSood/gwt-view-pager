package com.softdive.madpiggy.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(isNative = true, prototype = "Document")
public interface Document {

    public HTMLElement createElement(String div);

    public HTMLElement getElementById(String id);
    
    @JsProperty
    public HTMLBodyElement getBody();
    
    public HTMLElement querySelector(String query);

}
