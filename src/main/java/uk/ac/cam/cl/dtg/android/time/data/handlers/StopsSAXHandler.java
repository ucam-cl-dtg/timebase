package uk.ac.cam.cl.dtg.android.time.data.handlers;

import java.util.List;

import uk.ac.cam.cl.dtg.android.time.buses.BusStop;

public class StopsSAXHandler extends AbstractStopsSAXHandler<List<BusStop>> {

  @Override
  public List<BusStop> getData() {
    return busStops;
  }

}
