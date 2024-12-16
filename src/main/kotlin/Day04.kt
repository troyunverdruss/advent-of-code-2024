import java.io.File

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

    enum class Direction(val point: Point) {
        UP_LEFT(Point(-1, -1)),
        NONE(Point(0, 0)),
        DOWN_RIGHT(Point(1, 1)),
        DOWN_LEFT(Point(-1, 1)),
        UP_RIGHT(Point(1, -1)),
        UP(Point(0, -1)),
        DOWN(Point(0, 1)),
        LEFT(Point(-1, 0)),
        RIGHT(Point(1, 0));

        companion object {

            fun turnRight90(direction: Direction): Direction {
                return when (direction) {
                    UP -> RIGHT
                    DOWN -> LEFT
                    LEFT -> UP
                    RIGHT -> DOWN
                    else -> throw RuntimeException("Unknown direction $direction")
                }
            }
        }
    }

    fun findCrossingMasCount(pos: Point, grid: Map<Point, Char>): Long {
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


    data class Point(val x: Long, val y: Long) {
        operator fun plus(p: Point): Point {
            return Point(this.x + p.x, this.y + p.y)
        }
        operator fun minus(p: Point): Point {
            return Point(this.x - p.x, this.y - p.y)
        }
        fun up(): Point {
            return this + Direction.UP.point
        }
        fun down(): Point {
            return this + Direction.DOWN.point
        }
        fun left(): Point {
            return this + Direction.LEFT.point
        }
        fun right(): Point {
            return this + Direction.RIGHT.point
        }
        fun upLeft(): Point {
            return this + Direction.UP_LEFT.point
        }
        fun upRight(): Point {
            return this + Direction.UP_RIGHT.point
        }
        fun downLeft(): Point {
            return this + Direction.DOWN_LEFT.point
        }
        fun downRight(): Point {
            return this + Direction.DOWN_RIGHT.point
        }

    }

    companion object {

        fun readGrid(filePath: String): Map<Point, Char> {
            return parseGrid(File(filePath).readLines())
        }

        fun parseGrid(lines: List<String>): Map<Point, Char> {
            return lines.filter { it.isNotBlank() }
                .flatMapIndexed { y: Int, line: String ->
                    line.mapIndexed { x: Int, c: Char ->
                        Pair(Point(x.toLong(), y.toLong()), c)
                    }
                }.toMap()
        }
    }
}