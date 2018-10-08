package counters;

import ufostats.UfoSighting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 10/8/2018 at 8:52 PM
 * </p>
 *
 * @author bnabin51@gmail.com
 */
public class YearCounter extends Thread {

    public static boolean running = false;
    public static Map<Integer, Integer> countMap = new HashMap<>();

    private int size;
    private List<UfoSighting> ufoSightings;

    public YearCounter(List<UfoSighting> ufoSightings) {
        this.ufoSightings = ufoSightings;
        this.size = ufoSightings.size();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        running = true;
        for (int i = 0; i < size; i++) {
            UfoSighting sighting = ufoSightings.get(i);
            int year = sighting.year;

            Integer count = countMap.get(year);
            if (count == null) count = 0;
            countMap.put(year, ++count);
        }
        running = false;
    }

}
