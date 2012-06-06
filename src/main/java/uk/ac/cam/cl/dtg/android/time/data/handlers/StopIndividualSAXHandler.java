package uk.ac.cam.cl.dtg.android.time.data.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cam.cl.dtg.android.time.buses.*;


/**
 * SAX handler for individual stop info
 * @author dt316
 *
 */
public class StopIndividualSAXHandler extends DefaultHandler {


	// Temp storage
	StringBuilder sb = new StringBuilder();	
	BusStop stop;

	
	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		// Nothing to do
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
		
		if(qName.length() == 0) qName = localName;
		
		if(qName.equals("stop")) {

			stop = new BusStop("",0,0,"");
			
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
		
		
		if(qName.equals("name")) {
			
			stop.setName(v);
			
		} else if(qName.equals("ref")) {
			
			stop.setAtcoCode(v);
			
		} else if(qName.equals("lat")) {
			
			stop.setLatitude(Double.parseDouble(v));
			
		} else if(qName.equals("long")) {
			
			stop.setLongitude(Double.parseDouble(v));
			
		} else if(qName.equals("error")) {
			
			throw new SAXException(v);
			
		}

	}
	
	
	@Override
	public void characters(char ch[], int start, int length) {			
		char[] temp = new char[length];
		System.arraycopy(ch, start, temp, 0, length);						
		sb.append(temp);
	}
	
	public BusStop getData() {
		return stop;
	}
}

