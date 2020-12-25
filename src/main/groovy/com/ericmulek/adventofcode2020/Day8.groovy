package com.ericmulek.adventofcode2020

class Day8 {

    List<Instruction> instructionSet
    private Integer accumulator = 0

    private Set<Integer> executionTracker = []

    List<Instruction> instructionSetBackup

    boolean eofHit = false

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
                eofHit = true
            } else if (executionTracker.contains(index)) {
                proceed = false
            }
        }
        accumulator
    }

    //this was a terrible way to do this. the test harness should wire in a Day8 and blow it away with each test
    //Failing to clear out the state was the root of all my problems.
    Integer findBadInstruction() {
        int result = 0
        instructionSetBackup = instructionSet.clone() as List
        Set<Integer> suspectIndexes = []
        instructionSet.eachWithIndex { Instruction entry, int i ->
            if (entry.operation in [Operation.JMP, Operation.NOP]) {
                suspectIndexes << i
            }
        }
        suspectIndexes.find {
            instructionSet[it] = determineNewInstruction(instructionSet[it])
            result = getValueBeforeLoop()
            if (eofHit) {
                println "found it! result:$accumulator bad operation was \"${instructionSetBackup[it].operation} ${instructionSetBackup[it].argument}\" at line $it."
                reset()
                true
            } else {
                reset()
                false
            }
        }.tap {
            it
        }
        result
    }

    Instruction determineNewInstruction(Instruction instruction) {
        Operation temp = instruction.operation == Operation.JMP ?  Operation.NOP : Operation.JMP
        new Instruction(operation:temp, argument:instruction.argument)
    }

    void reset(){
        instructionSet = instructionSetBackup.clone() as List
        executionTracker = []
        eofHit = false
        accumulator = 0
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
