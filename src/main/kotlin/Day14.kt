import Day04.Point
import java.io.File

class Day14 : Day {
    override fun part1(): Long {
        val robots = readInput()

        return computePart1(robots, cycles = 100)
    }

    fun computePart1(robots: List<Robot>, width: Long = 101, height: Long = 103, cycles: Long): Long {
        if (width % 2 == 0L || height % 2 == 0L) {
            throw RuntimeException("width/height must not be even")
        }

        val middleWidth = width / 2
        val middleHeight = height / 2

        var robotPositions = listOf<Robot>()
        repeat(cycles.toInt()) {
            robotPositions = takeOneStep(robots, width, height)
        }

        val nwCount = countRobotsInQuadrant(robotPositions, Point(0, 0), Point(middleWidth, middleHeight))
        val swCount = countRobotsInQuadrant(robotPositions, Point(0, middleHeight + 1), Point(middleWidth, height))
        val neCount = countRobotsInQuadrant(robotPositions, Point(middleWidth + 1, 0), Point(width, middleHeight))
        val seCount =
            countRobotsInQuadrant(robotPositions, Point(middleWidth + 1, middleHeight + 1), Point(width, height))

        val quadrants = mutableMapOf(Pair(Quad.NW, 0), Pair(Quad.NE, 0), Pair(Quad.SW, 0), Pair(Quad.SE, 0))
        val counts = robotPositions.fold(quadrants) { res, r ->
            if (r.pos.x == middleWidth || r.pos.y == middleHeight) {
                // don't count
            } else if (r.pos.x < middleWidth && r.pos.y < middleHeight) {
                res[Quad.NW] = res[Quad.NW]!! + 1
            } else if (r.pos.x < middleWidth && r.pos.y > middleHeight) {
                res[Quad.SW] = res[Quad.SW]!! + 1
            } else if (r.pos.x > middleWidth && r.pos.y < middleHeight) {
                res[Quad.NE] = res[Quad.NE]!! + 1
            } else if (r.pos.x > middleWidth && r.pos.y > middleHeight) {
                res[Quad.SE] = res[Quad.SE]!! + 1
            } else {
                throw RuntimeException("Odd coordinate $r")
            }

            res
        }

        return listOf(nwCount, neCount, swCount, seCount).fold(1) { r, v -> r * v }
    }

    // Lower inclusive
    // Upper exclusive
    private fun countRobotsInQuadrant(robotPositions: List<Robot>, lower: Point, upper: Point): Long {
        return robotPositions.count {
            it.pos.x >= lower.x && it.pos.y >= lower.y && it.pos.x < upper.x && it.pos.y < upper.y
        }.toLong()
    }

    private fun takeOneStep(
        robots: List<Robot>,
        width: Long,
        height: Long
    ): List<Robot> {
        val endingPositions = robots.map { r ->
            Robot(
                Point(
                    Math.floorMod(r.pos.x + r.vel.x, width),
                    Math.floorMod(r.pos.y + r.vel.y, height)
                ),
                r.vel
            )
        }
        return endingPositions
    }

    enum class Quad {
        NW, NE, SE, SW
    }

    override fun part2(): Long {
        val robots = readInput()

        return computePart2(robots)
    }

    private fun computePart2(robots: List<Robot>): Long {
        var robotPositions = robots
        var cycles = 0L
        while (true) {
            robotPositions = takeOneStep(robotPositions, 101, 103)
            cycles += 1

            for (robot in robotPositions) {
                val testPositions = (0..32).map { idx ->
                    Point(
                        robot.pos.x,
                        robot.pos.y + (Day04.Direction.DOWN.point.y * idx),
                    )
                }
                if (robotPositions.map { it.pos }.containsAll(testPositions)) {
                    Day12.debugPrint(robotPositions.map { Pair(it.pos, '#') }.toSet(), 0, 0L)
                    println(cycles)
                }
            }

        }

    }

    private fun computePart2v1(robots: List<Robot>): Long {
        val majority = robots.size / 2 + 1
        var robotPositions = robots
        var lowestCountOfPositionsForMajority = robots.size.toLong()
        var cycles = 0L
        while (true) {
            robotPositions = takeOneStep(robotPositions, 101, 103)
            cycles += 1
            val robotPositionCounts = robotPositions.fold(mutableMapOf<Point, Long>()) { acc, r ->
                acc[r.pos] = (acc[r.pos] ?: 0) + 1
                acc
            }.entries.sortedByDescending { it.value }
            var countedRobots = 0L
            var distinctPositions = 0L
            val iterator = robotPositionCounts.listIterator()
            while (countedRobots < majority && iterator.hasNext()) {
                val next = iterator.next()
                distinctPositions += 1
                countedRobots += next.value
            }
//            Day12.debugPrint(robotPositions.map { Pair(it.pos, '#') }.toSet(), 0, 0L)
//            println(cycles)

            if (distinctPositions <= 144) {
                Day12.debugPrint(robotPositions.map { Pair(it.pos, '#') }.toSet(), 0, 0L)
                println(cycles)
//                break
            } else if (distinctPositions <= lowestCountOfPositionsForMajority) {
                Day12.debugPrint(robotPositions.map { Pair(it.pos, '#') }.toSet(), 0, 0L)
                println(cycles)
                lowestCountOfPositionsForMajority = distinctPositions
            }
            Day12.debugPrint(robotPositions.map { Pair(it.pos, '#') }.toSet(), 0, 0L)
            println(cycles)
        }

        return cycles
    }

    override fun part1ResultDescription() = "safety factor after 100 seconds"


    override fun part2ResultDescription() = "fewest number of seconds to see the christmas tree easter egg"

    fun readInput(): List<Robot> {
        return File("inputs/day14.txt").readLines()
            .map { parseLine(it) }
        //p=0,4 v=3,-3
    }

    data class Robot(val pos: Point, val vel: Point)

    fun parseLine(line: String): Robot {
        val parts = line.split(" ").map {
            val coordParts = it.split("=")[1].split(",")
            Point(coordParts[0].toLong(), coordParts[1].toLong())
        }
        return Robot(parts[0], parts[1])
    }
}