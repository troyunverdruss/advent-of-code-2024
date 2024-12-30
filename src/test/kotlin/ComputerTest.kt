import org.testng.Assert.*
import org.testng.annotations.Test

class ComputerTest {
    @Test
    fun `sample program`() {
        val lines = """
            Register A: 729
            Register B: 0
            Register C: 0

            Program: 0,1,5,4,3,0
        """.trimIndent().lines()
        val c = Computer()
        c.initialize(lines)
        c.run()
        assertEquals(
            c.output,
            listOf(4, 6, 3, 5, 6, 3, 5, 2, 1, 0)
        )

    }
}