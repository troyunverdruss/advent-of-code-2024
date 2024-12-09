package net.unverdruss

import Day04

class Day06 {
    fun part1(): Long {
        val grid = Day04.readGrid("inputs/day06.txt")
        return countVisitedSquares(grid)
    }

    fun countVisitedSquares(grid: Map<Day04.Point, Char>): Long {
        return runSimulation(grid).visitationGrid.values.count { it == '*' }.toLong()
    }

    data class SimulationResult(val visitationGrid: Map<Day04.Point, Char>, val loop: Boolean)
    fun runSimulation(grid: Map<Day04.Point, Char>): SimulationResult {
        val start = grid.entries.filter { it.value == '^' }[0].key
        var pos = start.copy()
        var dir = Day04.Direction.UP
        if (grid[pos] != '^') throw RuntimeException("Bad start found")

        val mutableGrid = grid.toMutableMap()
        val visits = mutableSetOf<Pair<Day04.Point, Day04.Direction>>()

        while (grid.keys.contains(pos)) {
            val visitsValue = Pair(pos, dir)
            if (visits.contains(visitsValue)) {
                return SimulationResult(mutableGrid, true)
            }

            mutableGrid[pos] = '*'
            visits.add(visitsValue)

            val nextPos = pos + dir.point
            if (grid[nextPos] == '#') {
                dir = Day04.Direction.turnRight90(dir)
            } else {
                pos = nextPos
            }
        }

        return SimulationResult(mutableGrid, false)
    }

    fun part2(): Long {
        val grid = Day04.readGrid("inputs/day06.txt")
        return computePart2(grid)
    }

    fun computePart2(grid: Map<Day04.Point, Char>): Long {
        val start = grid.entries.filter { it.value == '^' }[0].key
        val allVisitedLocationsWithoutStart =
            runSimulation(grid).visitationGrid.filter { it.value == '*' && it.key != start }.keys

        return allVisitedLocationsWithoutStart.map { testPoint ->
            val testGrid = grid.toMutableMap()
            testGrid[testPoint] = '#'
            runSimulation(testGrid).loop
        }.count { it }.toLong()
    }


}
