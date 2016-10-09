package com.mathman.tvshowtracker.service;

import java.util.List;

import com.omertron.thetvdbapi.*;
import com.omertron.thetvdbapi.model.Language;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	TheTVDBApi tvdb = new TheTVDBApi("123");
    	try {
			List<Language> result = tvdb.getLanguages();
		} catch (TvDbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println( "Hello World!" );
    }
}
