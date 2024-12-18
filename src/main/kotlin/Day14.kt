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

        val endingPositions = robots.map { r ->
            Robot(
                Point(
                    Math.floorMod(r.pos.x + r.vel.x * cycles, width),
                    Math.floorMod(r.pos.y + r.vel.y * cycles, height)
                ),
                r.vel
            )
        }


        val quadrants = mutableMapOf(Pair(Quad.NW, 0), Pair(Quad.NE, 0), Pair(Quad.SW, 0), Pair(Quad.SE, 0))
        val counts = endingPositions.fold(quadrants) { res, r ->
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

        return counts.values.fold(1) { r, v -> r * v }
    }

    enum class Quad {
        NW, NE, SE, SW
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
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