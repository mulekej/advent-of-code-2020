package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day7Spec extends Specification {

    Day7 systemUnderTest

    void setup() {
        [].sum()
        List<Bag> bags = new BagDataLoader().process('day7Dataset.txt')
        systemUnderTest = new Day7(bags: bags)
    }

    void "Part 1, find number of bags that can contain a shiny gold bag"() {
        when:
        int result = systemUnderTest.countBagsThatContain('shiny gold', [] as Set)

        then:
        result == 119
    }

    void "Part 2: find number of bags inside the target bag"() {
        when:
        int result = systemUnderTest.countBagInsideOfTargetBag('shiny gold', [] as Set).with {
            it - 1 // remove root level bag from the count
        }

        then:
        result == 155802
    }
}
