package com.ericmulek.adventofcode2020

class Day7 {

    List<Bag> bags

    Integer countBagsThatContain(String targetBag, Set<String> matchedBags) {
        int count = 0
        bags.findAll {
            it.content.keySet().contains(targetBag)
        }.findAll {
            !matchedBags.contains(it.type)
        }.tap {
            count += it.size()
            matchedBags.addAll(it*.type as List)
        }.each {
            count += countBagsThatContain(it.type, matchedBags)
        }
        count
    }

    Integer countBagInsideOfTargetBag(String targetBag, Set<String> matchedBags) {
        int count = 1
        bags.find {
            it.type == targetBag
        }.tap { Bag bag ->
//            count += it.content.values().sum() ?: 0
            bag.content.each { String key, Integer val ->
                def temp = countBagInsideOfTargetBag(key, matchedBags)
                count += (temp * val)
            }
//            matchedBags.addAll(it.content.keySet())
        }
        count
    }
}

class BagDataLoader {

    static final String RESOURCES_DIR = "src/main/resources"

    List<Bag> process(String fileName) {
        Set<String> masterList = []
        new File("$RESOURCES_DIR/$fileName").readLines().collect {
            List<String> line = it.split('contain')
            String type = line[0].replace('bags', '').trim()
            masterList << type
            Map<String, Integer> validContents = getValidBagContents(line[1].trim())
            new Bag(type: type, content: validContents)
        }
    }

    private Map<String, Integer> getValidBagContents(String value) {
        if (value.startsWith('no other')) {
            [:]
        } else {
            value.replace('.', '').trim().split(',').collectEntries { String content ->
                List<String> splitContent = content.replace('bag', '').replace('bags', '').trim().split()
                [("${splitContent[1]} ${splitContent[2]}".toString()): splitContent[0].toInteger()]
            }
        }
    }
}

class Bag {

    String type
    Map<String, Integer> content
}
