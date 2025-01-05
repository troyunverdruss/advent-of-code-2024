import Utils.Direction
import org.testng.Assert.*
import kotlin.test.Test

class Day21Test {
    @Test
    fun `test keypad path finding`() {
        // 029A
        // <A^A>^^AvvvA
        val kp = Day21.KeyPad()
        val path0 = kp.pathToNext('0')
        assertEquals(path0, listOf(Direction.LEFT))
        val path2 = kp.pathToNext('2')
        assertEquals(path2, listOf(Direction.UP))
        val path9 = kp.pathToNext('9')
//        assertEquals(path9, listOf(Direction.RIGHT, Direction.UP, Direction.UP))
        val pathA = kp.pathToNext('A')
        assertEquals(pathA, listOf(Direction.DOWN, Direction.DOWN, Direction.DOWN))
    }

    @Test
    fun `test example part 1`() {
        val d = Day21()
        val steps = d.computePart1("029A")


    }
}