package com.softdive.madpiggy.ui.client.jsinterop.core;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "HTMLElement")
public interface HTMLElement extends Element {

    public void setAttribute(String id, String value);
    
    public String getAttribute(String id);

    public void appendChild(HTMLElement element);

    public void addEventListener(String event, EventListener<? extends JsObject> handler);

    @JsProperty
    public void setInnerHTML(String text);

    @JsProperty
    public void setInnerText(String text);

}
