package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day6Spec extends Specification {

    Day6 systemUnderTest

    List<List<String>> groupAnswers

    void setup() {
        groupAnswers = new DataParser().process('day6Dataset.txt')
        systemUnderTest = new Day6()
    }

    void "Part 1, Count number of questions answered yes per group"() {
        when:
        int result = systemUnderTest.countOfYesAnswers(groupAnswers)

        then:
        result == 6521
    }

    void "Part 2, Count number of questions everyone answered yes to"() {
        when:
        int result = systemUnderTest.countOfAnswersPart2(groupAnswers)

        then:
        result == 3305
    }
}
