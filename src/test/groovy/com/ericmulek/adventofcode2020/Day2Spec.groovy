package com.ericmulek.adventofcode2020

import spock.lang.Specification

class Day2Spec extends Specification {

    Day2 systemUnderTest

    List<Password> passwords
    
    void setup() {
        systemUnderTest = new Day2()
        passwords = new PasswordLoader().preProcess('Day2Dataset.txt')
    }
    
    void "Part One, count valid passwords"() {
        given:
        systemUnderTest.passwordValidator = new TobogganPasswordValidator()

        when:
        int result = systemUnderTest.countValidPasswords(passwords)
        
        then:
        result == 393
    }

    void "Part 2, use revised Policy Validator"() {
        given:
        systemUnderTest.passwordValidator = new RevisedPasswordValidator()

        when:
        int result = systemUnderTest.countValidPasswords(passwords)

        then:
        result == 690
    }
}
