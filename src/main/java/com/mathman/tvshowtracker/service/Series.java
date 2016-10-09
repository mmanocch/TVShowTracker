package com.mathman.tvshowtracker.service;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
*  Represents a TV series
*  Includes JAXB annotation for XML unmarshalling
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19 
*/
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Series")
public class Series implements Comparable<Series> {

	private String seriesName;
	private String seriesId;
	private List<Actor> actors;
	private List<Episode> episodes;
	private String overview;
	private String imdbId;
	private String network;
	private String banner;
	private String airTime;
	


	public String getAirTime() {
		return airTime;
	}

	@XmlElement (name="Airs_Time")
	public void setAirTime(String airDate) {
		this.airTime = airDate;
	}

	public String getBanner() {
		return "http://thetvdb.com/banners/" + banner;
	}

	@XmlElement (name="banner")
	public void setBanner(String banner) {
		this.banner = banner;
	}

	@XmlElement (name="SeriesName")
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	@XmlElement (name="seriesid")
	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	@XmlElement (name = "Overview")
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public String getSeriesId() {
		return seriesId;
	}
	
	/**
	 * Gets the actors.
	 *
	 * @return the actors
	 */
	public List<Actor> getActors() {
		return actors;
	}

	public String getOverview() {
		return overview;
	}
	
	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	
	public String getImdbLink() {
		return "http://www.imdb.com/title/" + imdbId + "/";
	}

	@XmlElement (name = "IMDB_ID")
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getNetwork() {
		return network;
	}

	@XmlElement (name = "Network")
	public void setNetwork(String network) {
		this.network = network;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Series other = (Series) obj;
		if (seriesName == null) {
			if (other.seriesName != null)
				return false;
		} else if (!seriesName.equals(other.seriesName))
			return false;
		return true;
	}

	public int compareTo(Series s) {
		if(this.getSeriesName().compareTo(s.getSeriesName()) > 0)
			return 1;
		if(this.getSeriesName().compareTo(s.getSeriesName()) < 0)
			return -1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "Series [seriesName=" + seriesName + ", seriesId=" + seriesId + ", overview=" + overview + "]";
	}


}
