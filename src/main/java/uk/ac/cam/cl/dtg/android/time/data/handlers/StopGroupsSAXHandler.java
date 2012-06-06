package uk.ac.cam.cl.dtg.android.time.data.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cam.cl.dtg.android.time.buses.StopGroup;

/**
 * SAX handler for GetStops
 * @author dt316
 *
 */
public class StopGroupsSAXHandler extends DefaultHandler {


	// Temp storage
	private StopGroup currentGroup;
	StringBuilder sb = new StringBuilder();		
	private List<StopGroup> parsedData;

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		parsedData = new ArrayList<StopGroup>();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		
		if(qName.length() == 0) qName = localName;
		
		if(qName.equals("group")) {
			
			currentGroup = new StopGroup(null, 0, 0);
			
		}

		// empty the string
		sb.setLength(0);

	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
		
		String v = sb.toString();
		if(qName.length() == 0) qName = localName;
		
	//	System.out.println("End element: localname: "+localName+" qname: "+qName+" value: "+v);
		
		if(qName.equals("name")) {
			
			currentGroup.setName(v);
			
		} else if(qName.equals("ref")) {
			
			currentGroup.setRef(v);
			
		} else if(qName.equals("lat")) {
			
			currentGroup.setLatitude((Double.valueOf(v)));
			
		} else if(qName.equals("long")) {
			
			currentGroup.setLongitude((Double.valueOf(v)));
			
		} else if(qName.equals("group")) {
			
			parsedData.add(currentGroup);
			
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
	
	public List<StopGroup> getData() {
		return parsedData;
	}
}

