package com.softdive.madpiggy.ui.client;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.RootPaneContainer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.HasRows;

public class MadpiggyEntryPoint implements EntryPoint {
	
	public final static Logger logger = Logger.getLogger(MadpiggyEntryPoint.class.getName());
	
	public void onModuleLoad() {

		RootPanel.get().add(new TestView());
	}
}
