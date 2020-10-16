package com.ohio.health;

import java.util.Scanner;

/**
 * BACKGROUND
 * This kata simulates a babysitter working and getting paid for one night. The rules are pretty straight forward.
 * <p>
 * The babysitter:
 * <p>
 * - starts no earlier than 5:00PM
 * - leaves no later than 4:00AM
 * - gets paid $12/hour from start-time to bedtime
 * - gets paid $8/hour from bedtime to midnight
 * - gets paid $16/hour from midnight to end of job
 * - gets paid for full hours (no fractional hours)
 * <p>
 * Feature
 * As a babysitter, in order to get paid for 1 night of work, I want to calculate my nightly charge
 */

public class Babysitter {
    public static final int MIN_START = 17, MAX_END = 4, DEF_BED = 21;
    // 5PM, 4AM, and 9PM respectively
    // Assuming 24 hour system, rollover at 24
    public static final int PAY_START = 12, PAY_AFTER_BED = 8, PAY_END = 16;
    // Pay rates
    String AMPM = "";
    private int startTime, bedTime, endTime;

    public Babysitter() { // default constructor
        this(MIN_START, DEF_BED, MAX_END);
    }

    public Babysitter(int startTime, int bedTime, int endTime) { // parametrized constructor
        this.startTime = startTime;
        this.endTime = endTime;
        this.bedTime = bedTime;
    }

    public static boolean isValidTime(int time) {
        // validating time, hours between 0 and 23
        return !(time <= 0 || time > 23);
    }

    public static boolean isInRange(int time) {
        // Two choices: Since you start no earlier than 5:00PM or 1700h,
        // and you end no later than 4:00AM or 0400h,
        // start times can be from 1700-2300h OR
        // 0000-0400h
        return ((time >= MIN_START && time <= 23) //1700-2300h
                || time <= MAX_END //0000-0400h
        );
    }

    public static int adjustTime(int time) {
        // if time is 0, it will be set as 12:00AM
        // if time is 12, it will be set as 12:00PM
        // Any time between 13 to 23 is set as 1:00PM - 11:00PM

        if (time == 0) {
            return time + 12;
        } else if (time < 12) {
            return time;
        } else {
            return time - 12;
        }
    }

    public static int calcAdjust(int time) {
        // 2 is considered 2:00 AM, but gives value of 14
        // 0 is considered 12:00 AM

        if (time <= 4) {
            time += 24;
        }
        return time;
    }

    public static int calculate(int startTime, int bedTime, int endTime) {
        System.out.println("BABYSITTER BILL:");
        int hoursToBed, hoursAfterBed, hoursToEnd;

        int startAdj = calcAdjust(startTime);
        int bedAdj = calcAdjust(bedTime);
        int endAdj = calcAdjust(endTime);

        // START TIME TO BED TIME
        hoursToBed = 0;
        if (!isInRange(startTime)) {
            System.out.println("You can begin no earlier than 5:00 PM");
        } else if (!isInRange(endTime)) {
            System.out.println("You can leave no later than 4:00 AM");
        } else {
            if (startAdj >= bedAdj || startAdj >= 24) {
                // if start time is after bed OR
                // start time is after midnight
                hoursToBed = 0;
            }
            if (endAdj <= bedAdj) { // if end time is before bed
                if (endAdj >= 24) hoursToBed = 24 - startAdj; // if end time is after midnight
                else hoursToBed = endAdj - startAdj; // if end time is before bed
            } else { // if end time is after bed
                if (bedAdj >= 24) hoursToBed = 24 - startAdj; // bed is after midnight
                else hoursToBed = bedAdj - startAdj; // bed is before midnight
            }
        }
        System.out.println("HOURS BETWEEN START AND BEDTIME: " + hoursToBed);

        // BED TIME TO MIDNIGHT
        hoursAfterBed=0;
        if (startAdj > bedAdj || bedAdj >= 24 || startAdj >= 24 || endAdj <= bedAdj) {
            // if start time is after bed OR
            // bed is at or after midnight OR
            // start is at or after midnight OR
            // end is at or before bed
            hoursAfterBed = 0;
        }
        if (endAdj <= 24) { // end is before midnight
            hoursAfterBed = endAdj - bedAdj;
        }
        if (bedAdj <= 24) { // bed is before midnight
            hoursAfterBed = 24 - bedAdj;
        }

        System.out.println("HOURS BETWEEN BEDTIME AND MIDNIGHT: " + hoursAfterBed);

        // MIDNIGHT TO END

        hoursToEnd = 0;
        if (endAdj <= 24 || startAdj >= endAdj) { // end is before midnight
            hoursToEnd = 0;
        }
        if (endAdj > 24) {
            hoursToEnd = endAdj - 24;
        }
        System.out.println("HOURS BETWEEN MIDNIGHT AND END: " + hoursToEnd);

        int bill = PAY_START * hoursToBed +
                PAY_AFTER_BED * hoursAfterBed +
                PAY_END * hoursToEnd;
        System.out.println("---------------------------");
        System.out.println("START-BED: $" + PAY_START + ".00 * " + hoursToBed + " hours = $" + PAY_START * hoursToBed + ".00");
        System.out.println("BED-12AM: $" + PAY_AFTER_BED + ".00 * " + hoursAfterBed + " hours = $" + PAY_AFTER_BED * hoursAfterBed + ".00");
        System.out.println("12AM-END: $" + PAY_END + ".00 * " + hoursToEnd + " hours = $" + PAY_END * hoursToEnd + ".00 +");
        System.out.println("---------------------------");
        System.out.println("$" + bill + ".00 TOTAL");
        return bill;
    }

