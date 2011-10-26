package uk.ac.cam.cl.dtg.android.time.buses;

import java.util.LinkedList;

public class BusArrivalData {

	public String smsCode;
	public LinkedList<BusArrival> NextBuses = new LinkedList<BusArrival>();
	public String Timestamp;
	public String Information = "";
	public String stopName = ""; 
	
	public BusArrivalData() {
		
	}
	
	public BusArrivalData(String sms, LinkedList<BusArrival> arrivals) {
		NextBuses = arrivals;
		smsCode = sms;
	}
	
}
