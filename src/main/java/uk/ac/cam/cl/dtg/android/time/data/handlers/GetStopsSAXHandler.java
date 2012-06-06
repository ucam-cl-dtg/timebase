package uk.ac.cam.cl.dtg.android.time.data.handlers;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cam.cl.dtg.android.time.buses.BusStop;

/**
 * SAX handler for GetStops
 * @author dt316
 *
 */
public class GetStopsSAXHandler extends DefaultHandler implements SAXDataHandler<List<BusStop>> {


	// Temp storage
	private BusStop currentStop;
	StringBuilder sb = new StringBuilder();		
	private List<BusStop> parsedData;

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		parsedData = new LinkedList<BusStop>();
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
			
			currentStop = new BusStop("",0,0,"");
			
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
		
	//	System.out.println("End element: localname: "+localName+" qname: "+qName+" value: "+v);
		
		if(qName.equals("name")) {
			
			currentStop.setName(v);
			
		} else if(qName.equals("ref")) {
			
			currentStop.setAtcoCode(v);
			
		} else if(qName.equals("lat")) {
			
			currentStop.setLatitude((Double.valueOf(v)));
			
		} else if(qName.equals("long")) {
			
			currentStop.setLongitude((Double.valueOf(v)));
			
		} else if(qName.equals("stop")) {
			
			parsedData.add(currentStop);
			
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
	
	@Override
  public List<BusStop> getData() {
		return parsedData;
	}
}

