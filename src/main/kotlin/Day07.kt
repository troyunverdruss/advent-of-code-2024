import java.io.File

class Day07 {
    fun part1(): Long {
        val equations = readInput()

        //sum of the test values from just the equations that could possibly be true
        return equations.filter { couldBeTrue(it) }.sumOf { it.testValue }
    }

    fun part2(): Long {
        TODO()
    }

    data class CalibrationEquation(val testValue: Long, val equationValues: List<Long>) {
        companion object {
            fun parse(line: String): CalibrationEquation {
                val parts = line.split(":", limit = 2)
                val result = parts[0].toLong()
                val values = parts[1].trim().split(" ").map { it.toLong() }

                return CalibrationEquation(result, values)
            }
        }
    }

    fun couldBeTrue(eq: CalibrationEquation): Boolean {
        if (eq.equationValues.size == 2) {
            val summed = eq.equationValues.sum()
            val product = eq.equationValues.reduce { acc, v -> acc * v }
            return summed == eq.testValue || product == eq.testValue
        }

        val tmp = eq.equationValues.toMutableList()
        val a = tmp.removeFirst()
        val b = tmp.removeFirst()
        tmp.addFirst(a+b)
        // sum
        val sumResult = couldBeTrue(CalibrationEquation(eq.testValue, tmp))
        if (sumResult) {
            return true
        }
        tmp.removeFirst()
        tmp.addFirst(a*b)
        val prodResult = couldBeTrue(CalibrationEquation(eq.testValue, tmp))
        return prodResult
    }

    fun readInput(): List<CalibrationEquation> {
        val lines = File("inputs/day07.txt")
            .readLines()
            .filter { it.isNotBlank() }
        return parseInput(lines)
    }

     fun parseInput(lines: List<String>): List<CalibrationEquation> {
        return lines.map { CalibrationEquation.parse(it) }
    }
}
