package com.ericmulek.adventofcode2020

class Day2 {

    PasswordValidator passwordValidator

    int countValidPasswords(List<Password> passwords) {
        passwords.count {
            passwordValidator.validate(it)
        }
    }
}

class PasswordLoader {

    static final String RESOURCES_DIR = "src/main/resources"

    List<Password> preProcess(String fileName) {
        List passwords = []
        new File("$RESOURCES_DIR/$fileName").eachLine {
            String[] parts = it.split(' ')
            parts[0]
            Policy policy = getPolicy(parts[0], parts[1])
            passwords << new Password(policies: [policy], value: parts[2])
        }
        passwords
    }

    private Policy getPolicy(String range, String requiredCharacterString) {
        String requiredCharacter = requiredCharacterString.replace(':', '')
        List<Integer> acceptedRange = range.split('-')*.toInteger()
        new Policy(requiredCharacter: requiredCharacter, minRequired: acceptedRange[0], maxAllowed: acceptedRange[1])
    }
}

class Password {

    List<Policy> policies
    String value
}

class Policy {
    String requiredCharacter
    int minRequired
    int maxAllowed
}

interface PasswordValidator {

    boolean validate(Password password)
}
class TobogganPasswordValidator implements PasswordValidator {

    @Override
    boolean validate(Password password) {
        password.policies.every {Policy policy -> //every applied policy must be true
            password.value.toList().count{
                it == policy.requiredCharacter
            }.with { Number matchedChars ->
                (policy.minRequired..policy.maxAllowed).containsWithinBounds(matchedChars)
            }
        }
    }
}

class RevisedPasswordValidator implements PasswordValidator {

    @Override
    boolean validate(Password password) {
        password.policies.every {Policy policy -> //every applied policy must be true
            List<String> characters = password.value.toList()
            characters[policy.minRequired] == policy.requiredCharacter ||
                    characters[policy.maxAllowed] == policy.requiredCharacter
            /*
            todo given the revised policy definition, minRequired and maxAllowed are misnomers,
             code should be refactored at a later date it either be more generic or reflect the new definitions.
             Don't have time to do it now, really need to catch my toboggan and get to the airport.
             */
        }
    }
}
