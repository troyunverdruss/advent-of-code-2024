package net.unverdruss

import Day04

class Day06 {
    fun part1(): Long {
        val grid = Utils.readGrid("inputs/day06.txt")
        return countVisitedSquares(grid)
    }

    fun countVisitedSquares(grid: Map<Utils.Point, Char>): Long {
        return runSimulation(grid).visitationGrid.values.count { it == '*' }.toLong()
    }

    data class SimulationResult(val visitationGrid: Map<Utils.Point, Char>, val loop: Boolean)
    fun runSimulation(grid: Map<Utils.Point, Char>): SimulationResult {
        val start = grid.entries.filter { it.value == '^' }[0].key
        var pos = start.copy()
        var dir = Utils.Direction.UP
        if (grid[pos] != '^') throw RuntimeException("Bad start found")

        val mutableGrid = grid.toMutableMap()
        val visits = mutableSetOf<Pair<Utils.Point, Utils.Direction>>()

        while (grid.keys.contains(pos)) {
            val visitsValue = Pair(pos, dir)
            if (visits.contains(visitsValue)) {
                return SimulationResult(mutableGrid, true)
            }

            mutableGrid[pos] = '*'
            visits.add(visitsValue)

            val nextPos = pos + dir.point
            if (grid[nextPos] == '#') {
                dir = Utils.Direction.turnRight90(dir)
            } else {
                pos = nextPos
            }
        }

        return SimulationResult(mutableGrid, false)
    }

    fun part2(): Long {
        val grid = Utils.readGrid("inputs/day06.txt")
        return computePart2(grid)
    }

    fun computePart2(grid: Map<Utils.Point, Char>): Long {
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
