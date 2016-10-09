package com.mathman.tvshowtracker.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.mathman.tvshowtracker.service.Episode;
import com.mathman.tvshowtracker.service.Series;
import com.mathman.tvshowtracker.service.TVDB;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * Builds the series info window (where you can add a series to your list or
 * remove it from your list)
 *
 * @author Matthew Manocchio
 * @version 1.0
 * @created 2015-12-28
 * @since 2016-01-19
 */
public class ShowView  extends VerticalLayout {
	private TVDB tvdb = new TVDB();

	private Image showArtImage;
	private TextArea overviewTextArea;
	private TextArea actorsTextArea;
	private Link imdbLink;

	private TextArea upcomingEpisodes;
	private ComboBox showComboBox;

	private Button addRemoveShowButton;

	private String currentSeriesName;
	private Series currentSeries;

	private TVShowTrackerUI tvShowTrackerUI;
	public static final String NAME = "";
	
    public ShowView(TVShowTrackerUI tvShowTrackerUI) {
    	setSpacing(true);
    	setMargin(true);
    	this.tvShowTrackerUI = tvShowTrackerUI;
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		showArtImage = new Image("", null);
		
		overviewTextArea = new TextArea("Overview");
		overviewTextArea.setSizeFull();

		actorsTextArea = new TextArea("Actors");
		actorsTextArea.setSizeFull();
		
		imdbLink = new Link();

		upcomingEpisodes = new TextArea("Upcoming Episodes");
		upcomingEpisodes.setSizeFull();
		
		showComboBox = new ComboBox();

		populateShowComboBox();
		showComboBox.setWidth("50%");
		showComboBox.setNullSelectionAllowed(true);
		
		addComboBoxListener();
		
		addRemoveShowButton = new Button("Add Show", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					toggleAddRemoveShowButton();
				} catch (Exception e) { 
					Notification notification = new Notification("You have reached your limit.  You must become a premium subscriber to subscribe to more shows.  ", Notification.Type.HUMANIZED_MESSAGE);
	                notification.setDelayMsec(15);
	                notification.show(Page.getCurrent());
				}
			}

		});
		addRemoveShowButton.setWidth("25%");
	
	}

	private void buildLayout() {
		setSizeFull();
		setSpacing(true);

		addComponent(showComboBox);
		addComponent(showArtImage);
		addComponent(overviewTextArea);
		addComponent(actorsTextArea);
		addComponent(imdbLink);
		addComponent(upcomingEpisodes);
		addComponent(addRemoveShowButton);
		// addComponent(removeShowButton);

		setExpandRatio(showComboBox, 1);
		setExpandRatio(showArtImage, 3);
		setExpandRatio(overviewTextArea, 2);
		setExpandRatio(actorsTextArea, 2);
		setExpandRatio(imdbLink, 0);
		setExpandRatio(upcomingEpisodes, 2);
		setExpandRatio(addRemoveShowButton, 1);

		setComponentAlignment(addRemoveShowButton, Alignment.TOP_CENTER);
		setComponentAlignment(showComboBox, Alignment.TOP_CENTER);
		
	}

	private void toggleAddRemoveShowButton() {
		if ("Add Show".equals(addRemoveShowButton.getCaption()) )
		{
			tvShowTrackerUI.getScheduleView().addShow(getCurrentSeries());
			addRemoveShowButton.setCaption("Remove Show");
		}
		else
		{
			tvShowTrackerUI.getScheduleView().removeShow(getCurrentSeries());
			addRemoveShowButton.setCaption("Add Show");
		}
	}


	public void showSelectedListener(String showName) {
		try {
			currentSeries = tvdb.getSeries(showName);
			currentSeriesName = currentSeries.getSeriesName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		overviewTextArea.setValue(currentSeries.getOverview());
		String actorInfo = "";
		for (int i = 0; i < currentSeries.getActors().size(); i++) {
			actorInfo += currentSeries.getActors().get(i).toString();
		}
		actorsTextArea.setValue(actorInfo);
		showArtImage.setSource(new ExternalResource(currentSeries.getBanner()));
		imdbLink.setResource(new ExternalResource(currentSeries.getImdbLink()));
		upcomingEpisodes.setValue(showUpcomingEpisodes(currentSeries));
		imdbLink.setCaption("IMDB LINK");
		imdbLink.setTargetName("_blank");

		if (tvShowTrackerUI.getScheduleView().subscribed(showName))
			addRemoveShowButton.setCaption("Remove Show");
		else
			addRemoveShowButton.setCaption("Add Show");
	}

	public void addComboBoxListener() {
		showComboBox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String selectedShowName = (String) showComboBox.getValue();
				showSelectedListener(selectedShowName);
				
			}
		});
	}
	
	private void populateShowComboBox() {
		ArrayList<String> showNames = tvdb.getAllSeries("newshows.txt");
		for (String show : showNames) {
			showComboBox.addItem(show);
		}
	}

	private String showUpcomingEpisodes(Series s) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentDate = new Date();
		StringTokenizer st = new StringTokenizer(dateFormat.format(currentDate), "/ ");
		int currentYear = Integer.parseInt(st.nextToken());
		int currentMonth = Integer.parseInt(st.nextToken());
		int currentDay = Integer.parseInt(st.nextToken());

		List<Episode> upcomingEpisodes = new ArrayList<Episode>();
		for (int j = 0; j < s.getEpisodes().size(); j++) {
			Episode e = s.getEpisodes().get(j);
			if (e.getYear() >= currentYear && e.getMonth() >= currentMonth && e.getDay() >= currentDay)
				upcomingEpisodes.add(e);
		}

		Collections.sort(upcomingEpisodes);

		String output = "";
		if (upcomingEpisodes.size() == 0)
			output = "THERE ARE CURRENTLY NO UPCOMING EPISODES!";
		for (int i = 0; i < upcomingEpisodes.size(); i++)
			output += (upcomingEpisodes.get(i).getDate() + " -- " + upcomingEpisodes.get(i).getSeasonNum() + "x" + upcomingEpisodes.get(i).getEpisodeNum() + " (" + s.getAirTime() + ") *** " + upcomingEpisodes.get(i).getOverview() + " \n\n");

		return output;
	}

	public Series getCurrentSeries() {
		return currentSeries;
	}

	public void setCurrentSeries(Series currentSeries) {
		this.currentSeries = currentSeries;
	}

	public String getCurrentSeriesName() {
		return currentSeriesName;
	}

	public void setCurrentSeriesName(String currentSeriesName) {
		this.currentSeriesName = currentSeriesName;
	}

	public ComboBox getShowComboBox() {
		return showComboBox;
	}

	public void disableAddButton() {
		addRemoveShowButton.setEnabled(false);
	}

	public void enableAddButton() {
		addRemoveShowButton.setEnabled(true);
	}

	public Button getAddRemoveShowButton() {
		return addRemoveShowButton;
	}

}
