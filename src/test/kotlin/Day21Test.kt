import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {

    @Test
    fun `compute part 1`() {
        val codes = """
            029A
            980A
            179A
            456A
            379A
        """.trimIndent().lines()
        val d = Day21()
        val res = d.computePart1(codes)
        assertEquals(126384, res)
    }

    @Test
    fun `test example part 1, 029A`() {
        val d = Day21()
        val res = d.computeResultPart1("029A")
        assertEquals(res.complexity(), 68*29)
    }

    @Test
    fun `test example part 1, 980A`() {
        val d = Day21()
        val res = d.computeResultPart1("980A")
        assertEquals(res.complexity(), 60 * 980)
    }

    @Test
    fun `test example part 1, 179A`() {
        val d = Day21()
        val res = d.computeResultPart1("179A")
        assertEquals(res.complexity(), 68 * 179)
    }

    @Test
    fun `test example part 1, 456A`() {
        val d = Day21()
        val res = d.computeResultPart1("456A")
        assertEquals(res.complexity(), 64 * 456)
    }

    @Test
    fun `test example part 1, 379A`() {
        val d = Day21()
        val res = d.computeResultPart1("379A")
        assertEquals(64 * 379, res.complexity())
    }

    @Test
    fun `compare cartesian products`() {
        val d = Day21()
        val input = listOf(listOf("a"), listOf("b1", "b2"), listOf("c"))
        val a = d.allPaths(input)
        val b = d.cartesianProduct(input)
        assertEquals(a, b)
    }

}