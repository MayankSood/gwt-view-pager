package com.softdive.gwt.viewpager.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsType;

@JsType(isNative = true, prototype = "JSON")
public interface JSON {
    
    String stringify(JsObject obj);
    
}
