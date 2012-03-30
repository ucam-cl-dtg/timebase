package uk.ac.cam.cl.dtg.android.time.rail;

import java.util.LinkedList;
import java.util.Vector;

public class ServiceDetails {

	public Vector<CallingPoint> previousStations;
	public Vector<Vector<CallingPoint>> nextStations;
	public Vector<CallingPoint> entireRestRoute;
	
	public LinkedList<CallingPointRender> entireJourney;
	
	public Station theStation;
	
	public String reason;
	
	public String eta;
	public String sta;
	public String etd;
	public String std;
	
	public ServiceDetails() {
		nextStations = new Vector<Vector<CallingPoint>>();
	}
}
