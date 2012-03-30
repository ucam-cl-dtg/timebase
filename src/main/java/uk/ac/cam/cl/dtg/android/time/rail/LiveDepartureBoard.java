package uk.ac.cam.cl.dtg.android.time.rail;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class LiveDepartureBoard {

  /**
   * Exception thrown when error occurred talking to remote services
   * @author drt24
   *
   */
  public static class RemoteException extends Exception {
    private static final long serialVersionUID = 1L;

    protected RemoteException(String message, Throwable t){
      super(message,t);
    }
  }
  private static final String BOARDS_ADDRESS = "http://www.livedepartureboards.co.uk/ldbws/ldb2.asmx";
  
	public static ServiceBoard getDepartures(String CRS, int numDeps) throws RemoteException {

		try { // Required for the I/O
			URL	url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream	input;

			// URL of CGI-Bin script.
			url = new URL (BOARDS_ADDRESS);

			// URL connection channel.
			urlConn = url.openConnection();

			// Let the run-time system (RTS) know that we want input.
			urlConn.setDoInput (true);

			// Let the RTS know that we want to do output.
			urlConn.setDoOutput (true);

			// No caching, we want the real thing.
			urlConn.setUseCaches (false);  

			String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
				+ "<GetDepartureBoardRequest xmlns=\"http://thalesgroup.com/RTTI/2008-02-20/ldb/types\">"
				+ "<numRows>" + numDeps + "</numRows>"
				+ "<crs>" + CRS + "</crs>"
				+ "</GetDepartureBoardRequest></soap:Body></soap:Envelope>";


			// Specify the content type.
			urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			urlConn.setRequestProperty("Content-Length", Integer.toString(data.length()));

			// Send POST output.
			printout = new DataOutputStream (urlConn.getOutputStream ());

			printout.writeBytes (data);
			printout.flush ();
			printout.close ();

			// Get response data.
			input = new DataInputStream (urlConn.getInputStream ());

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create a new ContentHandler and apply it to the XML-Reader*/
			DepartureBoardSAXHandler theHandler = new DepartureBoardSAXHandler();
			xr.setContentHandler(theHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(input));		

			/* Parsing has finished. */
			return theHandler.getData();

		} catch (Exception e) {
			throw new RemoteException("While getting departures for " + CRS,e);
		}
	}

	public static ServiceBoard getArrivals(String CRS, int numArrs) throws RemoteException {

		try { // Required for the I/O
			URL	url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream	input;

			// URL of CGI-Bin script.
			url = new URL (BOARDS_ADDRESS);

			// URL connection channel.
			urlConn = url.openConnection();

			// Let the run-time system (RTS) know that we want input.
			urlConn.setDoInput (true);

			// Let the RTS know that we want to do output.
			urlConn.setDoOutput (true);

			// No caching, we want the real thing.
			urlConn.setUseCaches (false);  

			String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
				+ "<GetArrivalBoardRequest xmlns=\"http://thalesgroup.com/RTTI/2008-02-20/ldb/types\">"
				+ "<numRows>" + numArrs + "</numRows>"
				+ "<crs>" + CRS + "</crs>"
				+ "</GetArrivalBoardRequest></soap:Body></soap:Envelope>";


			// Specify the content type.
			urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			urlConn.setRequestProperty("Content-Length", Integer.toString(data.length()));

			// Send POST output.
			printout = new DataOutputStream (urlConn.getOutputStream ());

			printout.writeBytes (data);
			printout.flush ();
			printout.close ();

			// Get response data.
			input = new DataInputStream (urlConn.getInputStream ());

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create a new ContentHandler and apply it to the XML-Reader*/
			DepartureBoardSAXHandler theHandler = new DepartureBoardSAXHandler();
			xr.setContentHandler(theHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(input));		

			/* Parsing has finished. */
			return theHandler.getData();

		} catch (Exception e) {
		  throw new RemoteException("While getting arrivals for " + CRS,e);
		}
	}

	public static ServiceBoard getArrivalsDepartures(String CRS, int numServices) throws RemoteException {

		try { // Required for the I/O
			URL	url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream	input;

			// URL of CGI-Bin script.
			url = new URL (BOARDS_ADDRESS);

			// URL connection channel.
			urlConn = url.openConnection();

			// Let the run-time system (RTS) know that we want input.
			urlConn.setDoInput (true);

			// Let the RTS know that we want to do output.
			urlConn.setDoOutput (true);

			// No caching, we want the real thing.
			urlConn.setUseCaches (false);  

			String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
				+ "<GetArrivalDepartureBoardRequest xmlns=\"http://thalesgroup.com/RTTI/2008-02-20/ldb/types\">"
				+ "<numRows>" + numServices + "</numRows>"
				+ "<crs>" + CRS + "</crs>"
				+ "</GetArrivalDepartureBoardRequest></soap:Body></soap:Envelope>";


			// Specify the content type.
			urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			urlConn.setRequestProperty("Content-Length", Integer.toString(data.length()));

			// Send POST output.
			printout = new DataOutputStream (urlConn.getOutputStream ());

			printout.writeBytes (data);
			printout.flush ();
			printout.close ();

			// Get response data.
			input = new DataInputStream (urlConn.getInputStream ());

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create a new ContentHandler and apply it to the XML-Reader*/
			DepartureBoardSAXHandler theHandler = new DepartureBoardSAXHandler();
			xr.setContentHandler(theHandler);

			/* Parse the xml-data from our URL. */
			xr.parse(new InputSource(input));		

			/* Parsing has finished. */
			return theHandler.getData();

		} catch (Exception e) {
		  throw new RemoteException("While getting arrivals and departures for " + CRS,e);
		}
	}

	public static ServiceDetails queryDetails(String serviceID) throws IOException, SAXException {
	
		URL	url;
		URLConnection urlConn;
		DataOutputStream printout;
		DataInputStream	input;

		// URL of CGI-Bin script.
		url = new URL (BOARDS_ADDRESS);

		// URL connection channel.
		urlConn = url.openConnection();

		// Let the run-time system (RTS) know that we want input.
		urlConn.setDoInput (true);

		// Let the RTS know that we want to do output.
		urlConn.setDoOutput (true);

		// No caching, we want the real thing.
		urlConn.setUseCaches (false);  

		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://thalesgroup.com/RTTI/2007-10-10/ldb/types\">"
			+"<soapenv:Header/>"
			+"<soapenv:Body>"
			+"<typ:GetServiceDetailsRequest>"
			+"<typ:serviceID>" + serviceID + "</typ:serviceID>"
			+" </typ:GetServiceDetailsRequest>"
			+"</soapenv:Body>"
			+"</soapenv:Envelope>";

		// Specify the content type.
		urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		urlConn.setRequestProperty("Content-Length", Integer.toString(data.length()));

		// Send POST output.
		printout = new DataOutputStream (urlConn.getOutputStream ());

		printout.writeBytes (data);
		printout.flush ();
		printout.close ();

		// Get response data.
		input = new DataInputStream (urlConn.getInputStream ());

		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = null;
		try {
			sp = spf.newSAXParser();
		} catch (ParserConfigurationException e) {
			throw new SAXException(e);
		}

		/* Get the XMLReader of the SAXParser we created. */
		XMLReader xr = sp.getXMLReader();

		/* Create a new ContentHandler and apply it to the XML-Reader*/
		ServiceDetailsSAXHandler theHandler = new ServiceDetailsSAXHandler();
		xr.setContentHandler(theHandler);

		/* Parse the xml-data from our URL. */
		xr.parse(new InputSource(input));	

		return theHandler.getDetails();
	}
	public static ServiceDetails getServiceDetails(String serviceID) throws RemoteException {

		try { // Required for the I/O
			
			ServiceDetails d = queryDetails(serviceID);
			
			System.out.println("After getdetails: "+d.nextStations);


			// ================================= ADD ON BRANCHING ROUTES ================================

			// Get the trunk route
			Vector<CallingPoint> trunk;
			if(d.nextStations.size() > 0) {

				trunk = d.nextStations.get(0);

				boolean firstroute = true;

				// Loop through each branched route
				for(Vector<CallingPoint> branch : d.nextStations) {

					if(!firstroute){ 

						// Get where we start
						CallingPoint branchPoint = branch.get(0);

						// Scan through trunk finding where we are
						for(CallingPoint trunkStation : trunk) {

							if(trunkStation.equals(branchPoint)) {
								trunkStation.branches.add(branch);
								break;
							}
						}

					}

					firstroute = false;
				}
			} else {
				trunk = new Vector<CallingPoint>();
			}

			d.entireRestRoute = trunk;

			System.out.println("After first bit: "+d.nextStations);

			// ================================= CREATE LIST OF CALLINGPOINTRENDER OBJECTS ================================

			// Assemble entire journey
			LinkedList<CallingPointRender> journey = new LinkedList<CallingPointRender>();


			// add the prior stops
			if(d.previousStations != null)
				for(CallingPoint p : d.previousStations)
					journey.add(new CallingPointRender(p));

			// add current station
			CallingPoint curr = new CallingPoint();
			curr.location = d.theStation;
			curr.sta = d.sta;
			curr.eta = d.eta;
			curr.std = d.std;
			curr.etd = d.etd;
			CallingPointRender rp = new CallingPointRender(curr);
			rp.isCurrStation = true;
			journey.add(rp);

			// Loop through rest of the route
			for(CallingPoint p : d.entireRestRoute) {

				// Add station to the renderitems list
				CallingPointRender cp = new CallingPointRender(p);

				journey.add(cp);

				// Does this point have any branches off? If so add render points
				for(Vector<CallingPoint> branch : p.branches) {

					CallingPointRender newcp = new CallingPointRender(null);
					newcp.isTrainSplit = true;
					newcp.branchRoute = branch;
					journey.add(newcp);

				}

			}

			// First station in journey has starting icon
			journey.get(0).isFirst = true;

			// store in service details
			d.entireJourney = journey;

			System.out.println("return: "+d.nextStations);

			// add all stations after with a gap
			return d;


		} catch (Exception e) {
		  throw new RemoteException("While getting service details for " + serviceID,e);
		}
	}
	
	

}
