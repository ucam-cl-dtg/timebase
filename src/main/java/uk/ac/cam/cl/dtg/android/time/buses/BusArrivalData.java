package uk.ac.cam.cl.dtg.android.time.buses;

import java.util.LinkedList;

public class BusArrivalData {

  private String naptanCode;
  private String atcoCode;
	private LinkedList<BusArrival> nextBuses = new LinkedList<BusArrival>();
	private String timestamp;
	private String information = "";
	private String stopName = "";
	
	public BusArrivalData() {
		
	}
	
	public BusArrivalData(String atcoCode, LinkedList<BusArrival> arrivals) {
		nextBuses = arrivals;
		this.atcoCode = atcoCode;
	}

  /**
   * @return the naptanCode
   */
  public String getNaptanCode() {
    return naptanCode;
  }

  /**
   * @param naptanCode the naptanCode to set
   */
  public void setNaptanCode(String naptanCode) {
    this.naptanCode = naptanCode;
  }

  /**
   * @return the atcoCode
   */
  public String getAtcoCode() {
    return atcoCode;
  }

  /**
   * @param atcoCode the atcoCode to set
   */
  public void setAtcoCode(String atcoCode) {
    this.atcoCode = atcoCode;
  }

  /**
   * @return the nextBuses
   */
  public LinkedList<BusArrival> getNextBuses() {
    return nextBuses;
  }

  /**
   * @param nextBuses the nextBuses to set
   */
  public void setNextBuses(LinkedList<BusArrival> nextBuses) {
    this.nextBuses = nextBuses;
  }

  /**
   * @return the stopName
   */
  public String getStopName() {
    return stopName;
  }

  /**
   * @param stopName the stopName to set
   */
  public void setStopName(String stopName) {
    this.stopName = stopName;
  }

  /**
   * @return the timestamp
   */
  public String getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return the information
   */
  public String getInformation() {
    return information;
  }

  /**
   * @param information the information to set
   */
  public void setInformation(String information) {
    this.information = information;
  }
}
