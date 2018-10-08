package ufostats;

import counters.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created on 10/8/2018 at 7:33 PM
 *
 * @author bnabin51@gmail.com
 */
public class Main {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        read();
        showResults();
        System.out.println("Elapsed: " + (System.currentTimeMillis() - time));
    }

    private static void read() {
        try {
            FileInputStream fis = new FileInputStream("scrubbed.csv");
            Scanner in = new Scanner(fis);

            if (in.hasNextLine()) {
                // to ignore first line.
                in.nextLine();
            } else {
                System.out.println("Nothing in the file.");
                return;
            }

            int readCount = 0;

            while (in.hasNextLine()) {

                List<UfoSighting> ufoSightings = new ArrayList<>(1000);

                readCount += 1000;
                System.out.println("Reading up to " + readCount);

                for (int i = 0; i < 1000; i++) {

                    if (in.hasNextLine()) {
                        try {
                            String line = in.nextLine();
                            String[] tokens = line.split(",");

                            String dateTimeStr = tokens[0];
                            // String city = tokens[1];
                            String state = tokens[2];
                            String country = tokens[3];
                            // String shape = tokens[4];
                            // String durationSec = tokens[5];
                            // String durationString = tokens[6];
                            // String comments = tokens[7];
                            // String postedDate = tokens[8];
                            // String latitude = tokens[9];
                            // String longitude = tokens[10];

                            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                            Date date = dateFormat.parse(dateTimeStr);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);

                            UfoSighting sighting = new UfoSighting(country, state, calendar);
                            ufoSightings.add(sighting);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else break;
                }

                new CountryCounter(ufoSightings).start();
                new StateCounter(ufoSightings).start();
                new YearCounter(ufoSightings).start();
                new MonthCounter(ufoSightings).start();
                new TimeOfDayCounter(ufoSightings).start();
            }

            // wait until all counting threads finish.
            while (CountryCounter.running || MonthCounter.running || StateCounter.running ||
                    TimeOfDayCounter.running || YearCounter.running) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showResults() {
        Map<String, Integer> countryCounts = CountryCounter.countMap;
        System.out.println("\nCountries: " + countryCounts);

        Map<String, Integer> stateCounts = StateCounter.countMap;
        System.out.println("\nStates: " + stateCounts);

        Map<Integer, Integer> yearCounts = YearCounter.countMap;
        System.out.println("\nYears: " + yearCounts);

        Map<Integer, Integer> monthCounts = MonthCounter.countMap;
        System.out.println("\nMonths (0=Jan): " + monthCounts);

        Map<TimeOfDay, Integer> timeOfDayCounts = TimeOfDayCounter.countMap;
        System.out.println("Times of day: " + timeOfDayCounts);
    }

}
