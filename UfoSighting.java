package ufostats;

import java.util.Calendar;

/**
 * This class represents an UFO sighting.
 * <p>
 * Created on 10/8/2018 at 8:37 PM
 * </p>
 *
 * @author bnabin51@gmail.com
 */
public class UfoSighting {

    public int year;
    public int month; // Jan = 0, Dec = 11.
    public TimeOfDay timeOfDay;

    public String country;
    public String state;

    UfoSighting(String country, String state, Calendar calendar) {
        if (country.equals("")) country = "Unknown";
        this.country = country;

        if (state.equals("")) state = "Unknown";
        this.state = state;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        // Decrease 1 minute so that we can arrange only based on hour.
        calendar.add(Calendar.MINUTE, -1);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 0 && hour < 8) {
            this.timeOfDay = TimeOfDay.EARLY_MORNING;

        } else if (hour >= 8 && hour < 12) {
            this.timeOfDay = TimeOfDay.LATE_MORNING;

        } else if (hour >= 12 && hour < 16) {
            this.timeOfDay = TimeOfDay.AFTERNOON;

        } else if (hour >= 16 && hour < 20) {
            this.timeOfDay = TimeOfDay.EVENING;

        } else {
            this.timeOfDay = TimeOfDay.NIGHT;
        }
    }

}
