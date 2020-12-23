package com.ericmulek.adventofcode2020

import org.hibernate.validator.constraints.Range

import javax.validation.*
import javax.validation.constraints.Pattern
import java.lang.annotation.*

class Day4 {

    PassportValidator passportValidator

    Integer countValidPassports(List<Passport> passports) {
        passports.count {
            try {
                passportValidator.validate(it)
            } catch (ConstraintViolationException cve) {
                false
            }
        }
    }
}

class Passport {

    @Range(min = 1920l, max = 2002l)
    Integer byr //(Birth Year)
    @Range(min = 2010l, max = 2020l)
    Integer iyr //(Issue Year)
    @Range(min = 2020l, max = 2030l)
    Integer eyr //(Expiration Year)
    @ValidHeight
    String hgt //(Height)
    @Pattern(regexp = '#{1}[a-f0-9]{6}')
    String hcl //(Hair Color)
    @Pattern(regexp = 'amb|blu|brn|gry|grn|hzl|oth')
    String ecl //(Eye Color)
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
        validator.validate(passport).tap {
            it
        }.isEmpty() //should be no validation errors
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
                    byr:it.byr as Integer,
                    iyr:it.iyr as Integer,
                    eyr:it.eyr as Integer,
                    hgt:it.hgt,
                    hcl:it.hcl,
                    ecl:it.ecl,
                    pid:it.pid,
                    cid:it.cid,
            )
        }
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

    @Override
    boolean isValid(String value, ConstraintValidatorContext context) {
        String METRIC_UNIT_OF_MEASURE = 'cm'
        String IMPERIAL_UNIT_OF_MEASURE = 'in'

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
