import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day14Test {
    @Test
    fun `part 1 example`() {
        val d = Day14()
        val lines = """
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
        """.trimIndent().lines().map { d.parseLine(it) }

        assertEquals(
            12,
            d.computePart1(lines, width = 11, height = 7, cycles = 100)
        )
    }

    @Test
    fun `part 1 single robot example`() {
        val d = Day14()
        val lines = """
            p=2,4 v=2,-3
        """.trimIndent().lines().map { d.parseLine(it) }

        assertEquals(
            12,
            d.computePart1(lines, width = 11, height = 7, cycles = 5)
        )
    }

}