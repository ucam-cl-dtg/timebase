package uk.ac.cam.cl.dtg.android.time.rail;

import java.util.Vector;

public class CallingPoint {

	public Station location;
	
	public String scheduled;
	public String actual;
	public String estimated;
	
	public String sta;
	public String eta;
	public String ata;
	public String std;
	public String etd;
	public String atd;	
	
	public boolean isCurrStation = false;
	
	public Vector<Vector<CallingPoint>> branches = new Vector<Vector<CallingPoint>>();
	
	@Override
  public String toString() {
		return location + " @ " + scheduled;
	}
	
	// TODO: needs to compare by time too
	public boolean equals(CallingPoint p) {
		System.out.println("EQUALS: is "+p+" equal to "+this);
		return (p.location.CRS.equals(location.CRS)) ? true : false;
	}
}
