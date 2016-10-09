package com.mathman.tvshowtracker.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement(name = "Series")
public class AllSeries {
	String airTime;

	public String getAirTime() {
		return airTime;
	}

	@XmlElement (name="Airs_Time")
	public void setAirTime(String airTime) {
		this.airTime = airTime;
	}
	
	
}
