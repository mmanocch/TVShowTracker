package com.mathman.tvshowtracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Holds a list of actors of a TV series
* Includes JAXB annotation for XML unmarshalling
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19 
*/
@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement(name = "Data")
public class EpisodeList {

	@XmlElement(name = "Episode", type=Episode.class)
    private List<Episode> episodes = new ArrayList<Episode>();
	
	public EpisodeList() {}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	@Override
	public String toString() {
		return "EpisodeList [episodes=" + episodes + "]";
	}
	
	
}
