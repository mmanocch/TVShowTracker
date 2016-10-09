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
* @since   @created 2015-12-28
* @since   2016-01-19 
*/
@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement(name = "Actors")
public class ActorList {
	
	@XmlElement(name = "Actor", type=Actor.class)
	 private List<Actor> actors = new ArrayList<Actor>();
	 
	 public ActorList() {}
	 
	 public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "ActorList [actors=" + actors + "]";
	}

	
	 
	 
}
