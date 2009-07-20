package uk.ac.cam.cl.dtg.android.time.buses;

import java.io.Serializable;

/**
 * Represents a bus stop
 * 
 * @author dt316
 *
 */
public class BusStop implements Serializable {
	

	private String Name = "";
	private double Latitude;
	private double Longitude;
	private String stopRef = "";
	private String smsCode = "";


	public BusStop(String name, double latitude, double longitude,
			String stopRef) {
		
		super();
		Latitude = latitude;
		Longitude = longitude;
		Name = name;
		this.stopRef = stopRef;
		
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
	
	public String toString() {
		return "\n"+Name+" ("+stopRef+", "+Latitude+"/"+Longitude+")";
		//return Name;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getSmsCode() {
		return smsCode;
	}
	
}
