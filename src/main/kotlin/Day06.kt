package net.unverdruss

class Day06 {
    fun part1(): Long {
        val grid = Day04.readGrid("inputs/day06.txt")
        return runSimulation(grid)
    }

    fun runSimulation(grid: Map<Day04.Point, Char>): Long {
        val start = grid.entries.filter { it.value == '^' }[0].key
        var pos = start.copy()
        var dir = Day04.Direction.UP
        if (grid[pos] != '^') throw RuntimeException("Bad start found")

        val mutableGrid = grid.toMutableMap()

        while (grid.keys.contains(pos)) {
            mutableGrid[pos] = '*'

            val nextPos = pos + dir.point
            if (grid[nextPos] == '#') {
                dir = Day04.Direction.turnRight90(dir)
            } else {
                pos = nextPos
            }
        }

        return mutableGrid.values.count { it == '*' }.toLong()
    }

    fun part2(): Long {
        TODO()
    }


}
