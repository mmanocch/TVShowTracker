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
public class SeriesList
{
    @XmlElement(name = "Series", type=Series.class)
    private List<Series> series = new ArrayList<Series>();
 
    public SeriesList() {}
    
    public SeriesList(List<Series> series) {
        this.series = series;
    }

    public List<Series> getSeries() {
        return series;
    }
 
    public void setSeries(List<Series> series) {
        this.series = series;
    }

	@Override
	public String toString() {
		return "Series List [series=" + series + "]";
	}
    
    
}