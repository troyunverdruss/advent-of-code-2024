import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day13Test {
    @Test
    fun `part 1 ex 1`() {
        val lines = """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertEquals(280, d.computePart1(listOf(clawMachine)))
    }

    @Test
    fun `part 1 ex 2`() {
        val lines = """
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertEquals(0, d.computePart1(listOf(clawMachine)))
    }

    @Test
    fun `part 1 ex 3`() {
        val lines = """
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertEquals(200, d.computePart1(listOf(clawMachine)))
    }

    @Test
    fun `part 1 ex 4`() {
        val lines = """
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertEquals(0, d.computePart1(listOf(clawMachine)))
    }

    @Test
    fun `part 2 ex 1`() {
        val lines = """
            Button A: X+94, Y+34
            Button B: X+22, Y+67
            Prize: X=8400, Y=5400
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertEquals(0, d.computePart2(listOf(clawMachine)))
    }

    @Test
    fun `part 2 ex 2`() {
        val lines = """
            Button A: X+26, Y+66
            Button B: X+67, Y+21
            Prize: X=12748, Y=12176
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertTrue ( d.computePart2(listOf(clawMachine)) > 0)
    }

    @Test
    fun `part 2 ex 3`() {
        val lines = """
            Button A: X+17, Y+86
            Button B: X+84, Y+37
            Prize: X=7870, Y=6450
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertEquals(0, d.computePart2(listOf(clawMachine)))
    }

    @Test
    fun `part 2 ex 4`() {
        val lines = """
            Button A: X+69, Y+23
            Button B: X+27, Y+71
            Prize: X=18641, Y=10279
        """.trimIndent().lines()
        val d = Day13()
        val clawMachine = d.parseClawMachine(lines)
        assertTrue ( d.computePart2(listOf(clawMachine)) > 0)
    }
}