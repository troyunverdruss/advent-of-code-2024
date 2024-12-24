import Day04.Companion.parseGrid
import Day04.Point
import java.io.File

class Day15 : Day {
    override fun part1(): Long {
        val day15Input = parseRawInput(File("inputs/day15.txt").readText())

        return computePart1(day15Input)
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription(): String {
        return "sum of all box GPS coordinates"
    }

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

    data class Day15Input(val grid: Map<Point, Char>, val instructions: List<Char>)

    fun parseRawInput(input: String): Day15Input {
        val parts = input.split("\n\n")
        val grid = parseGrid(parts[0].lines())
        val instructions = parts[1].map { it }.filterNot { it == '\n' }

        return Day15Input(grid, instructions)
    }

    fun getStartingPosition(day15Input: Day15Input): Point {
        return day15Input.grid.filter { it.value == '@' }.keys.first()
    }

    val dirLookup = mapOf(
        '^' to Day04.Direction.UP.point,
        'v' to Day04.Direction.DOWN.point,
        '<' to Day04.Direction.LEFT.point,
        '>' to Day04.Direction.RIGHT.point,
    )

    fun computePart1(input: Day15Input): Long {

        var pos = getStartingPosition(input)

        val grid = input.grid.toMutableMap()
        input.instructions.forEach { instruction ->
            val direction = when (instruction) {
                '^' -> Day04.Direction.UP.point
                'v' -> Day04.Direction.DOWN.point
                '<' -> Day04.Direction.LEFT.point
                '>' -> Day04.Direction.RIGHT.point
                else -> throw RuntimeException("Unknown direction: $instruction")
            }
            if (grid[pos + direction] == '.') {
                grid[pos] = '.'
                pos += direction
                grid[pos] = '@'
            } else if (grid[pos + direction] == '#') {
                // wall, noop
            } else if (grid[pos + direction] == 'O') {
                // handle moving one or more boxes
                var testPosition = pos + direction
                val toMove = mutableListOf<Point>()
                while (grid[testPosition] == 'O') {
                    toMove.add(testPosition)
                    testPosition += direction
                }
                if (grid[testPosition] == '.') {
                    // move the robot
                    grid[pos] = '.'
                    pos += direction
                    grid[pos] = '@'

                    // move the boxes
                    toMove.forEach { boxPos ->
                        grid[boxPos + direction] = 'O'
                    }
                }
            } else {
                throw RuntimeException("Unknown grid data: ${grid[pos + direction]}")
            }
        }

        return grid.filter { it.value == 'O' }.map { it.key }.map { it.y * 100L + it.x }.sum()
    }
}