import net.unverdruss.Day04
import net.unverdruss.Day06
import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day06Test {
    @Test
    fun `part 1 example`() {
        val lines = """
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
        val grid = Day04.parseGrid(lines)
        val d = Day06()

        assertEquals(41, d.runSimulation(grid))

    }
}