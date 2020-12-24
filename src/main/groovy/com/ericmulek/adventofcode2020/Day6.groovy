package com.ericmulek.adventofcode2020

class Day6 {

    Integer countOfYesAnswers(List<List<String>> groups) {
        groups.collect {List<String> groupAnswers ->
            groupAnswers*.toList().flatten().unique().size()
        }.sum() as Integer
    }

    Integer countOfAnswersPart2(List<List<String>> groups) {
        groups.collect {List<String> groupAnswers ->
            //get unique set of answered questions
            groupAnswers*.toList().flatten().unique().collectEntries { String question ->
                groupAnswers.every {
                    it.contains(question)
                }.with {
                    [(question), it] //create map entry with whether the question was answered by everyone
                }
            }.with { Map<String, Boolean> answers ->
                answers.count { //count how many questions were answered by everyone in the group
                    it.value
                }
            }
        }.sum() as Integer
    }
}

class DataParser {

    static final String RESOURCES_DIR = "src/main/resources"

    List<List<String>> process(String fileName) {
        List<List<String>> groupAnswers = []
        List<String> buffer = []
        new File("$RESOURCES_DIR/$fileName").eachLine {
            if (it) {
                buffer << it
            } else {
                groupAnswers << buffer
                buffer = []
            }
        }
        if (buffer) { //eachLine doesn't process white space at end of file
            groupAnswers << buffer
        }
        groupAnswers
    }

}
