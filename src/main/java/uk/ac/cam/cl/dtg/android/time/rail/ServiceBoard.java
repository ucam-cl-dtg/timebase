package uk.ac.cam.cl.dtg.android.time.rail;

import java.util.List;

public class ServiceBoard {

	Station theStation;	
	List<TrainService> services;
	
	public ServiceBoard(Station theStation, List<TrainService> services) {
		super();
		this.theStation = theStation;
		this.services = services;
	}

	public Station getStation() {
		return theStation;
	}

	public void setStation(Station theStation) {
		this.theStation = theStation;
	}

	public List<TrainService> getServices() {
		return services;
	}

	public void setServices(List<TrainService> services) {
		this.services = services;
	}
	
	
	
}
