package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day8Spec extends Specification {

    Day8 systemUnderTest

    void setup() {
        List<Instruction> instructionSet = new InstructionReader(fileName:'day8Dataset.txt').read()
        systemUnderTest = new Day8(instructionSet:instructionSet)
    }

    void "Part 1, Find accumulator value before loop kicks in."() {
        when:
        int result = systemUnderTest.valueBeforeLoop

        then:
        result == 1709
    }

    void "Part 2, find bad operation"() {
        when:
        int result = systemUnderTest.findBadInstruction()

        then:
        result == 1976
    }
}
