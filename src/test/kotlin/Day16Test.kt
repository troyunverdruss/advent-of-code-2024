import Utils.parseGrid
import org.testng.Assert.*
import org.testng.annotations.Test

class Day16Test {
    @Test
    fun `part 1 example`() {
        val lines = """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent().lines()
        val grid = parseGrid(lines)
        val d = Day16()
        assertEquals(d.computePart1(grid),7036)
    }
}