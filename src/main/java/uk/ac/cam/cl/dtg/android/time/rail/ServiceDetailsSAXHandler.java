package uk.ac.cam.cl.dtg.android.time.rail;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * SAX handler for GetStops
 * @author dt316
 *
 */
public class ServiceDetailsSAXHandler extends DefaultHandler {


	// Temp storage
	StringBuilder sb = new StringBuilder();	

	ServiceDetails service;

	Vector currItinery;
	CallingPoint currCallingPoint;

	boolean isCallingPoint = false;
	boolean isBefore = false;

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {

		service = new ServiceDetails();
		service.theStation = new Station("","");

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

		if(qName.equals("callingPointList")) {
			currItinery = new Vector<CallingPoint>();
		} if(qName.equals("callingPoint")) {
			currCallingPoint = new CallingPoint();
			currCallingPoint.location = new Station("","");
			isCallingPoint = true;
		} else if(qName.equals("previousCallingPoints")) {
			isBefore = true;
		} else if(qName.equals("subsequentCallingPoints")) {
			isBefore = false;
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

		if(qName.equals("locationName")) {
			if(isCallingPoint)
			{
				currCallingPoint.location.setName(v);
			} else {
				service.theStation.setName(v);
			}
		} else if(qName.equals("crs")) {
			if(isCallingPoint) {
				currCallingPoint.location.setCRS(v);
			} else {
				service.theStation.setCRS(v);
			}
		} else if(qName.equals("st")) {
			if(isCallingPoint) currCallingPoint.scheduled = v;
		} else if(qName.equals("et")) {
			if(isCallingPoint) currCallingPoint.estimated = v;
		} else if(qName.equals("at")) {
			if(isCallingPoint) currCallingPoint.actual = v;
		} else if(qName.equals("sta")) {
			service.sta = v;
		} else if(qName.equals("eta")) {
			service.eta = v;
		}  else if(qName.equals("std")) {
			service.std = v;
		} else if(qName.equals("etd")) {
			service.etd = v;
		} else if(qName.equals("disruptionReason")) {
			service.reason = v;
		} else if(qName.equals("callingPoint")) {
			currItinery.add(currCallingPoint);
		} else if(qName.equals("callingPointList")) {
			//System.out.println("Ended point list. isBefore is: "+isBefore);
			//System.out.println("Contains: "+currItinery);
			if(isBefore) {
				service.previousStations = currItinery;
			} else {
				service.nextStations.add(currItinery);
				//System.out.println("nextstations is: "+service.nextStations);
			}
		}		
	}

	@Override
	public void characters(char ch[], int start, int length) {			

		char[] temp = new char[length];
		System.arraycopy(ch, start, temp, 0, length);						
		sb.append(temp);
	}

	public ServiceDetails getDetails() {
		return service;
	}

}

