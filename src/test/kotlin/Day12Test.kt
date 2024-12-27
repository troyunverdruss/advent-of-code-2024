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
        val grid = Utils.parseGrid(lines)
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
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(80, d.computePart2(grid))
    }

    @Test
    fun `part 2 example 2`() {
        val lines = """
            OOOOO
            OXOXO
            OOOOO
            OXOXO
            OOOOO
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(436, d.computePart2(grid))
    }

    @Test
    fun `part 2 example 3`() {
        val lines = """
            EEEEE
            EXXXX
            EEEEE
            EXXXX
            EEEEE
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(236, d.computePart2(grid))
    }

    @Test
    fun `part 2 example 4`() {
        val lines = """
            AAAAAA
            AAABBA
            AAABBA
            ABBAAA
            ABBAAA
            AAAAAA
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(368, d.computePart2(grid))
    }

    @Test
    fun `part 2 example 5, large example`() {
        val lines = """
            RRRRIICCFF
            RRRRIICCCF
            VVRRRCCFFF
            VVRCCCJFFF
            VVVVCJJCFE
            VVIVCCJJEE
            VVIIICJJEE
            MIIIIIJJEE
            MIIISIJEEE
            MMMISSJEEE
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        val regions = d.findRegions(grid)
        val expected = mapOf<Char, Triple<Long, Long, Long>>(
            'R' to Triple(12L, 10L, 120L),
            'I' to Triple(14L, 4L, 16L),
            'C' to Triple(14L, 22L, 308L),
            'F' to Triple(10L, 12L, 120L),
            'V' to Triple(13L, 10L, 130L),
            'J' to Triple(11L, 12L, 132L),
            'C' to Triple(1L, 4L, 4L),
            'E' to Triple(13L, 8L, 104L),
            'I' to Triple(14L, 16L, 224L),
            'M' to Triple(5L, 6L, 30L),
            'S' to Triple(3L, 6L, 18L),
        )

        regions.forEach { region ->
            val key = region.first().second
            println(key)
//            if (key != 'I') {
println("$key area: ${region.size}")
println("$key area: ${d.computeNumberOfSides(grid, region)}")
//                val ex = expected[key]!!
//                assertEquals(region.size.toLong(), ex.first)
//                assertEquals(d.computeNumberOfSides(grid, region), ex.second)

            println()
        }

        assertEquals(1206, d.computePart2(grid))
    }

    @Test
    fun `test edges for single box region`() {
        val d = Day12()
        d.computeNumberOfSides(
            mutableMapOf(), setOf(
                Pair(Utils.Point(0, 0), 'A'),
                Pair(Utils.Point(1, 0), 'A'),
                Pair(Utils.Point(0, 1), 'A'),
                Pair(Utils.Point(1, 1), 'A'),

                )
        )
    }

    @Test
    fun `part 2 test enclosed plot`() {
        val lines = """
            AAA
            ABA
            AAA
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(d.computePart2(grid), 68)
    }

    @Test
    fun `part 2 simplest case`() {
        val lines = """
            A
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(d.computePart2(grid), 4)
    }

    @Test
    fun `part 2 simple diagonal`() {
        val lines = """
            AAAA
            AABA
            ABAA
            AAAA
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        val d = Day12()
        assertEquals(d.computePart2(grid), 12*14+8)
    }
}