package uk.ac.cam.cl.dtg.android.time.data;

/**
 * Provides functionality for querying the data feeds for Cambridgeshire's
 * bus system
 * 
 * @author dt316
 *
 */

import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import uk.ac.cam.cl.dtg.android.time.buses.BusArrivalData;
import uk.ac.cam.cl.dtg.android.time.buses.BusStop;
import uk.ac.cam.cl.dtg.android.time.data.handlers.ArrivalsSAXHandler;
import uk.ac.cam.cl.dtg.android.time.data.handlers.GetStopsSAXHandler;
import uk.ac.cam.cl.dtg.android.time.data.handlers.StopIndividualSAXHandler;

public class TransportDataProvider {

	private String feedURL;
	private String apiKey;
	
	public TransportDataProvider(String apiKey, String feedURL) {
		
		this.feedURL = feedURL;
		this.apiKey = apiKey;
	}

	/**
	 * Returns the list of BusStops visible at a certain zoom level.
	 * @param level Zoom level, 1, 2 or 3. 1 is farthest, 3 is nearest.
	 * @return
	 * @throws TransportDataException
	 */
	public List<BusStop> getBusStops(int level) throws TransportDataException
	{
	
		try {
			
			/* Create a URL we want to load some xml-data from. */
			URL url = new URL(feedURL+"GetStops?key="+apiKey+"&level="+level);

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
			
			/* Create a new ContentHandler and apply it to the XML-Reader*/
			GetStopsSAXHandler theHandler = new GetStopsSAXHandler();
			xr.setContentHandler(theHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(url.openStream()));		
			
			/* Parsing has finished. */
			return theHandler.getData();
			
		} catch (Exception e) {
			throw new TransportDataException(e);

		}
		
	}
	
	/**
	 * Gets the next n arrivals at a certain bus stop.
	 * @param Stop The stop in question.
	 * @param NumberToFetch Maximum number of arrivals to fetch. First 3 are of the format 'x minutes', after that timetabled information is given.
	 * @return
	 * @throws TransportDataException
	 */
	public BusArrivalData getBusArrivalData(String stopRef, int numberToFetch) throws TransportDataException
	{
		try {
			
			/* Create a URL we want to load some xml-data from. */
			URL url = new URL(feedURL+"GetArrivals?key="+apiKey+"&atco="+stopRef+"&numarrivals="+numberToFetch);

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
			
			/* Create a new ContentHandler and apply it to the XML-Reader*/
			ArrivalsSAXHandler theHandler = new ArrivalsSAXHandler();
			xr.setContentHandler(theHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(url.openStream()));		
			
			/* Parsing has finished. */
			return theHandler.getData();
			
		} catch (Exception e) {
			
			throw new TransportDataException(e);

		}
		
	}
	
	/**
	 * Gets the next n arrivals at a certain bus stop.
	 * @param smsCode SMS code of the stop to lookup
	 * @return Requested bus stop
	 * @throws TransportDataException
	 */
	public BusStop getStopBySMS(String smsCode) throws TransportDataException
	{
		try {
			
			/* Create a URL we want to load some xml-data from. */
			URL url = new URL(feedURL+"GetStopBySMS?key="+apiKey+"&smscode="+smsCode);

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();
			
			/* Create a new ContentHandler and apply it to the XML-Reader*/
			StopIndividualSAXHandler theHandler = new StopIndividualSAXHandler();
			xr.setContentHandler(theHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(url.openStream()));		
			
			/* Parsing has finished. */
			return theHandler.getData();
			
		} catch (Exception e) {
			
			throw new TransportDataException(e);

		}
		
	}	
	
}
