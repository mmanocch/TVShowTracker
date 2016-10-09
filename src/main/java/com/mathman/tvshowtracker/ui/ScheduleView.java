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
import com.vaadin.data.Property;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

/**
 * Builds your personal shows schedule based on your subscribed shows
 *
 * @author Matthew Manocchio
 * @version 1.0
 * @created 2015-12-28
 * @since 2016-01-19
 */
public class ScheduleView extends VerticalLayout {

	public static final String NAME = "My Shows";

	private List<Episode> upcomingEpisodes;
	private List<Series> mySeriesList;
	private int currentYear;
	private int currentDay;
	private int currentMonth;

	private ListSelect mySeriesUIList = new ListSelect("My Shows");
	private TreeTable treeTable;

	public ScheduleView() {
		setCurrentDate();
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		mySeriesList = new ArrayList<Series>();

		treeTable = new TreeTable("");
		treeTable.setSizeFull();

		for (Object item : treeTable.getItemIds().toArray())
			treeTable.setCollapsed(item, false);

		mySeriesUIList.setSizeFull();
		
		 mySeriesUIList.addValueChangeListener(new Property.ValueChangeListener() {
		        private static final long serialVersionUID = 1L;

		        @Override
		        public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		            final String value = (String) mySeriesUIList.getValue();
		            ((TVShowTrackerUI)getUI()).getShowView().getShowComboBox().select(value);
		            ((TVShowTrackerUI)getUI()).getShowView().showSelectedListener(value);

		        }
		    });
	}

	public void buildLayout() {
		addComponent(mySeriesUIList);
		setExpandRatio(mySeriesUIList, 1);

		addComponent(treeTable);
		setExpandRatio(treeTable, 1);
	}
	
	public void refreshSchedule() {
		populateUpcomingEpisodes();
		sortUpcomingEpisodes();
		populateTree();
	}

	private void populateUpcomingEpisodes() {
		upcomingEpisodes = new ArrayList<Episode>();
		for (int i = 0; i < mySeriesList.size(); i++)
			for (int j = 0; j < mySeriesList.get(i).getEpisodes().size(); j++) {
				Episode e = mySeriesList.get(i).getEpisodes().get(j);
				if (e.getYear() >= getCurrentYear() && e.getMonth() >= getCurrentMonth() && e.getDay() >= getCurrentDay())
					getUpcomingEpisodes().add(e);
			}
	}

	private void sortUpcomingEpisodes() {
		Collections.sort(upcomingEpisodes);
	}

	private void populateTree() {
		treeTable.removeAllItems();
		treeTable.addContainerProperty("Shows", String.class, "");
		String previousDate = "";
		List<Integer> episodesOnDates = new ArrayList<Integer>();
		String[] episodeDates = new String[upcomingEpisodes.size()];
		treeTable.addItem(new Object[] { "SHOW TIMES" }, 0);
		int count = 0;
		int dateNum = -1;
		int episodeCount = 0;
		int ongoingCount = 0;
		for (int i = 0; i < upcomingEpisodes.size(); i++) {
			if (!upcomingEpisodes.get(i).getDate().equals(previousDate)) {
				ongoingCount++;
				dateNum++;
				previousDate = upcomingEpisodes.get(i).getDate();
				episodeDates[count] = upcomingEpisodes.get(i).getDate();
				treeTable.addItem(new Object[] { upcomingEpisodes.get(i).getDate() }, ongoingCount);
				episodeCount = 1;
				episodesOnDates.add(episodeCount);
				count++;
			} else {
				episodeCount++;
				episodesOnDates.set(dateNum, episodeCount);
			}
		}

		for (int episode = 0; episode < getUpcomingEpisodes().size(); episode++) {
			for (int seriesNum = 0; seriesNum < mySeriesList.size(); seriesNum++)
				for (int episodeNum = 0; episodeNum < mySeriesList.get(seriesNum).getEpisodes().size(); episodeNum++) {
					Episode temp = mySeriesList.get(seriesNum).getEpisodes().get(episodeNum);
					if (temp.getDate().equals(episodeDates[episode])) {
						ongoingCount++;
						String output = (mySeriesList.get(seriesNum).getSeriesName() + " - " + temp.getSeasonNum() + "x" + temp.getEpisodeNum() + " (" + mySeriesList.get(seriesNum).getAirTime() + ")");
						treeTable.addItem(new Object[] { output }, ongoingCount);
					}
				}
		}

		for (int i = 0; i < episodesOnDates.size(); i++)
			treeTable.setParent(i + 1, 0);

		count = 0;
		for (int i = 0; i < episodesOnDates.size(); i++)
			for (int j = 0; j < episodesOnDates.get(i); j++) {
				count++;
				// for(int episode = episodesOnDates.length + 1 + i; episode <=
				// ongoingCount; episode++)
				treeTable.setParent(episodesOnDates.size() + count, i + 1);
			}

		// Expand the tree
		treeTable.setCollapsed(episodesOnDates.size(), false);
		for (Object itemId : treeTable.getItemIds())
			treeTable.setCollapsed(itemId, false);
	}

	private void setCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentDate = new Date();
		StringTokenizer st = new StringTokenizer(dateFormat.format(currentDate), "/ ");
		currentYear = Integer.parseInt(st.nextToken());
		currentMonth = Integer.parseInt(st.nextToken());
		currentDay = Integer.parseInt(st.nextToken());
	}

	public void addShow(Series series) {
		mySeriesList.add(series);
		mySeriesUIList.addItem(series.getSeriesName());
		refreshSchedule();
	}

	public void removeShow(Series series) {
		mySeriesList.remove(series);
		mySeriesUIList.removeItem(series.getSeriesName());
		refreshSchedule();
	}

	public boolean subscribed(String seriesName)
	{
		Series s = new Series();
		s.setSeriesName(seriesName);
		return (mySeriesList.indexOf( s ) != -1 );
	}

	public TreeTable getTreeTable() {
		return treeTable;
	}

	public void setTreeTable(TreeTable schedule) {
		this.treeTable = schedule;
	}

	public List<Episode> getUpcomingEpisodes() {
		return upcomingEpisodes;
	}

	public void setUpcomingEpisodes(List<Episode> upcomingEpisodes) {
		this.upcomingEpisodes = upcomingEpisodes;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	public void setSeries(List<Series> mySeries) {
		this.mySeriesList = mySeries;
	}

	public List<Series> getShows() {
		return mySeriesList;
	}

	public ListSelect getMySeriesUIList() {
		return mySeriesUIList;
	}
}