    public static void main(String[] args) {
        Babysitter karen = new Babysitter();
        Scanner input = new Scanner(System.in);
        boolean validEnd, validStart, validBed;
        validEnd = validStart = validBed = false;
        System.out.println("Calculate the payrate");

        while (!validStart) {
            System.out.println("Enter start time (5PM - 3AM, 17h - 3h): ");
            while (!input.hasNextInt()) {
                String textInput = input.next();
                System.out.println(textInput + " is not a valid time.");
            }
            int nextStart = input.nextInt();
            karen.setStartTime(nextStart);
            validStart = isInRange(nextStart);
            if(!validStart) System.out.println("You can begin no earlier than 5:00 PM");
        }
        int start = karen.getStartTime();
        if (calcAdjust(start)>=24){
            System.out.println("Start time is at " + adjustTime(start) + ":00AM");
        } else {
            System.out.println("Start time is at " + adjustTime(start) + ":00PM");
        }

        while (!validBed) {
            System.out.println("Enter bed time (5PM - 4AM, 17h - 4h): ");
            while (!input.hasNextInt()) {
                String textInput = input.next();
                System.out.println(textInput + " is not a valid time.");
            }
            int nextBed = input.nextInt();
            karen.setBedTime(nextBed);
            validBed = isInRange(nextBed);

        }
        int bed = karen.getBedTime();
        if (calcAdjust(bed) >= 24){
            System.out.println("Bed time is at " + adjustTime(bed) + ":00AM");
        }else{
            System.out.println("Bed time is at " + adjustTime(bed) + ":00PM");
        }

        while (!validEnd) {
            System.out.println("Enter end time (6PM - 4AM, 18h - 4h): ");
            while (!input.hasNextInt()) {
                String textInput = input.next();
                System.out.println(textInput + " is not a valid time.");
            }
            int nextEnd = input.nextInt();
            karen.setEndTime(nextEnd);
            validEnd = isInRange(nextEnd);
            if(!validEnd) System.out.println("You can end no later than 4:00 AM");
        }
        int end = karen.getEndTime();
        if (calcAdjust(end) >= 24){
            System.out.println("End time is at " + adjustTime(end) + ":00AM");
        }else{
            System.out.println("End time is at " + adjustTime(end) + ":00PM");
        }

        int totalWage = calculate(start, bed, end);
        System.out.println("Babysitter is owed $" + totalWage + ".00 for tonight.");
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getBedTime() {
        return bedTime;
    }

    public void setBedTime(int bedTime) {
        this.bedTime = bedTime;
    }

    // Public getters for private values

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}