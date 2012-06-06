package uk.ac.cam.cl.dtg.android.time.buses;

import java.io.Serializable;

public abstract class AbstractStop implements Serializable{

  private static final long serialVersionUID = 1L;

  private String name = "";
  private double latitude;
  private double longitude;

  public AbstractStop(String name, double latitude, double longitude) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLongitude() {
    return longitude;
  }

  protected abstract String getRef();

  @Override
  public String toString() {
    return name + " (" + getRef() + ", " + latitude + "/" + longitude + ")\n";
  }
}
