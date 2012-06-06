package uk.ac.cam.cl.dtg.android.time.buses;

public class StopGroup extends AbstractStop {

  private String ref;
  private String dist;
  public StopGroup(String name, double latitude, double longitude) {
    super(name, latitude, longitude);
  }

  @Override
  public String getRef() {
    return ref;
  }

  /**
   * @return the dist
   */
  public String getDist() {
    return dist;
  }

  /**
   * @param dist the dist to set
   */
  public void setDist(String dist) {
    this.dist = dist;
  }

  /**
   * @param ref the ref to set
   */
  public void setRef(String ref) {
    this.ref = ref;
  }

}
