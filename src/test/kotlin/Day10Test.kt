import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day10Test {
    val ex1 = """
        0123
        1234
        8765
        9876
    """.trimIndent().lines()

    val ex2 = """
        ...0...
        ...1...
        ...2...
        6543456
        7.....7
        8.....8
        9.....9
    """.trimIndent().lines()

    val ex3 = """
        ..90..9
        ...1.98
        ...2..7
        6543456
        765.987
        876....
        987....
    """.trimIndent().lines()

    val ex4 = """
        10..9..
        2...8..
        3...7..
        4567654
        ...8..3
        ...9..2
        .....01
    """.trimIndent().lines()

    val ex5 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().lines()

    @Test
    fun `part 1, smallest example`() {
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(ex1))
        assertEquals(1,  d.computePart1(grid))
    }

    @Test
    fun `part 1, example 2, fork shaped map`() {
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(ex2))
        assertEquals(2,  d.computePart1(grid))
    }

    @Test
    fun `part 1, example 3`() {
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(ex3))
        assertEquals(4,  d.computePart1(grid))
    }


    @Test
    fun `part 1, example 4`() {
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(ex4))
        assertEquals(3,  d.computePart1(grid))
    }

    @Test
    fun `part 1, example 5, full example`() {
        val d = Day10()
        val grid = d.parseToLongsGrid(Day04.parseGrid(ex5))
        assertEquals(36,  d.computePart1(grid))
    }


}