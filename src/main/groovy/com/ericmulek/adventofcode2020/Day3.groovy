package com.ericmulek.adventofcode2020

class Day3 {

    String space
    String tree
    List<List<String>> arealMap

    Long countTreesOnRoute(Point startingPosition, Point targetPosition) {
        Integer treesHit = 0
        Point currentPosition = startingPosition
        while (currentPosition.y < arealMap.size()) {

            def temp = arealMap[currentPosition.y][currentPosition.x]
            if (temp == tree) {
                treesHit++
            }
            currentPosition = calculateNewPosition(currentPosition, targetPosition)
        }
        treesHit
    }

    private Point calculateNewPosition(Point currentPosition, Point targetPosition) {
        new Point(x: currentPosition.x + targetPosition.x, y: currentPosition.y + targetPosition.y)
    }

    Long processMultipleRoutes(Point startingPosition, List<Point> slopes) {
        slopes.collect {
            countTreesOnRoute(startingPosition, it)
        }.inject { long cumulative, long currentVal ->
            cumulative * currentVal
        }
    }
}

class Point {
    int x
    int y
}

class Day3DataLoad {

    static final String RESOURCES_DIR = "src/main/resources"

    List<List<String>> process(String fileName, int repeatsToTheRight) {
        List<List<String>> topographicMap = []
        new File("$RESOURCES_DIR/$fileName").eachLine {
            topographicMap << (it * repeatsToTheRight).toList()
        }
        topographicMap
    }
}
