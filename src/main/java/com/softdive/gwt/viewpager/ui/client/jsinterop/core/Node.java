package com.softdive.gwt.viewpager.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "Node")
public interface Node extends EventTarget {

    void bind(String property, JsObject... objects);
}
