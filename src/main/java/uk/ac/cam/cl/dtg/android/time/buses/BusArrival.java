package uk.ac.cam.cl.dtg.android.time.buses;

import java.io.Serializable;

public class BusArrival implements Serializable {

	private static final long serialVersionUID = -9046897187435560242L;
	
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
	
	public BusArrival(String service, String dest, long dueTime) {
		serviceID = service;
		destination = dest;
		this.dueTime = new ArrivalTime(dueTime);
  }

  /**
   * 
   * @param service
   * @param destination
   * @param dueTime
   * @since 0.0.4
   */
  public BusArrival(String service, String destination, ArrivalTime dueTime) {
    this.serviceID = service;
    this.destination = destination;
    this.dueTime = dueTime;
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
	
	public void setDueTime(long dueTime) {
		this.dueTime = new ArrivalTime(dueTime);
	}

	@Override
  public String toString() {
		return serviceID + " to "+destination+" (" + dueTime + ")";
	}
}
