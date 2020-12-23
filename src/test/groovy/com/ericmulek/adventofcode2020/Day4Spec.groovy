package com.ericmulek.adventofcode2020

import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class Day4Spec extends Specification {

    Day4 systemUnderTest

    List<Passport> passports

    void setup() {
        passports = new PassportDataLoader().preProcess('day4Dataset.txt')
        systemUnderTest = new Day4()
    }

    void "Part 1, get number of valid passports"() {
        given:
        systemUnderTest.passportValidator = new SusPassportValidator()

        when:
        int result = systemUnderTest.countValidPassports(passports)

        then:
        result == 192
    }

    void "Part 2, Validate use refined rules"() {
        given:
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        systemUnderTest.passportValidator = new RefinedPassportValidator(validator: validator)

        when:
        int result = systemUnderTest.countValidPassports(passports)

        then:
        result == 101

    }
}
