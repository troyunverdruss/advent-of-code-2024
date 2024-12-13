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
}