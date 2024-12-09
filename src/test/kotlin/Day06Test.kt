import net.unverdruss.Day06
import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day06Test {

    private val lines = """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent()
        .lines()

    @Test
    fun `part 1 example`() {

        val grid = Day04.parseGrid(lines)
        val d = Day06()

        assertEquals(41, d.countVisitedSquares(grid))
    }

    @Test
    fun `part 2 example`() {
        val grid = Day04.parseGrid(lines)
        val d = Day06()

        assertEquals(6, d.computePart2(grid))
    }
}