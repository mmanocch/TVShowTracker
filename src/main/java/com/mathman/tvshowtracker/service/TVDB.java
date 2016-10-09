package com.mathman.tvshowtracker.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
* This class connects to thetvdb web server, makes the appropriate API calls, parses the returned data, and returns
* it into the appropriate Java objects
*
* @author  Matthew Manocchio
* @version 1.0
* @created 2015-12-28
* @since   2016-01-19  
*/
public class TVDB {
	//The api key needed to connect to the web server
	//Part of my registration to the site
	private String apiKey = "1B5E8EC115535803";
	
	/**
	 * Gets the all series.
	 *
	 * @param filename the filename
	 * @return all the series' names
	 * @pre  filename != null
	 * @post All the series names' will be located in an ArrayList
	 */
	public ArrayList<String> getAllSeries(String filename) {
		ArrayList<String> currentShows = new ArrayList<String>();

		// Get file from resources folder (shows.txt)
		File file = new File(getClass().getClassLoader().getResource(filename).getFile());

		Scanner input = null;
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				currentShows.add(input.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			input.close();
		}
		return currentShows;
	}
	
	/**
	 * Gets the series.
	 *
	 * @param showName the show name
	 * @return the series
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 */
	public Series getSeries(String showName) throws ClientProtocolException, IOException, JAXBException {
		Series show = findSeries(showName);
		List<Actor> showActors = findActors(show.getSeriesId());
		List<Episode> showEpisodes = findEpisodes(show.getSeriesId());
		show.setAirTime(findAirTime(show.getSeriesId()));
		show.setActors(showActors);
		show.setEpisodes(showEpisodes);
		return show;
	}
	
	/**
	 * Find series.
	 *
	 * @param show the show
	 * @return the series
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @pre  show != null
	 * @post General information about the inputed series will be populated in the returned Series object
	 */
	public Series findSeries(String show) throws ClientProtocolException, IOException, JAXBException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://thetvdb.com/api/GetSeries.php?seriesname=" + URLEncoder.encode(show, "UTF-8"));

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			
			
			 JAXBContext jaxbContext = JAXBContext.newInstance(SeriesList.class);
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 SeriesList seriesList = (SeriesList)jaxbUnmarshaller.unmarshal( new StringReader(responseBody) ); 
			 
			 for (Series series : seriesList.getSeries()) {
				if(series.getSeriesName().equalsIgnoreCase(show))
					return series;
			 }
			 
			return null;
		} finally {
			httpclient.close();
		}
	}

	
	/**
	 * Find actors.
	 *
	 * @param seriesId the series id
	 * @return the list of actors
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @pre  seriesId != null
	 * @post the series' actors will be returned in a list of Actor objects
	 */
	public List<Actor> findActors(String seriesId) throws ClientProtocolException, IOException, JAXBException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://thetvdb.com/api/" + apiKey + "/series/" + seriesId + "/actors.xml");

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			
			
			 JAXBContext jaxbContext = JAXBContext.newInstance(ActorList.class);
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 ActorList actorsList = (ActorList)jaxbUnmarshaller.unmarshal( new StringReader(responseBody) ); 
			 
			 return actorsList.getActors();
		} finally {
			httpclient.close();
		}
	}
	
	/**
	 * Find episodes.
	 *
	 * @param seriesId the series id
	 * @return the list of episodes
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @pre  seriesId != null
	 * @post the series' episodes will be returned in a List of episode objects
	 */
	public List<Episode> findEpisodes(String seriesId) throws ClientProtocolException, IOException, JAXBException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://thetvdb.com/api/" + apiKey + "/series/" + seriesId + "/all/en.xml");

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			
			
			 JAXBContext jaxbContext = JAXBContext.newInstance(EpisodeList.class);
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 EpisodeList episodeList = (EpisodeList)jaxbUnmarshaller.unmarshal( new StringReader(responseBody) ); 
			 
			 return episodeList.getEpisodes();
		} finally {
			httpclient.close();
		}
	}
	
	/**
	 * Find air date.
	 *
	 * @param seriesId the series id
	 * @return the air time
	 * @throws ClientProtocolException the client protocol exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 * @pre  seriesId != null
	 * @post the series episode air time will be returned
	 */
	public String findAirTime(String seriesId) throws ClientProtocolException, IOException, JAXBException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://thetvdb.com/api/" + apiKey + "/series/" + seriesId + "/all/en.xml");

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			 
			 JAXBContext jaxbContext1 = JAXBContext.newInstance(SeriesList.class);
			 Unmarshaller jaxbUnmarshaller1 = jaxbContext1.createUnmarshaller();
			 SeriesList allSeries = (SeriesList)jaxbUnmarshaller1.unmarshal( new StringReader(responseBody) ); 
			 
			 return allSeries.getSeries().get(0).getAirTime();
		} finally {
			httpclient.close();
		}
	}


	
}
