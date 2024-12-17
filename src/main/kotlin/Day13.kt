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
        // y = mx
//            val eq1 = (clawMachine.buttonA.y / clawMachine.buttonA.x) * x - (clawMachine.buttonB.y / clawMachine.buttonB.x) * x

        // A button = 3 tokens
        // B button = 1 token

        var pos = clawMachine.prize
        val solutions1 = mutableListOf<Long>()
        var tokens = 0L
        while (pos.x > 0 && pos.y > 0) {
            if (pos.x % clawMachine.buttonB.x == 0L && pos.y % clawMachine.buttonB.y == 0L && pos.x / clawMachine.buttonB.x == pos.y / clawMachine.buttonB.y) {
                solutions1.add(tokens + pos.x / clawMachine.buttonB.x)
            }
            pos = Point(pos.x - clawMachine.buttonA.x, pos.y - clawMachine.buttonA.y)
            tokens += 3
        }

        tokens = 0L
        pos = clawMachine.prize
        val solutions2 = mutableListOf<Long>()
        while (pos.x > 0 && pos.y > 0) {
            if (pos.x % clawMachine.buttonA.x == 0L && pos.y % clawMachine.buttonA.y == 0L && pos.x / clawMachine.buttonA.x == pos.y / clawMachine.buttonA.y) {
                solutions2.add(tokens + 3*(pos.x / clawMachine.buttonA.x))
            }
            pos = Point(pos.x - clawMachine.buttonB.x, pos.y - clawMachine.buttonB.y)
            tokens += 1
        }

        return if (solutions1.isNotEmpty() || solutions2.isNotEmpty()) {
            listOf(solutions1.min(), solutions2.min()).min()
        } else {
            0
        }
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