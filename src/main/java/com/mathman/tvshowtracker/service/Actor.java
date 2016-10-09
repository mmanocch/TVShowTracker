package com.mathman.tvshowtracker.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Represents an actor in a TV series
* Includes JAXB annotation for XML unmarshalling
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19 
*/
@XmlRootElement(name = "Actor")
public class Actor {
	private int id;
	private String name;
	private String role;
	private int sortOrder;
	
	public int getId() {
		return id;
	}
	@XmlElement (name="id")
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@XmlElement (name="Name")
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	@XmlElement (name="Role")
	public void setRole(String role) {
		this.role = role;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	@XmlElement (name="SortOrder")
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	@Override
	public String toString() {
		return name + "----" + role + "\n";
	}
	
	
}
