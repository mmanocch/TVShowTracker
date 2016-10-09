package com.mathman.tvshowtracker.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Runner Class
 * 
 * Builds the final layout of the GUI Contains the entry point for my
 * application (init)
 *
 * @author Matthew Manocchio
 * @version 1.0
 * @created 2015-12-28
 * @since 2016-01-19
 */
@SuppressWarnings("serial")
@Theme("valo")
public class TVShowTrackerUI extends UI {

	private ShowView showView = new ShowView(this);
	private ScheduleView scheduleView = new ScheduleView();
	/*
	 * Entry point for my application Vaadin calls it
	 */
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("TV Show Tracker");
		Label title = new Label("TV Show Tracker");
		final HorizontalLayout headerLayout = new HorizontalLayout(title);
		headerLayout.setComponentAlignment(title, Alignment.TOP_CENTER);
		
		final VerticalLayout footerLayout = new VerticalLayout(new Label(""));
		final VerticalLayout contentLayout = new VerticalLayout();

		final Panel contentPanel = new Panel(contentLayout);
		contentPanel.setSizeFull();

		final VerticalLayout mainLayout = new VerticalLayout(headerLayout, contentPanel, footerLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(contentPanel, 1);
		setContent(mainLayout);
		
		HorizontalLayout hLayout = new HorizontalLayout();
		contentPanel.setContent( hLayout );
		hLayout.setSizeFull();
		hLayout.addComponent( scheduleView );
		hLayout.setExpandRatio(scheduleView, 1);

		hLayout.addComponent( showView );
		hLayout.setExpandRatio(showView, 2);

	}

	/*
	 * Deployed as a Servlet or Portlet.
	 *
	 * You can specify additional servlet parameters like the URI and UI class
	 * name and turn on production mode when you have finished developing the
	 * application.
	 */
	@WebServlet(urlPatterns = "/*")
	@VaadinServletConfiguration(ui = TVShowTrackerUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	
	public ShowView getShowView() {
		return showView;
	}

	public ScheduleView getScheduleView() {
		return scheduleView;
	}
}