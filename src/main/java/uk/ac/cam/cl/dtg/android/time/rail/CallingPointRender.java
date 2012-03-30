package uk.ac.cam.cl.dtg.android.time.rail;

import java.util.Vector;

public class CallingPointRender {

	// A list item showing the current station
	public boolean isCurrStation = false;
	
	// An expandable list item allowing you to view a branched route
	public boolean isTrainSplit = false;
	
	// If this represents a branched route, this is the route
	public Vector<CallingPoint> branchRoute;

	// Is the first item on a routing
	public boolean isFirst = false;
	
	// Is the last item on a routing
	public boolean isLast = false;
	
	// Actual info about the item
	public CallingPoint point;

	
	public CallingPointRender(CallingPoint p) {
		point = p;
	}
}
