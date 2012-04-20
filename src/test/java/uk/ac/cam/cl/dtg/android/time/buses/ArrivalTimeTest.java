package uk.ac.cam.cl.dtg.android.time.buses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class ArrivalTimeTest {

  private static final long SLIDE = 20;
  private static final long MINUTE = 1000L * 60L;

  @Test
  public void due() {
    Date now = new Date();
    ArrivalTime due = new ArrivalTime("Due");
    assertTrue(String.format("%d,%d", now.getTime(), due.getTime()),
        due.getTime() - now.getTime() <= SLIDE);
  }

  @Test
  public void mins() {
    int[] minutes = {0, 1, 2, 5, 30, 60, 95, 832};
    for (int minute : minutes) {
      mins(minute);
      minsLF(minute);
    }
    mins(1);
  }

  private void mins(int minutes) {
    Date now = new Date();
    ArrivalTime mins = new ArrivalTime(minutes + " mins");
    assertTrue(String.format("%d,%d", now.getTime(), mins.getTime()), mins.getTime()
        - now.getTime() <= SLIDE + minutes * MINUTE);
  }

  private void minsLF(int minutes) {
    Date now = new Date();
    ArrivalTime mins = new ArrivalTime(minutes + " mins LF");
    assertTrue(String.format("%d,%d", now.getTime(), mins.getTime()), mins.getTime()
        - now.getTime() <= SLIDE + minutes * MINUTE);
  }

  @Test
  public void twentyFourHourFormat() {
    String[] times = {"12:31", "01:56", "09:21"};
    for (String time : times) {
      ArrivalTime at = new ArrivalTime(time);
      assertEquals(time, at.getArrivalTime());
    }
  }

}
