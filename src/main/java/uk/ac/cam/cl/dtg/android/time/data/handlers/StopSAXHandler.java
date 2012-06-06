package uk.ac.cam.cl.dtg.android.time.data.handlers;

import uk.ac.cam.cl.dtg.android.time.buses.BusStop;

public class StopSAXHandler extends AbstractStopsSAXHandler<BusStop> {

  @Override
  public BusStop getData() {
    return busStops.get(0);
  }

}
