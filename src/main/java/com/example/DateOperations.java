package com.example;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Created by z003rv2s on 26.01.2017.
 */
public class DateOperations {

    public void testDate() {

        Instant now = Instant.now();
        Instant prev = Instant.MIN;

        Duration dur = Duration.between(prev, now);

        System.out.println("Duration " + dur.toDays());

        LocalDate birth = LocalDate.of(1987, Month.MARCH, 7);
        LocalDate today = LocalDate.now();

        Period per = Period.between(birth, today);

        System.out.println("My Age : " + per.getYears() + " , " + per.getMonths() + " months");
        System.out.println("My Age : " + per.get(ChronoUnit.YEARS) + " , " + per.get(ChronoUnit.MONTHS) + " months");
        System.out.println("Total months : " + birth.until(today, ChronoUnit.MONTHS));

        LocalTime nowT = LocalTime.now();
        LocalTime time = LocalTime.of(10,20);
        LocalTime bedTime = LocalTime.of(23, 0);
        LocalTime wakeup = bedTime.plusHours(8);

        System.out.println("Wakeup at " + wakeup);

    }
}
