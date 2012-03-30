package uk.ac.cam.cl.dtg.android.time.buses;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a bus stop
 * 
 * @author dt316
 *
 */
public class BusStop implements Serializable {
	
	private static final long serialVersionUID = -2339048398866898322L;

	private String Name = "";
	private double Latitude;
	private double Longitude;
	private String stopRef = "";
	private String smsCode = "";
	private HashMap<String, Object> meta = new HashMap<String, Object>();


	public BusStop(String name, double latitude, double longitude,
			String stopRef) {
		
		super();
		Latitude = latitude;
		Longitude = longitude;
		Name = name;
		this.stopRef = stopRef;
		
	}
	
	public void setMeta(String tag, Object obj) {
		meta.put(tag, obj);
	}
	
	public Object getMeta(String tag) {
		return meta.get(tag);
	}

	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setStopRef(String stopRef) {
		this.stopRef = stopRef;
	}

	public String getStopRef() {
		return stopRef;
	}
	
	@Override
  public String toString() {
		return Name+" ("+stopRef+", "+Latitude+"/"+Longitude+")\n";
		//return Name;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getSmsCode() {
		return smsCode;
	}
	
}
