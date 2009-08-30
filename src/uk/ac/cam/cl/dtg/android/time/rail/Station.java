package uk.ac.cam.cl.dtg.android.time.rail;

public class Station {

	String name;
	String CRS;
	
	public Station(String name, String CRS) {
		super();
		this.name = name;
		this.CRS = CRS;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCRS() {
		return CRS;
	}
	public void setCRS(String cRS) {
		CRS = cRS;
	}
	
}
