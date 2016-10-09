package com.mathman.tvshowtracker.service;

import java.util.StringTokenizer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Represents an episode in a TV series
* Includes JAXB annotation for XML unmarshalling
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19 
*/
@XmlRootElement(name = "Episode")
public class Episode implements Comparable<Episode> {
	private int id;
	private int episodeNum;
	private int seasonNum;
	private String airDate;
	private String overview;
	private int year;
	private int day;
	private int month;

	public int getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(int id) {
		this.id = id;
	}

	public int getEpisodeNum() {
		return episodeNum;
	}

	@XmlElement(name = "EpisodeNumber")
	public void setEpisodeNum(int episodeNum) {
		this.episodeNum = episodeNum;
	}

	public int getSeasonNum() {
		return seasonNum;
	}

	@XmlElement(name = "SeasonNumber")
	public void setSeasonNum(int seasonNum) {
		this.seasonNum = seasonNum;
	}

	public String getAirDate() {
		return airDate;
	}

	@XmlElement(name = "FirstAired")
	public void setAirDate(String airDate) {
		StringTokenizer st = new StringTokenizer(airDate, "-");
		while (st.hasMoreTokens()) {
			year = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken());
			day = Integer.parseInt(st.nextToken());
		}
	}

	public int getYear() {
		return year;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public String getDate() {
		return (year + "-" + month + "-" + day);
	}

	public String getOverview() {
		return overview;
	}

	@XmlElement(name = "Overview")
	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "Episode [id=" + id + ", episodeNum=" + episodeNum + ", seasonNum=" + seasonNum + ", airDate=" + year + "-" + month + "-" + day + ", overview=" + overview + "]";
	}

	public int compareTo(Episode e) {
		if (year > e.year)
			return 1;
		if (year < e.year)
			return -1;
		if (year == e.year) {
			if (month > e.month)
				return 1;
			if (month < e.month)
				return -1;
			if (month == e.month && day > e.day)
				return 1;
			if (month == e.month && day < e.day)
				return -1;
		} 
			return 0;
	}

}
