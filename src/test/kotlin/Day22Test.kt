import org.testng.Assert.*
import kotlin.test.Test

class Day22Test {
    @Test
    fun `verify generateNextNumber works`() {
        val d = Day22()
        assertEquals(d.generateNextNumber(123), 15887950)
    }

    @Test
    fun `verify xor`() {
        assertEquals(42 xor 15, 37)
    }

    @Test
    fun `verify modulo`() {
        assertEquals(100000000 % 16777216, 16113920)
    }

    @Test
    fun `part 2, print example numbers`() {
        val d = Day22()

        val cache = mutableMapOf<List<Long>, Long>()
        d.genSecrets(123, cache)
    }

    @Test
    fun `part 2, example`() {
        val inputs = listOf(
            1L,
            2L,
            3L,
            2024L
        )
        val d = Day22()
        val result = d.computePart2(inputs)
        assertEquals(result, 23)
    }


}