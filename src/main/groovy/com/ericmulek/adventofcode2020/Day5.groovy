package com.ericmulek.adventofcode2020

class Day5 {

    static final Integer MAX_ROW = 127
    static final Integer MAX_COLUMN = 7
    static final int ID_OFFSET = 8

    List<String> bookedSeats

    Long findMaxSeatId() {
        bookedSeats.collect { String seat ->
            processSeat(seat)
        }.max()
    }

    private Long processSeat(String seat) {
        List<Integer> rows = (0..MAX_ROW)
        List<Integer> columns = (0..MAX_COLUMN)

        List remainingRows = rows
        List remainingColumns = columns
        seat.toList().each {
            TreeDirection direction = TreeDirection.findByValue(it)
            if (direction == TreeDirection.ROW_LEFT) {
                remainingRows = remainingRows.subList(0, remainingRows.size() / 2 as Integer)
            } else if (direction == TreeDirection.ROW_RIGHT) {
                remainingRows = remainingRows.subList(remainingRows.size() / 2 as Integer, remainingRows.size())
            } else if (direction == TreeDirection.COLUMN_LEFT) {
                remainingColumns = remainingColumns.subList(0, remainingColumns.size() / 2 as Integer)
            } else if (direction == TreeDirection.COLUMN_RIGHT) {
                remainingColumns = remainingColumns.subList(remainingColumns.size() / 2 as Integer, remainingColumns.size())
            }
        }
        ID_OFFSET * remainingRows[0] + remainingColumns[0]
    }

    Long findMySeatId() {
        List<Long> seatIds = bookedSeats.collect { String seat ->
            processSeat(seat)
        }.sort()

        seatIds.find {
            seatIds.contains(it + 2) && !seatIds.contains(it + 1)
        }.with {
            it + 1
        }
    }
}

enum TreeDirection {
    ROW_LEFT('F'),
    ROW_RIGHT('B'),
    COLUMN_LEFT('L'),
    COLUMN_RIGHT('R')

    TreeDirection(String value) {
        this.value = value
    }

    static TreeDirection findByValue(String value) {
        values().find { it.value == value }
    }

    String value
}

class SeatDataLoader {

    static final String RESOURCES_DIR = "src/main/resources"

    List<String> preProcess(String fileName) {
        new File("$RESOURCES_DIR/$fileName").readLines()
    }
}
