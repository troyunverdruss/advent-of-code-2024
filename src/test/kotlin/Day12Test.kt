import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class Day12Test {
    @Test
    fun `part 1 smallest example`() {
        val lines = """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent().lines()
        val grid = Day04.parseGrid(lines)
        val d = Day12()
        assertEquals(140, d.computePart1(grid))
    }

    @Test
    fun `part 2 smallest example`() {
        val lines = """
            AAAA
            BBCD
            BBCC
            EEEC
        """.trimIndent().lines()
        val grid = Day04.parseGrid(lines)
        val d = Day12()
        assertEquals(80, d.computePart2(grid))
    }

    @Test
    fun `test edges for single box region`() {
        val d = Day12()
        d.computeNumberOfSides(mutableMapOf(), setOf(
            Pair(Day04.Point(0,0), 'A'),
            Pair(Day04.Point(1,0), 'A'),
            Pair(Day04.Point(0,1), 'A'),
            Pair(Day04.Point(1,1), 'A'),

        ))
    }
}