package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day5Spec extends Specification {

    Day5 systemUnderTest

    List<String> seats

    void setup() {
        seats = new SeatDataLoader().preProcess('day5Dataset.txt')
        systemUnderTest = new Day5(bookedSeats: seats)
    }

    void "Part 1"() {
        when:
        long result = systemUnderTest.findMaxSeatId()

        then:
        result == 922
    }

    void "Part 2"() {
        when:
        long result = systemUnderTest.findMySeatId()

        then:
        result == 747
    }
}
