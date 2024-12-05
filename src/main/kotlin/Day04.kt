package net.unverdruss

import java.io.File

class Day04 {
    var count = 0
    fun part1(): Long {
        val grid = readGrid()
        val starts = grid.filter { it.value == 'X' }.map { it.key }
        return starts.map { findXmasCount(it, grid) }.sum()
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
            val s = (0..3).map { step ->
                try {
                    grid[Point(pos.x + (d.x * step), pos.y + (d.y * step))]
                } catch (e: Exception) {
                    // probably out of bounds
                }
            }.joinToString("")
            if (s == "XMAS") {
                println("$pos, $d")
                count += 1
                println(count)
            }
            s
        }.count { it -> it == "XMAS" }.toLong()

    }



    data class Point(val x: Long, val y: Long)

    private fun readGrid(): Map<Point, Char> {
        return File("inputs/day04.txt").readLines()
            .filter { it.isNotBlank() }
            .flatMapIndexed { y: Int, line: String ->
                line.mapIndexed { x: Int, c: Char ->
                    Pair(Point(x.toLong(), y.toLong()), c)
                }
            }.toMap()
    }

    fun part2(): Long {
        TODO("Not yet implemented")
    }
}