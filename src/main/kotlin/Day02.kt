package net.unverdruss

import java.io.File
import kotlin.math.abs

class Day02 {
    fun part1(): Long {
        val reports = loadReports()
        println("reports = ${reports}")
        return reports.count { isReportSafePart1(it) }.toLong()
    }

    fun part2(): Long {
        val reports = loadReports()

        val totallyValid = reports.count { isReportSafePart1(it) }
        val provisionallyValid = reports.filter { !isReportSafePart1(it) }
            .map { report ->
                (0..report.lastIndex).any {index ->
                    val mutableReport = report.toMutableList()
                    mutableReport.removeAt(index)
                    isReportSafePart1(mutableReport)
                }
            }
            .count { it }
        return (totallyValid + provisionallyValid).toLong()
    }

    fun testForErrors(report: List<Long>) {

        println("Testing ${report}")

        val myPrediction = isReportSafe(report, enableDampener = true)
        println("  Prediction: $myPrediction")
        println("  Result with full list: ${isReportSafePart1(report)}")
        println("  Starting to drop entries to see if one might be valid")
        (0..report.lastIndex).forEach {index ->
            val mutableReport = report.toMutableList()
            mutableReport.removeAt(index)
            val r = isReportSafePart1(mutableReport)
            println("    Drop $index, list: $mutableReport, result: $r")
        }

//        val actual = isReportSafePart1(report)
//
//        val actuals = (0..report.lastIndex).any {
//            val mutableReport = report.toMutableList()
//            mutableReport.removeAt(it)
//            val r = isReportSafePart1(mutableReport)
////            println("${mutableReport} => $r")
//            r
//        }
//
////        println(myPrediction)
////        println(actual)
////        println(actuals)
//
//        if (myPrediction == actual) {
//            println("Always correct: $report [$myPrediction == $actual]")
//        } else {
//            if (myPrediction == actuals) {
//                println("Correct when dropping 1: $report [$myPrediction == $actuals]")
//            } else {
//                println("Mismatch when dropping 1: $report [$myPrediction == $actuals]")
//            }
//        }
//        if (!(myPrediction == actual || myPrediction == actuals)) {
//            println("Not correct: ${report}. [pred: ${myPrediction}, act: $actuals]")
//        }
    }

    fun isReportSafe(report: List<Long>, enableDampener: Boolean = false): Boolean {
        return if (enableDampener) {
            isReportSafeTry2(report, enableDampener) || isReportSafeTry2(report.slice(1..report.lastIndex)) || isReportSafeTry2(report.drop(1))
        } else {
            isReportSafeTry2(report)
        }
    }

    private fun isReportSafeTry2(report: List<Long>, enableDampener: Boolean = false): Boolean {
        var errors = 0
        var last: Long? = null
        var dir: Direction? = null

        for ((index, level) in report.withIndex()) {
            if (last == null) {
                last = level
                continue
            } else if (level == last) {
                errors += 1
                continue
            } else if (abs(level - last) > 3) {
                errors += 1
                continue
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
                        errors += 1
                        continue
                    } else if (level - last > 3) {
                        errors += 1
                        continue
                    }
                } else {
                    if (level > last) {
                        errors += 1
                        continue
                    } else if (last - level > 3) {
                        errors += 1
                        continue
                    }
                }
                last = level
            }
        }

        val safe = errors == 0 || (errors == 1 && enableDampener)

//        if (safe) {
//            println("safe: ${report}")
//        } else {
//            println("unsafe: ${report}")
//
//        }

        return safe
    }

    fun isReportSafePart1(report: List<Long>): Boolean {
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

//        if (safe) {
//            println("safe: ${report}")
//        } else {
//            println("unsafe: ${report}")
//
//        }

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