import java.io.File
import kotlin.math.abs

object Utils {

    val cardinalDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

    fun distance(a: Point, b: Point): Long {
        return abs(b.x - a.x) + abs(b.y - a.y)
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
            fun turnLeft90(direction: Direction): Direction {
                return when (direction) {
                    UP -> LEFT
                    DOWN -> RIGHT
                    LEFT -> DOWN
                    RIGHT -> UP
                    else -> throw RuntimeException("Unknown direction $direction")
                }
            }
        }
    }



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
    
    fun debugPrint(grid: Map<Utils.Point, Char>) {
        val minX = grid.keys.minBy { it.x }.x
        val minY = grid.keys.minBy { it.y }.y
        val maxX = grid.keys.maxBy { it.x }.x
        val maxY = grid.keys.maxBy { it.y }.y
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                print(grid[Utils.Point(x, y)] ?: '.')
            }
            println()
        }
    }

    fun debugPrint(region: Set<Pair<Utils.Point, Char>>, area: Int, sides: Long) {
        val map = region.toMap()
        val minX = region.minBy { it.first.x }.first.x
        val minY = region.minBy { it.first.y }.first.y
        val maxX = region.maxBy { it.first.x }.first.x
        val maxY = region.maxBy { it.first.y }.first.y
        (minX..maxX).forEach { x ->
            (minY..maxY).forEach { y ->
                print(map[Utils.Point(x, y)] ?: '.')
            }
            println()
        }
        println("Area: $area")
        println("Sides: $sides")
    }
}