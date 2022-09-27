package dev.victorhleme.parking.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkingCheckoutTest {

    @ParameterizedTest
    @MethodSource("times")
    void calculatePrice(LocalDateTime entry, LocalDateTime exit, Double expected) {
        Double actual = ParkingCheckout.calculatePrice(entry, exit);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> times() {
        LocalDateTime now = LocalDateTime.now();
        return
            Stream.of(
                Arguments.of(now, now.plusMinutes(59), 5.0),
                Arguments.of(now, now.plusMinutes(61), 5.0),
                Arguments.of(now, now.plusMinutes(121), 7.0),
                Arguments.of(now, now.plusDays(1).minusMinutes(1), 49.0),
                Arguments.of(now, now.plusDays(1), 20.0),
                Arguments.of(now, now.plusDays(2), 40.0)
            );
    }
}