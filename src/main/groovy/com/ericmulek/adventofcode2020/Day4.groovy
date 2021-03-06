package com.ericmulek.adventofcode2020

import org.hibernate.validator.constraints.Range

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Validator
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import java.lang.annotation.*

class Day4 {

    PassportValidator passportValidator

    Integer countValidPassports(List<Passport> passports) {
        passports.count {
            passportValidator.validate(it)
        }
    }
}

class Passport {

    @NotNull
    @Range(min = 1920l, max = 2002l)
    Integer byr //(Birth Year)
    @NotNull
    @Range(min = 2010l, max = 2020l)
    Integer iyr //(Issue Year)
    @NotNull
    @Range(min = 2020l, max = 2030l)
    Integer eyr //(Expiration Year)
    @NotNull
    @ValidHeight
    String hgt //(Height)
    @NotNull
    @Pattern(regexp = '#{1}[a-f0-9]{6}')
    String hcl //(Hair Color)
    @NotNull
    @Pattern(regexp = 'amb|blu|brn|gry|grn|hzl|oth')
    String ecl //(Eye Color)
    @NotNull
    @Pattern(regexp = '\\d{9}')
    String pid //(Passport ID)
    String cid //(Country ID)
}

interface PassportValidator {

    boolean validate(Passport passport)
}

class SusPassportValidator implements PassportValidator {

    //could use Java @NotNull for this, but suspect this will be more adaptable for part 2
    List<String> fieldsToIgnore = ['cid']

    @Override
    boolean validate(Passport passport) {
        passport.properties.findAll {
            !fieldsToIgnore.contains(it.key)
        }.tap {
            it
        }.every {
            it.value
        }
    }
}

class RefinedPassportValidator implements PassportValidator {

    Validator validator

    @Override
    boolean validate(Passport passport) {
        validator.validate(passport).each {
            println("${it.propertyPath}, ${it.invalidValue} :${it.message}")
        }.tap {
            it
        }.isEmpty() //should be no validation errors
    }
}

@Documented
@Constraint(validatedBy = HeightValidator)
@Target([ElementType.METHOD, ElementType.FIELD])
@Retention(RetentionPolicy.RUNTIME)
@interface ValidHeight {
    String message() default "Invalid Height"

    Class[] groups() default []

    Class[] payload() default []
}

class HeightValidator implements ConstraintValidator<ValidHeight, String> {

    static final String METRIC_UNIT_OF_MEASURE = 'cm'
    static final String IMPERIAL_UNIT_OF_MEASURE = 'in'

    @Override
    boolean isValid(String value, ConstraintValidatorContext context) {

        //could be better, use a enum or map to hold properties. Would like some DI but it just base java validation, not spring.
        if (value?.endsWith(METRIC_UNIT_OF_MEASURE)) {
            value.replace(METRIC_UNIT_OF_MEASURE, '').toInteger().with {
                it >= 150 && it <= 193
            }
        } else if (value?.endsWith(IMPERIAL_UNIT_OF_MEASURE)) {
            value.replace(IMPERIAL_UNIT_OF_MEASURE, '').toInteger().with {
                it >= 59 && it <= 76
            }
        } else {
            false
        }
    }
}

class PassportDataLoader {

    static final String RESOURCES_DIR = "src/main/resources"
    static final String FIELD_DELIMINATOR = ' '
    static final String K_V_DELIMINATOR = ':'

    List<Passport> preProcess(String fileName) {
        List passports = []
        List<String> passportBuffer = []
        new File("$RESOURCES_DIR/$fileName").eachLine {
            if (it) {
                passportBuffer << it
            } else {
                passports << processBuffer(passportBuffer)
                passportBuffer = []
            }
        }
        if (passportBuffer) { //eachLine doesn't process white space at end of file
            passports << processBuffer(passportBuffer)
        }
        passports
    }

    private Passport processBuffer(List<String> buffer) {
        buffer.collectMany {
            it.split(FIELD_DELIMINATOR) as List<String>
        }.collectEntries {
            it.split(K_V_DELIMINATOR).with {
                [(it[0]): it[1]]
            }
        }.with {
            new Passport(
                    byr: it.byr as Integer,
                    iyr: it.iyr as Integer,
                    eyr: it.eyr as Integer,
                    hgt: it.hgt,
                    hcl: it.hcl,
                    ecl: it.ecl,
                    pid: it.pid,
                    cid: it.cid,
            )
        }
    }
}
