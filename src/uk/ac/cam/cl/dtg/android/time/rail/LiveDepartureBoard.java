package uk.ac.cam.cl.dtg.android.time.rail;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import uk.ac.cam.cl.dtg.android.time.data.handlers.GetStopsSAXHandler;

public class LiveDepartureBoard {

	public static ServiceBoard getDepartures(String CRS, int numDeps) {

		try { // Required for the I/O
			URL	url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream	input;

			// URL of CGI-Bin script.
			url = new URL ("http://www.livedepartureboards.co.uk/ldbws/ldb2.asmx");

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
			System.out.println("Exception thrown");
			System.out.println(e.getMessage());
			return null;
			
		}
	}
	
	public static ServiceBoard getArrivals(String CRS, int numArrs) {

		try { // Required for the I/O
			URL	url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream	input;

			// URL of CGI-Bin script.
			url = new URL ("http://www.livedepartureboards.co.uk/ldbws/ldb2.asmx");

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
			System.out.println("Exception thrown");
			System.out.println(e.getMessage());
			return null;
			
		}
	}
}
