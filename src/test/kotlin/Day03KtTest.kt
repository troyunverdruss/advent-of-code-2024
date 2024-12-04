import net.unverdruss.Day03
import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day03KtTest {
    @Test
    fun `verify parser`() {
        val input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"

        val d = Day03()
        val foundMulPairs = d.findValidValues(input)
        assertEquals(
            listOf(Pair(2L, 4L), Pair(5L, 5L), Pair(11L, 8L), Pair(8L, 5L)), foundMulPairs
        )
    }
}