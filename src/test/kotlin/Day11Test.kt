import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun `part 1 example`() {
        val d = Day11()
        val input = "0 1 10 99 999".split(" ").map { it.toLong() }
        val endingList = d.computePart1(input, blinks = 1)
        assertEquals(listOf(1L, 2024L, 1L, 0L, 9L, 9L, 2021976L), endingList)
    }
}