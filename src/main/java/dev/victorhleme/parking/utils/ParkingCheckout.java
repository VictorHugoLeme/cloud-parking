package dev.victorhleme.parking.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingCheckout {

    private static final int ONE_HOUR = 60;
    private static final int TWENTY_FOUR_HOUR = ONE_HOUR * 24;

    public static final Double ONE_HOUR_VALUE = 5.0;
    public static final Double ADDITIONAL_PER_HOUR_VALUE = 2.0;
    public static final Double DAY_VALUE = 20.0;

    public static Double calculatePrice(LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();
        if (minutes <= ONE_HOUR) {
            return ONE_HOUR_VALUE;
        } else if (minutes < TWENTY_FOUR_HOUR) {
            return ONE_HOUR_VALUE + ADDITIONAL_PER_HOUR_VALUE * Math.ceil((minutes - ONE_HOUR) / ONE_HOUR);
        } else {
            return DAY_VALUE * Math.ceil((minutes) / TWENTY_FOUR_HOUR);
        }

    }
}
