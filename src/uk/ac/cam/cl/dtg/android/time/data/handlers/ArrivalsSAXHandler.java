package uk.ac.cam.cl.dtg.android.time.data.handlers;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cam.cl.dtg.android.time.buses.*;


/**
 * SAX handler for GetStops
 * @author dt316
 *
 */
public class ArrivalsSAXHandler extends DefaultHandler {


	// Temp storage
	StringBuilder sb = new StringBuilder();	
	BusArrivalData data;
	BusArrival currArrival;
	ArrivalTime arrTime;
	
	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		data = new BusArrivalData();
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
		
		if(qName.equals("arrivals")) {

			data.Timestamp = atts.getValue("timestamp");
			
		} else if(qName.equals("arrival")) {
			
			currArrival = new BusArrival("","",0);
			
		} else if(qName.equals("time")) {
			
			arrTime = new ArrivalTime(0);
		}
		
		//System.out.println(namespaceURI+"/"+localName+"/"+qName);
		
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
		
		if(qName.equals("service")) {
			
			currArrival.setServiceID(v);
			
		} else if(qName.equals("destination")) {
			
			currArrival.setDestination(v);
			
		} else if(qName.equals("name")) {
			
			data.stopName = v;
			
		} else if(qName.equals("smscode")) {
			
			data.smsCode = v;
			
		} else if(qName.equals("millis")) {
			
			arrTime.setTime(Long.parseLong(v));
			
		} else if(qName.equals("isdue")) {
			
			arrTime.isDue = Boolean.parseBoolean(v);
			
		} else if(qName.equals("islive")) {
			
			arrTime.isLiveData = Boolean.parseBoolean(v);
			
		} else if(qName.equals("time")) {
			
			currArrival.setDueTime(arrTime);
			
		} else if(qName.equals("arrival")) {
			
			data.NextBuses.add(currArrival);
			
		} else if(qName.equals("error")) {
			
			throw new SAXException(v);
			
		}

	}
	
	
	@Override
	public void characters(char ch[], int start, int length) {			
		
		char[] temp = new char[length];
		System.arraycopy(ch, start, temp, 0, length);			
		String v = String.valueOf(temp);			
		sb.append(temp);
	}
	
	public BusArrivalData getData() {
		return data;
	}
}

