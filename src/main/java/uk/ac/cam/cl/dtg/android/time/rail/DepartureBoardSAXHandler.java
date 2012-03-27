package uk.ac.cam.cl.dtg.android.time.rail;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * SAX handler for GetStops
 * @author dt316
 *
 */
public class DepartureBoardSAXHandler extends DefaultHandler {


	// Temp storage
	StringBuilder sb = new StringBuilder();	
	
	TrainService service;
	List<TrainService> allServices;
	ServiceBoard board;
	
	boolean isOrigin = false;
	boolean isDest = false;
	
	Station currDest;
	
	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		
		allServices = new LinkedList<TrainService>();
		board = new ServiceBoard(new Station("",""),null);
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	/** Gets be called on opening tags like:
	 * <tag>
	 * Can provide attribute(s), when xml was like:
	 * <tag attribute="attributeValue">*/
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		//System.out.println("Start ele: "+qName);
		if(qName.length() == 0) qName = localName;
		
		if(qName.equals("service")) {

			service = new TrainService();
			service.destinations = new Vector<Station>();
			
		} else if(qName.equals("origin")) {

			isOrigin = true;
			service.origin = new Station("","");
			
		} else if(qName.equals("destination")) {

			isDest = true;
			
			
		} else if(qName.equals("location")) {
			
			if(isDest) currDest = new Station("","");
		}
		
		// empty the string
		sb.setLength(0);
	
	}

	/** Gets be called on closing tags like:
	 * </tag> */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
		
		String v = sb.toString();
		if(qName.length() == 0) qName = localName;
		
		//System.out.println("END TAG "+qName+" data: "+v);
		
		if(qName.equals("service")) {
			
			allServices.add(service);
			
		} else if(qName.equals("origin")) {

			isOrigin = false;			
			
		} else if(qName.equals("destination")) {

			isDest = false;					
			
		} else if(qName.equals("location")) {
			
			if(isDest) service.destinations.add(currDest);
			
		} else if(qName.equals("locationName")) {

			if(isOrigin) service.origin.setName(v);
			if(isDest) currDest.setName(v);
			if(!isOrigin && !isDest) board.getStation().setName(v);
			
		} else if(qName.equals("crs")) {

			if(isOrigin) service.origin.setCRS(v);
			if(isDest) currDest.setCRS(v);
			if(!isOrigin && !isDest) board.getStation().setCRS(v);
			
		} else if(qName.equals("serviceID")) {

			service.setServiceID(v);
			
		} else if(qName.equals("std")) {

			service.setStd(v);
			
		} else if(qName.equals("etd")) {

			service.setEtd(v);
		} else if(qName.equals("sta")) {

			service.setSta(v);
			
		} else if(qName.equals("eta")) {

			service.setEta(v);
			
		} else if(qName.equals("platform")) {

			service.setPlatform(v);
			
		} else if(qName.equals("operator")) {

			service.setTOC(v);
			
		} else if(qName.equals("via")) {

			service.setVia(v);
			
		} else if(qName.equals("isCircularRoute")) {

			if(v.equals("true")) service.setCircular(true);
			
		}

	}
	
	
	@Override
	public void characters(char ch[], int start, int length) {			
		
		char[] temp = new char[length];
		System.arraycopy(ch, start, temp, 0, length);						
		sb.append(temp);
	}
	
	public ServiceBoard getData() {
		
		board.setServices(allServices);
		return board;
	}
}

