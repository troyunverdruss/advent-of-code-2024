import org.testng.Assert.*
import kotlin.test.Test

class Day18Test {
    val corruption = listOf(
        Utils.Point(5,4),
        Utils.Point(4,2),
        Utils.Point(4,5),
        Utils.Point(3,0),
        Utils.Point(2,1),
        Utils.Point(6,3),
        Utils.Point(2,4),
        Utils.Point(1,5),
        Utils.Point(0,6),
        Utils.Point(3,3),
        Utils.Point(2,6),
        Utils.Point(5,1),
        Utils.Point(1,2),
        Utils.Point(5,5),
        Utils.Point(2,5),
        Utils.Point(6,5),
        Utils.Point(1,4),
        Utils.Point(0,4),
        Utils.Point(6,4),
        Utils.Point(1,1),
        Utils.Point(6,1),
        Utils.Point(1,0),
        Utils.Point(0,5),
        Utils.Point(1,6),
        Utils.Point(2,0),
    )

    @Test
    fun `part 1 example`() {
        val d = Day18()
        val shortestPath = d.computePart1(corruption, size = 7, bytesToUse = 12)
        assertEquals(shortestPath ,22)
    }

    @Test
    fun `part 2 example`() {
        val d = Day18()

        (0..corruption.lastIndex).forEach {
            try {
                print("$it: ${corruption[it]}")
                val shortestPath = d.computePart1(corruption, size = 7, bytesToUse = it+1)
                println(": $shortestPath")
            } catch (e: Day18.NoPathFoundException) {
                println()
                println("Paths are obstructed by point: ${corruption[it]}")
            }
        }
    }
}