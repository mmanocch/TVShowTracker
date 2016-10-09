package com.mathman.tvshowtracker.serviceTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.ClientProtocolException;

import com.mathman.tvshowtracker.service.EpisodeList;
import com.mathman.tvshowtracker.service.Series;
import com.mathman.tvshowtracker.service.Subscription;
import com.mathman.tvshowtracker.service.TVDB;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public AppTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws JAXBException
	 */
	public void testApp() throws JAXBException {
		 File episodes = new File("d:\\temp\\TVDBSeriesEpisodes.xml");
		
		 JAXBContext jaxbContext2 = JAXBContext.newInstance(EpisodeList.class);
		 Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
		 EpisodeList episodesList = (EpisodeList) jaxbUnmarshaller2.unmarshal(episodes);
		 System.out.println(episodesList);
		
		

	}
	
	public void testShowsFromFile() {
		ArrayList<String> currentShows = new ArrayList<String>();
		
		//Get file from resources folder
		File file = new File(getClass().getClassLoader().getResource("shows.txt").getFile());

		Scanner input = null;
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				currentShows.add(input.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			input.close();
		}
	}
	
	public void testTVDB() throws ClientProtocolException, IOException, JAXBException {
		TVDB tvDb = new TVDB();
		System.out.println(tvDb.findEpisodes("78901"));
	}
	
	public void testAddShow() throws Exception {
		Subscription pS = new Subscription();
		pS.addShow(new Series());
	}
	
}
