package com.mathman.tvshowtracker.service;


/**
* This class connects to the TVDB web server, makes the proper API calls, parses the returned data, and returns
* it into the appropriate objects
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19 
*/
public class Subscriber {
	private String name;
	private Subscription sub;

	public Subscriber(String name) {
		this.name = name;
		sub = new Subscription();
	}

	public String getName() {
		return name;
	}
	
	public Subscription getSub() {
		return sub;
	}

	public void setSub(Subscription sub) {
		this.sub = sub;
	}
	

}
