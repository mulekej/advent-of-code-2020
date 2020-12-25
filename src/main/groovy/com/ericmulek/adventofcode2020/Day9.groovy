package com.ericmulek.adventofcode2020

class Day9 {

    long preamble

    Long findFirstInvalidCheckDigit(List<Long> numbers) {
        (preamble..numbers.size()-1).findAll { Long index ->
            isInvalid(numbers, index)
        }.with {
            numbers[it[0]]
        }
    }

    private boolean isInvalid(List<Long> numbers, long index) {
        numbers.subList(index - preamble as int, index as int).with { List<Long> sublist ->
            findValuesThatSumToCheckDigit(sublist, numbers[index])
        }
    }

    private boolean findValuesThatSumToCheckDigit(List<Long> numbers, long value) {
        Set<Long> seen = []
        numbers.find { Long number ->
            long remainder = value - number
            if (seen.contains(remainder)) {
                true
            } else {
                seen << number
                false
            }
        }.with {
            !it
        }
    }
}

class Day9Loader {

    private static final String RESOURCES_DIR = "src/main/resources"

    String fileName

    List<Long> read() {
        new File("$RESOURCES_DIR/$fileName").readLines().collect {
            it.toLong()
        }
    }
}
