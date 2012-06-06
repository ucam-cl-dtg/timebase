package uk.ac.cam.cl.dtg.android.time.data.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.ac.cam.cl.dtg.android.time.buses.*;

/**
 * SAX handler for individual stop info
 * 
 * @author dt316
 * 
 */
public abstract class AbstractStopsSAXHandler<T> extends DefaultHandler implements
    SAXDataHandler<T> {

  // Temp storage
  private StringBuilder sb = new StringBuilder();
  private BusStop currentStop;
  protected List<BusStop> busStops;

  // ===========================================================
  // Methods
  // ===========================================================
  @Override
  public void startDocument() throws SAXException {
    busStops = new ArrayList<BusStop>();
  }

  /**
   * Gets be called on opening tags like: <tag> Can provide attribute(s), when xml was like: <tag
   * attribute="attributeValue">
   */
  @Override
  public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
      throws SAXException {

    if (qName.length() == 0)
      qName = localName;

    if (qName.equals("stop")) {

      currentStop = new BusStop("", 0, 0, "");

    }

    // empty the string
    sb.setLength(0);

  }

  /**
   * Gets be called on closing tags like: </tag>
   */
  @Override
  public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

    String v = sb.toString();
    if (qName.length() == 0)
      qName = localName;

    if (qName.equals("common")) {

      currentStop.setName(v);

    } else if (qName.equals("atco")) {

      currentStop.setAtcoCode(v);

    } else if (qName.equals("naptan")) {

      currentStop.setNaptanCode(v);

    } else if (qName.equals("lat")) {

      currentStop.setLatitude(Double.parseDouble(v));

    } else if (qName.equals("long")) {

      currentStop.setLongitude(Double.parseDouble(v));

    } else if (qName.equals("stop")) {

      busStops.add(currentStop);

    } else if (qName.equals("error")) {

      throw new SAXException(v);

    }

  }

  @Override
  public void characters(char ch[], int start, int length) {
    char[] temp = new char[length];
    System.arraycopy(ch, start, temp, 0, length);
    sb.append(temp);
  }
}
