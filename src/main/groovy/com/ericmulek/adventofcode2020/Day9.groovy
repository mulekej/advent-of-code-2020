package com.ericmulek.adventofcode2020

class Day9 {

    static final int MIN_CONTIGUOUS_RANGE = 2
    long preamble

    Long findFirstInvalidCheckDigit(List<Long> numbers) {
        (preamble..numbers.size() - 1).findAll { Long index ->
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

    Long part2(List<Long> numbers) {
        def result = 0
        def invalidNumber = findFirstInvalidCheckDigit(numbers)
        for (int x = 0; x < numbers.size() - MIN_CONTIGUOUS_RANGE && !result; x++) {
            for (int y = x + MIN_CONTIGUOUS_RANGE; y < numbers.size() && !result; y++) {
                numbers.subList(x, y).tap {
                    if (invalidNumber == it.sum()) {
                        result = it.min() + it.max()
                    }
                }
            }
        }
        result
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
