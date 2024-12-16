import Day04.Point
import java.io.File
import kotlin.math.max

class Day13 : Day {
    override fun part1(): Long {
        val clawMachines = readInput()

        return computePart1(clawMachines)
    }

    fun computePart1(clawMachines: List<ClawMachine>): Long {
        return clawMachines.map { clawMachine ->
            solveClawMachine(clawMachine)
        }.sum().toLong()
//         return 0L
    }

    fun solveClawMachine(clawMachine: ClawMachine, tokens: Long = 0L): Long {
        if (clawMachine.prize.x < 0 || clawMachine.prize.y < 0) {
            return 0
        }
        if (clawMachine.prize.x == 0L && clawMachine.prize.y == 0L) {
            return tokens
        }
        // Button A
        val a = solveClawMachine(
            ClawMachine(clawMachine.buttonA, clawMachine.buttonB, clawMachine.prize - clawMachine.buttonA), tokens + 3L
        )
        // Button B
        val b = solveClawMachine(
            ClawMachine(clawMachine.buttonA, clawMachine.buttonB, clawMachine.prize - clawMachine.buttonB), tokens + 1L
        )
        return max(a, b)
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription(): String = "min tokens needed"


    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

    fun readInput(): List<ClawMachine> {
        return File("inputs/day13.txt")
            .readText().split("\n\n")
            .map { parseClawMachine(it.lines()) }
    }

    data class ClawMachine(val buttonA: Point, val buttonB: Point, val prize: Point)

    fun parseClawMachine(lines: List<String>): ClawMachine {
        val a = lines[0].split(":")[1].split(",").map { it.split("+")[1].trim().toLong() }
        val b = lines[1].split(":")[1].split(",").map { it.split("+")[1].trim().toLong() }
        val p = lines[2].split(":")[1].split(",").map { it.split("=")[1].trim().toLong() }

        return ClawMachine(Point(a[0], a[1]), Point(b[0], b[1]), Point(p[0], p[1]))
    }
}