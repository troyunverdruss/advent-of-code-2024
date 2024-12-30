import Utils.parseGrid
import org.testng.Assert.*
import org.testng.annotations.Test

class Day16Test {
    @Test
    fun `part 1 example, small`() {
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

    @Test
    fun `part 2 example, small`() {
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
        assertEquals(d.computePart2(grid),45)
    }

    @Test
    fun `part 2 custom, tiny`() {
        val lines = """
            #####
            #..E#
            #...#
            #S..#
            #####
        """.trimIndent().lines()
        val grid = parseGrid(lines)
        val d = Day16()
        assertEquals(d.computePart2(grid),45)
    }

@Test
    fun `part 2 example, large`() {
        val lines = """
            #################
            #...#...#...#..E#
            #.#.#.#.#.#.#.#.#
            #.#.#.#...#...#.#
            #.#.#.#.###.#.#.#
            #...#.#.#.....#.#
            #.#.#.#.#.#####.#
            #.#...#.#.#.....#
            #.#.#####.#.###.#
            #.#.#.......#...#
            #.#.###.#####.###
            #.#.#...#.....#.#
            #.#.#.#####.###.#
            #.#.#.........#.#
            #.#.#.#########.#
            #S#.............#
            #################
        """.trimIndent().lines()
        val grid = parseGrid(lines)
        val d = Day16()
        assertEquals(d.computePart2(grid),64)
    }

    fun recurse(i: Int): Int {
        if (i > 10000) {
            println("done")
            return i
        }
        return recurse(i+1)
    }

    @Test
    fun `test recursion depth`() {
        assertEquals(recurse(0), 10001)
    }
}