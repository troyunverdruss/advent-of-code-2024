import org.testng.Assert.*
import kotlin.test.Test

class Day19Test {
    val input = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.trimIndent()

    @Test
    fun `part 1 example`() {
        val d = Day19()
        val input = d.parseLines(input.split("\n\n"))
        assertEquals(d.computePart1(input), 6)
    }

    @Test
    fun `part 2 example`() {
        val d = Day19()
        val input = d.parseLines(input.split("\n\n"))
        assertEquals(d.computePart2(input), 16)
    }
}