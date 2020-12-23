package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day3Spec extends Specification {

    Day3 systemUnderTest

    void setup() {
        List<List<String>> map = new Day3DataLoad().process('day3DataSet.txt', 300)
        systemUnderTest = new Day3(arealMap: map, space: '.', tree: '#')
    }

    void "Part 1, count number of trees hit on way down the coast"() {
        given:
        Point startingPosition = new Point(x: 0, y: 0)
        Point targetPosition = new Point(x: 3, y: 1)

        when:
        int result = systemUnderTest.countTreesOnRoute(startingPosition, targetPosition)

        then:
        result == 207
    }

    void "Part 2, get multiple value of trees hit given a list of slopes"() {
        given:
        Point startingPosition = new Point(x: 0, y: 0)
        List<Point> slopes = [
                new Point(x:1, y:1),
                new Point(x:3, y:1),
                new Point(x:5, y:1),
                new Point(x:7, y:1),
                new Point(x:1, y:2),
                ]
        when:
        long result = systemUnderTest.processMultipleRoutes(startingPosition, slopes)

        then:
        result == 2655892800

    }
}
