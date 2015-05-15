package com.softdive.madpiggy.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(isNative = true, prototype = "HTMLFormElement")
public interface HTMLFormElement extends HTMLElement {

    void submit();

    void reset();

    boolean checkValidity();
    
    @JsProperty
    int getLength();

    @JsProperty
    String acceptCharset();

    @JsProperty
    String action();

    @JsProperty
    String autocomplete();

    @JsProperty
    String enctype();

    @JsProperty
    String encoding();

    @JsProperty
    String method();

    @JsProperty
    String name();

    @JsProperty
    boolean noValidate();

    @JsProperty
    String target();
}
