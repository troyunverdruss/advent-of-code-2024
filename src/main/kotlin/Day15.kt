import Utils.parseGrid
import Utils.Point
import java.io.File

class Day15 : Day {
    override fun part1(): Long {
        val day15Input = parseRawInput(File("inputs/day15.txt").readText(), false)

        return computePart1(day15Input)
    }

    override fun part2(): Long {
        val day15Input = parseRawInput(File("inputs/day15.txt").readText(), true)
        return computePart2(day15Input)
    }

    override fun part1ResultDescription(): String {
        return "sum of all box GPS coordinates"
    }

    override fun part2ResultDescription(): String {
        return "sum of all box GPS coordinates after scaling up"
    }

    data class Day15Input(val grid: Map<Point, Char>, val instructions: List<Char>)

    fun parseRawInput(input: String, scaleUp: Boolean = false): Day15Input {
        val parts = input.split("\n\n")
        val rawLines = parts[0].lines()

        return if (scaleUp) {
            val scaledLines = rawLines.map { line ->
                val chars = line.flatMap { c ->
                    when (c) {
                        // If the tile is #, the new map contains ## instead.
                        '#' -> listOf('#', '#')
                        //If the tile is O, the new map contains [] instead.
                        'O' -> listOf('[', ']')
                        //If the tile is ., the new map contains .. instead.
                        '.' -> listOf('.', '.')
                        //If the tile is @, the new map contains @. instead.
                        '@' -> listOf('@', '.')
                        else -> throw RuntimeException("Unexpected character $c")
                    }
                }
                chars.joinToString("")
            }
            val scaledGrid = parseGrid(scaledLines)
            val instructions = parts[1].map { it }.filterNot { it == '\n' }
            Day15Input(scaledGrid, instructions)
        } else {
            val rawGrid = parseGrid(rawLines)
            val instructions = parts[1].map { it }.filterNot { it == '\n' }

            Day15Input(rawGrid, instructions)
        }
    }

    fun getStartingPosition(day15Input: Day15Input): Point {
        return day15Input.grid.filter { it.value == '@' }.keys.first()
    }

    val dirLookup = mapOf(
        '^' to Utils.Direction.UP.point,
        'v' to Utils.Direction.DOWN.point,
        '<' to Utils.Direction.LEFT.point,
        '>' to Utils.Direction.RIGHT.point,
    )

    fun computePart1(input: Day15Input): Long {

        var pos = getStartingPosition(input)

        val grid = input.grid.toMutableMap()
        input.instructions.forEach { instruction ->
            val direction = when (instruction) {
                '^' -> Utils.Direction.UP.point
                'v' -> Utils.Direction.DOWN.point
                '<' -> Utils.Direction.LEFT.point
                '>' -> Utils.Direction.RIGHT.point
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

    fun computePart2(input: Day15Input): Long {

        var pos = getStartingPosition(input)

        val grid = input.grid.toMutableMap()
        input.instructions.forEach { instruction ->
            val direction = when (instruction) {
                '^' -> Utils.Direction.UP.point
                'v' -> Utils.Direction.DOWN.point
                '<' -> Utils.Direction.LEFT.point
                '>' -> Utils.Direction.RIGHT.point
                else -> throw RuntimeException("Unknown direction: $instruction")
            }
            if (grid[pos + direction] == '.') {
                grid[pos] = '.'
                pos += direction
                grid[pos] = '@'
            } else if (grid[pos + direction] == '#') {
                // wall, noop
            } else if (grid[pos + direction] == '[' || grid[pos + direction] == ']') {
                // Same logic as before for left/right
                if (instruction == '<' || instruction == '>') {
                    var testPosition = pos + direction
                    val toMove = mutableListOf<Pair<Point, Char>>()
                    while (grid[testPosition] == '[' || grid[testPosition] == ']') {
                        toMove.add(Pair(testPosition, grid[testPosition]!!))
                        testPosition += direction
                    }
                    if (grid[testPosition] == '.') {
                        // move the robot
                        grid[pos] = '.'
                        pos += direction
                        grid[pos] = '@'

                        // move the boxes
                        toMove.reversed().forEach { boxPartPair ->
                            grid[boxPartPair.first + direction] = boxPartPair.second
                        }
                    }
                } else {
                    // Moving UP or DOWN
                    // Need to handle overlapping pushes
                    val toMove = findAllBoxesToMove(grid, pos, direction)
                    val canMove = verifyAllBoxesCanMove(grid, toMove, direction)
                    if (toMove.isNotEmpty() && canMove) {
                        // move the boxes
                        toMove.forEach { boxPartPair ->
                            grid[boxPartPair.first + direction] = boxPartPair.second
                            grid[boxPartPair.first] = '.'
                        }

                        // move the robot
                        grid[pos] = '.'
                        pos += direction
                        grid[pos] = '@'
                    }
                }
            } else {
                throw RuntimeException("Unknown grid data: ${grid[pos + direction]}")
            }
//            println("Move $instruction:")
//            Utils.debugPrint(grid)
        }

        return grid.filter { it.value == '[' }.map { it.key }.map { it.y * 100L + it.x }.sum()
    }

    private fun verifyAllBoxesCanMove(
        grid: Map<Point, Char>,
        toMove: List<Pair<Point, Char>>,
        direction: Point
    ): Boolean {
        return toMove.all {
            grid[it.first + direction] == '.' || grid[it.first + direction] == '[' || grid[it.first + direction] == ']'
        }
    }

    private fun findAllBoxesToMove(
        grid: Map<Point, Char>,
        pos: Point,
        direction: Point
    ): List<Pair<Point, Char>> {
        val testPosition = pos + direction
        if (grid[testPosition] == '.' || grid[testPosition] == '#') {
            return listOf()
        }

        val res = mutableListOf<Pair<Point, Char>>()
        val testPosition2 = if (grid[testPosition] == '[') {
            res.add(Pair(testPosition, '['))
            res.add(Pair(testPosition + Utils.Direction.RIGHT.point, ']'))
            testPosition + Utils.Direction.RIGHT.point
        } else {
            res.add(Pair(testPosition, ']'))
            res.add(Pair(testPosition + Utils.Direction.LEFT.point, '['))
            testPosition + Utils.Direction.LEFT.point
        }


        val res1 = findAllBoxesToMove(grid, testPosition, direction)
        val res2 = findAllBoxesToMove(grid, testPosition2, direction)

        return if (direction.y < 0) { // UP
            (res + res1 + res2).sortedBy { it.first.y }
        } else {
            (res + res1 + res2).sortedByDescending { it.first.y }
        }
    }

}