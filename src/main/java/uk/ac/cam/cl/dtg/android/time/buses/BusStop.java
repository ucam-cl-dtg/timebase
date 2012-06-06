package uk.ac.cam.cl.dtg.android.time.buses;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a bus stop
 * 
 * @author dt316
 * 
 */
public class BusStop extends AbstractStop implements Serializable {

  private static final long serialVersionUID = -2339048398866898322L;

  private String atcoCode = "";
  private String naptanCode = "";
  private HashMap<String, Object> meta = new HashMap<String, Object>();

  public BusStop(String name, double latitude, double longitude, String atcoCode) {

    super(name, latitude, longitude);
    this.atcoCode = atcoCode;

  }

  public void setMeta(String tag, Object obj) {
    meta.put(tag, obj);
  }

  public Object getMeta(String tag) {
    return meta.get(tag);
  }

  public void setAtcoCode(String atco) {
    this.atcoCode = atco;
  }

  public String getAtcoCode() {
    return atcoCode;
  }

  public void setNaptanCode(String naptan) {
    this.naptanCode = naptan;
  }

  public String getNaptanCode() {
    return naptanCode;
  }

  @Override
  public String getRef() {
    return getAtcoCode();
  }
}
