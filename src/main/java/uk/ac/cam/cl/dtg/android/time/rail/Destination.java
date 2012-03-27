package uk.ac.cam.cl.dtg.android.time.rail;

import uk.ac.cam.cl.dtg.android.time.buses.ArrivalTime;

public class Destination implements Comparable<Destination> {

	TrainService service;
	CallingPoint dest;
	
	public Destination(TrainService service, CallingPoint dest) {
		super();
		this.service = service;
		this.dest = dest;
	}
	
	public String toString() {
		
		return service.std+" to "+service.destinations.firstElement().name+" arrives at"+dest.scheduled;
	}

	@Override
	public int compareTo(Destination other) {		
		ArrivalTime us = new ArrivalTime(dest.scheduled);
		ArrivalTime them = new ArrivalTime(other.dest.scheduled);
		
		return (them.getTime() > us.getTime()) ? -1 : 1;
		
	}
	
}
