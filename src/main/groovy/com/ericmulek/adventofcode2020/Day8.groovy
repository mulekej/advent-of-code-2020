package com.ericmulek.adventofcode2020

class Day8 {

    List<Instruction> instructionSet
    private Integer accumulator = 0

    private Set<Integer> executionTracker = []

    Integer getValueBeforeLoop() {
        int index = 0
        boolean proceed = true
        while (proceed) {
            Instruction instruction = instructionSet[index]
            executionTracker << index
            switch (instruction.operation) {
                case (Operation.NOP):
                    index++
                    break
                case (Operation.ACC):
                    accumulator += instruction.argument
                    index++
                    break
                case (Operation.JMP):
                    index += instruction.argument
                    break
            }

            if (index >= instructionSet.size()) {
                proceed = false
            } else if (executionTracker.contains(index)) {
                proceed = false
            }
        }
        accumulator
    }
}

class InstructionReader {

    private static final String RESOURCES_DIR = "src/main/resources"

    String fileName

    List<Instruction> read() {
        new File("$RESOURCES_DIR/$fileName").readLines().collect { String line ->
            line.replace('+', '').split().with { String[] instruction ->
                Operation op = Operation.valueOf(instruction[0].toUpperCase())
                Integer argument = instruction[1].toInteger()
                new Instruction(operation:op, argument:argument)
            }
        }
    }
}

class Instruction {

    Operation operation
    int argument
}

enum Operation {

    ACC,
    JMP,
    NOP
}
