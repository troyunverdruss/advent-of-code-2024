import net.unverdruss.Day02
import org.testng.annotations.DataProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day02Test {

    @get:DataProvider(name = "reportsProviderPart1")
    val reports = arrayOf(
        Pair(true, listOf(7L, 6, 4, 2, 1)),
        Pair(false, listOf(1L, 2, 7, 8, 9)),
        Pair(false, listOf(9L, 7, 6, 2, 1)),
        Pair(false, listOf(1L, 3, 2, 4, 5)),
        Pair(false, listOf(8L, 6, 4, 4, 1)),
        Pair(true, listOf(1L, 3, 6, 7, 9)),
    )

    @get:DataProvider(name = "reportsProviderPart2")
    val reports2 = arrayOf(
        Pair(true, listOf(7L, 6, 4, 2, 1)),
        Pair(false, listOf(1L, 2, 7, 8, 9)),
        Pair(false, listOf(9L, 7, 6, 2, 1)),
        Pair(true, listOf(1L, 3, 2, 4, 5)),
        Pair(true, listOf(8L, 6, 4, 4, 1)),
        Pair(true, listOf(1L, 3, 6, 7, 9)),
    )

    @Test(dataProvider = "reportsProviderPart1")
    fun `part1`(resultAndReport: Pair<Boolean, List<Long>>) {
        val d = Day02()
        assertEquals(resultAndReport.first, d.isReportSafe(resultAndReport.second))
    }

    @Test(dataProvider = "reportsProvider2")
    fun `part2`(resultAndReport: Pair<Boolean, List<Long>>) {
        val d = Day02()
        assertEquals(resultAndReport.first, d.isReportSafe(resultAndReport.second))
    }

}