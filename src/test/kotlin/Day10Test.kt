import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day10Test {

    @Test
    fun `part 1, smallest example`() {
        val lines = """
        0123
        1234
        8765
        9876
    """.trimIndent().lines()
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(1, d.computePart1(grid))
    }

    @Test
    fun `part 1, example 2, fork shaped map`() {
        val lines = """
        ...0...
        ...1...
        ...2...
        6543456
        7.....7
        8.....8
        9.....9
    """.trimIndent().lines()

        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(2, d.computePart1(grid))
    }

    @Test
    fun `part 1, example 3`() {
        val lines = """
        ..90..9
        ...1.98
        ...2..7
        6543456
        765.987
        876....
        987....
    """.trimIndent().lines()

        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(4, d.computePart1(grid))
    }


    @Test
    fun `part 1, example 4`() {
        val lines = """
        10..9..
        2...8..
        3...7..
        4567654
        ...8..3
        ...9..2
        .....01
    """.trimIndent().lines()

        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(3, d.computePart1(grid))
    }

    @Test
    fun `part 1, example 5, full example`() {
        val lines = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().lines()

        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(36, d.computePart1(grid))
    }


    @Test
    fun `part 2, example 1`() {
        val lines = """
        .....0.
        ..4321.
        ..5..2.
        ..6543.
        ..7..4.
        ..8765.
        ..9....
    """.trimIndent().lines()
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(3, d.computePart2(grid))
    }

    @Test
    fun `part 2, example 2`() {
        val lines = """
        ..90..9
        ...1.98
        ...2..7
        6543456
        765.987
        876....
        987....
    """.trimIndent().lines()
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(13, d.computePart2(grid))
    }

    @Test
    fun `part 2, example 3`() {
        val lines = """
        012345
        123456
        234567
        345678
        4.6789
        56789.
    """.trimIndent().lines()
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(227, d.computePart2(grid))
    }

    @Test
    fun `part 2, example 4`() {
        val lines = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().lines()
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(lines))
        assertEquals(81, d.computePart2(grid))
    }

}