package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day9Spec extends Specification {

    Day9 systemUnderTest

    void setup() {
        systemUnderTest = new Day9(preamble:25)
    }

    void "Part 1, find first invalid number"() {
        given:
        List<Long> temp = new Day9Loader(fileName:'day9Dataset.txt').read()

        when:
        long result = systemUnderTest.findFirstInvalidCheckDigit(temp)

        then:
        result == 27911108
    }

    void "Part 2"() {
        when:
        def result = systemUnderTest

        then:
        !result
    }
}
