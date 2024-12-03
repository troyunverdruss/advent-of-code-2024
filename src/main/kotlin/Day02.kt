package net.unverdruss

import java.io.File
import kotlin.math.abs

class Day02 {
    fun part1(): Long {
        val reports = loadReports()
        println("reports = ${reports}")
        return reports.count { isReportSafe(it) }.toLong()
    }

    fun part2(): Long {
        TODO("not done")
    }

    fun isReportSafe(report: List<Long>): Boolean {
        var safe = true
        var last: Long? = null
        var dir: Direction? = null
        for (level in report) {
            if (last == null) {
                last = level
            } else if (level == last) {
                safe = false
                break
            } else if (abs(level - last) > 3) {
                safe = false
                break
            } else if (dir == null) {
                dir = if (level - last > 0) {
                    Direction.UP
                } else {
                    Direction.DOWN
                }
                last = level
            } else {
                if (dir == Direction.UP) {
                    if (level < last) {
                        safe = false
                        break
                    }
                    if (level - last > 3) {
                        safe = false
                        break
                    }
                } else {
                    if (level > last) {
                        safe = false
                        break
                    }
                    if (last - level > 3) {
                        safe = false
                        break
                    }
                }
                last = level
            }
        }

        if (safe) {
            println("safe: ${report}")
        } else {
            println("unsafe: ${report}")

        }

        return safe
    }

    private enum class Direction { UP, DOWN }

    private fun loadReports(): List<List<Long>> {
        return File("inputs/day02.txt")
            .readLines()
            .filter { it.isNotBlank() }
            .map { line ->
                line.split(" ").map { it.toLong() }
            }
    }
}