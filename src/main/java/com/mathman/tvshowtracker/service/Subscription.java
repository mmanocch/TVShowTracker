package com.mathman.tvshowtracker.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
* Holds all information about a regular subscription
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19 
*/
public class Subscription {
	protected List<Series> myShows;
	protected int maxSubSize;
	
	/**
	 * Adds the show to your list.
	 *
	 * @param show the show
	 * @throws Exception the exception
	 * @pre  show != null
	 * @post myShows.contains(show) == true
	 */
	public void addShow(Series show) throws Exception {
		myShows.add(show);
		Collections.sort(myShows);
	}
	
	/**
	 * Removes the show from your list.
	 *
	 * @param series the series
	 * @post myShows.contains(series) == false
	 */
	public void removeShow(Series series) {
		for(int i = 0; i < myShows.size(); i++)
			if(series.getSeriesName().equals(myShows.get(i).getSeriesName()))
				myShows.remove(i);
	}

	
	/**
	 * Gets the max sub size.
	 *
	 * @return the max sub size
	 * 
	 */
	public int getMaxSubSize() {
		return maxSubSize;
	}

	public Subscription() {
		super();
		maxSubSize=3;
		myShows = new ArrayList<Series>();
	}

	/**
	 * Gets the my shows.
	 *
	 * @return the my shows
	 */
	public List<Series> getMyShows() {
		return myShows;
	}
	
	/**
	 * Sets the my shows.
	 *
	 * @param myShows the new my shows
	 */
	public void setMyShows(List<Series> myShows) {
		this.myShows = myShows;
	}
	

}
