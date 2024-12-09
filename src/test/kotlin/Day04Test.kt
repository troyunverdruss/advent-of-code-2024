import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class Day04Test {
    @Test
    fun `part1 example`() {
        val grid = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
            .lines()
            .flatMapIndexed { y: Int, line: String ->
                line.mapIndexed { x: Int, c: Char ->
                    Pair(Day04.Point(x.toLong(), y.toLong()), c)
                }
            }.toMap()

        val starts = grid.filter { it.value == 'X' }.map { it.key }
        val d = Day04()
        assertEquals(
            starts.map { d.findXmasCount(it, grid) }.sum(),
            18,
        )
    }

    @Test
    fun `part1 custom 1`() {
        val grid = """
            XMAS
            XMAS
            SAMX
            XXXX
            MMMM
            AAAA
            SSSS
            X
             M
              A
               S
               X
              M
             A
            S
            S
            A
            M
            X
            S
             A
              M
               X
               S
              A
             M
            X
        """.trimIndent()
            .lines()
            .flatMapIndexed { y: Int, line: String ->
                line.mapIndexed { x: Int, c: Char ->
                    Pair(Day04.Point(x.toLong(), y.toLong()), c)
                }
            }.toMap()

        val starts = grid.filter { it.value == 'X' }.map { it.key }
        val d = Day04()
        assertEquals(
            starts.map { d.findXmasCount(it, grid) }.count().toLong(),
            3,
        )
    }

    @Test
    fun `part2 example`() {
        val grid = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
            .lines()
            .flatMapIndexed { y: Int, line: String ->
                line.mapIndexed { x: Int, c: Char ->
                    Pair(Day04.Point(x.toLong(), y.toLong()), c)
                }
            }.toMap()

        val starts = grid.filter { it.value == 'A' }.map { it.key }
        val d = Day04()
        assertEquals(
            starts.map { d.findCrossingMasCount(it, grid) }.sum(),
            9,
        )
    }

}