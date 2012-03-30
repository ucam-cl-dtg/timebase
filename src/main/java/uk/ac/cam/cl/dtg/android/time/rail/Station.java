package uk.ac.cam.cl.dtg.android.time.rail;

import java.io.Serializable;

public class Station implements Serializable {
	private static final long serialVersionUID = -4926840317091215157L;

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
	
	public boolean equals(Station s) {
		System.out.println("Is "+s+" equal to "+this);
		return (s.CRS == CRS);
	}
	@Override
  public String toString() {
		return getName() + " (" + getCRS() + ")";
	}
	
}
