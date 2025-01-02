import Utils.Direction
import Utils.Point
import java.io.File
import java.util.LinkedList

class Day18 : Day {
    override fun part1(): Long {
        val corruption = getCoruptionBytes()
        return computePart1(corruption.subList(0, 1024))
    }

    private fun getCoruptionBytes() = File("inputs/day18.txt")
        .readLines()
        .map { line ->
            val parts = line.split(",")
            Point(parts[0].toLong(), parts[1].toLong())
        }

    data class Step(val pos: Point) {
        var prev: Step? = null
    }

    fun computePart1(corruption: List<Point>, size: Int = 71, bytesToUse: Int = 1024): Long {
        val grid = (0L..<size).flatMap { y ->
            (0L..<size).map { x ->
                Point(x, y) to '.'
            }
        }.toMap().toMutableMap()

        corruption.subList(0, bytesToUse).forEach { grid[it] = '#' }

//        Utils.debugPrint(grid)

        val solutions = mutableListOf<Step>()
        val toVisit = LinkedList<Step>()
        val toVisitSet = mutableSetOf<Step>()
        val visited = mutableSetOf<Step>()
        val start = Step(Point(0, 0))
        val end = Point((size - 1).toLong(), (size - 1).toLong())

        toVisit.add(start)
        toVisitSet.add(start)

        val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
        while (toVisit.isNotEmpty()) {
            val curr = toVisit.pop()
            toVisitSet.remove(curr)
            visited.add(curr)

            if (curr.pos == end) {
                solutions.add(curr)
                continue
            }

            neighborDirections.forEach { dir ->
                val nextPos = curr.pos + dir.point
                val nextStep = Step(nextPos)
                nextStep.prev = curr
                if (grid[nextPos] == '.' && !toVisitSet.contains(nextStep) && !visited.contains(nextStep)) {
                    toVisit.add(nextStep)
                    toVisitSet.add(nextStep)
                }
            }
        }

        if (solutions.isEmpty()) {
            throw NoPathFoundException("Unable to find path")
        }

        return solutions.map { sol ->
            var length = 0L
            var curr: Step? = sol
            while (curr != null) {
                length += 1
                curr = curr.prev
            }
            // unique positions are 1 more than the number of steps
            length - 1
        }.min()
    }

    class NoPathFoundException(message: String) : RuntimeException(message)

    override fun part2(): Long {
        val corruption = getCoruptionBytes()
        (1024..corruption.lastIndex).forEach {
            try {
                print("$it: ${corruption[it]}")
                val shortestPath = computePart1(corruption, bytesToUse = it+1)
                println(": $shortestPath")
            } catch (e: NoPathFoundException) {
                println()
                println("Paths are obstructed by point: ${corruption[it]}")
                return -1
            }
        }
        return -1
    }

    override fun part1ResultDescription() = "Shortest path after 1024 bytes"

    override fun part2ResultDescription() = "First coordinate that blocks the path"
}