import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class Day07Test {
    val lines = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent()
        .lines()

    @Test
    fun `part1 example`() {
        val d = Day07()
        val equations = d.parseInput(lines)
        assertEquals(
            3749,
            equations.filter { d.couldBeTrue(it) }.sumOf { it.testValue }
        )
    }

    @Test
    fun `part2 example`() {
        val d = Day07()
        val equations = d.parseInput(lines)
        assertEquals(
            11387,
            equations.filter { d.couldBeTrueWithConcat(it) }.sumOf { it.testValue }
        )
    }
}