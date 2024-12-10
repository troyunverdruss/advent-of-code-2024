import org.testng.annotations.Test
import kotlin.test.assertEquals

class Day09Test {
    @Test
    fun `test simple example 1 defragging`() {
        val d = Day09()
        val input = d.parseInput("12345")
        val memory = d.createMemoryLayout(input)
        val defrag = d.defragMemory(memory)
        val expected = expectedFromStringExample("022111222......")
        assertEquals(expected, defrag)
    }

    @Test
    fun `longer example 1`() {
        val d = Day09()
        val input = d.parseInput("2333133121414131402")
        assertEquals(1928, d.computePart1(input))
    }
    @Test
    fun `longer example 2`() {
        val d = Day09()
        val input = d.parseInput("2333133121414131402")
        assertEquals(2858, d.computePart2(input))
    }

    @Test
    fun `test longer example 1 defragging`() {
        val d = Day09()
        val input = d.parseInput("2333133121414131402")
        val memory = d.createMemoryLayout(input)
        val defrag = d.defragMemory(memory)

        val expected = expectedFromStringExample("0099811188827773336446555566..............")
        assertEquals(expected, defrag)
    }

    @Test
    fun `test part2 defragging`() {
        val d = Day09()
        val input = d.parseInput("2333133121414131402")
        val memory = d.createMemoryLayout(input)
        val defrag = d.defragMemoryKeepingFilesIntact(memory)

        //                                                          00...111...2...333.44.5555.6666.777.888899
        val expected = expectedFromStringExample("00992111777.44.333....5555.6666.....8888..")
        //                                                          00992111777.44....333.....5555.6666.8888..
        assertEquals(expected, defrag)
    }

    @Test
    fun `test part2 defragging basic example`() {
        val d = Day09()
        val input = d.parseInput("13223") // 0...11..222 => 022211.....
        val memory = d.createMemoryLayout(input)
        val defrag = d.defragMemoryKeepingFilesIntact(memory)

        val expected = expectedFromStringExample("022211.....")
        assertEquals(expected, defrag)
    }

    private fun expectedFromStringExample(input: String): List<Long?> {
        val expected = input.map {
            if (it == '.') {
                null
            } else {
                "$it".toLong()
            }
        }
        return expected
    }
}