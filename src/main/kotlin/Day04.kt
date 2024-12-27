import Utils.Point
import Utils.Direction
import Utils.readGrid

class Day04 {
    var count = 0
    fun part1(): Long {
        val grid = readGrid("inputs/day04.txt")
        val starts = grid.filter { it.value == 'X' }.map { it.key }
        return starts.map { findXmasCount(it, grid) }.sum()
    }

    fun part2(): Long {
        val grid = readGrid("inputs/day04.txt")
        val starts = grid.filter { it.value == 'A' }.map { it.key }
        return starts.map { findCrossingMasCount(it, grid) }.sum()
    }

    fun findXmasCount(pos: Point, grid: Map<Point, Char>): Long {
        val dirs = listOf(
            Point(1, 0), // forward
            Point(-1, 0), // backward
            Point(0, -1), // up
            Point(0, 1), // down
            Point(1, 1), // down, forward
            Point(-1, 1), // down, backward
            Point(1, -1), // up, forward
            Point(-1, -1), // up, backward
        )

        return dirs.map { d ->
            (0..3).map { step ->
                grid[Point(pos.x + (d.x * step), pos.y + (d.y * step))]
            }.joinToString("")
        }.count { it -> it == "XMAS" }.toLong()

    }


    fun findCrossingMasCount(pos: Utils.Point, grid: Map<Utils.Point, Char>): Long {
        val diagDown = listOf(
            Direction.UP_LEFT,
            Direction.NONE,
            Direction.DOWN_RIGHT,
        )
        val diagUp = listOf(
            Direction.DOWN_LEFT,
            Direction.NONE,
            Direction.UP_RIGHT, // up, forward
        )

        val crossOne = diagDown.map { d -> grid[Point(pos.x + (d.point.x), pos.y + (d.point.y))] }
            .joinToString("")
        val crossTwo = diagUp.map { d -> grid[Point(pos.x + (d.point.x), pos.y + (d.point.y))] }
            .joinToString("")

        return if ((crossOne == "MAS" || crossOne == "SAM") && (crossTwo == "MAS" || crossTwo == "SAM")) {
            1
        } else {
            0
        }
    }
}