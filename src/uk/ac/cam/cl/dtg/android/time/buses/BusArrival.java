package uk.ac.cam.cl.dtg.android.time.buses;

import java.io.Serializable;

public class BusArrival implements Serializable {

	String serviceID;
	String destination;
	ArrivalTime dueTime;
	
	public BusArrival(String service, String dest, String due) {
		serviceID = service;
		try {
			dueTime = new ArrivalTime(due);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		destination = dest;
	}
	
	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public ArrivalTime getDueTime() {
		return dueTime;
	}

	public void setDueTime(ArrivalTime dueTime) {
		this.dueTime = dueTime;
	}

	public String toString() {
		return serviceID + " to "+destination+" (" + dueTime + ")";
	}
}
