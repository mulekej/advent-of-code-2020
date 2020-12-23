package com.ericmulek.adventofcode2020

class Day1 {

    Integer requiredSum

    Integer processExpenseReportPart1(List<Integer> expenses) {
        expenses.collect {Integer a ->
            expenses.find {Integer b ->
                (a + b) == requiredSum
            }?.with { Integer b ->
                a * b
            }
        }.find()
    }

    Integer processExpenseReportPart2(List<Integer> expenses) {
        expenses.collect {Integer a ->
            expenses.find {Integer b ->
                (requiredSum - a - b) in expenses
            }?.with { Integer b ->
                Integer remainingNumber = expenses.find{ it == (requiredSum - a - b)}
                remainingNumber * a * b
            }
        }.find()
    }
}
