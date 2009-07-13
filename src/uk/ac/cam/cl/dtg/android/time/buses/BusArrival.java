package uk.ac.cam.cl.dtg.android.time.buses;

public class BusArrival {

	String serviceID;
	String destination;
	String dueTime;
	
	public BusArrival(String service, String dest, String due) {
		serviceID = service;
		dueTime = due;
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

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	public String toString() {
		return serviceID + " to "+destination+" (" + dueTime + ")";
	}
}
