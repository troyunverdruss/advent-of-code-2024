import org.testng.Assert.*
import org.testng.annotations.Test
import java.io.File

class Day17Test {
    @Test
    fun `compare computers`() {
        val c = Computer()
        c.initialize(File("inputs/day17.txt").readLines(Charsets.UTF_8))

        val ic = InputComputer()

        repeat(1000000) { aVal ->
            ic.output.clear()
            c.output.clear()
            c.pointer = 0

            ic.regA = aVal.toLong()
            c.regA = aVal.toLong()
            ic.regB = 0
            c.regB = 0
            ic.regC = 0
            c.regC = 0

            c.run()
            ic.run()

            assertEquals(c.output, ic.output)
        }
    }


    @Test
    fun `manual testing`() {

        val seen = mutableMapOf(
            0 to false,
            1 to false,
            2 to false,
            3 to false,
            4 to false,
            5 to false,
            6 to false,
            7 to false,
        )

        (0..30).forEach { i ->
            val ic = InputComputer()
            ic.regA = i.toLong()
            ic.regB = 0
            ic.regC = 0

            ic.run()
            ic.output.forEach { v ->
                if (seen[v] == false || i == 6) {
                    seen[v] = true
                    println("$i => ${ic.output}")
                }
            }
        }
    }

    @Test
    fun `test getting outputs`() {
        val nums = listOf(0,1,2,3,4,5,6,7)
        val combos = nums.flatMap { a ->
            nums.map { b ->
                Pair(a,b)
            }
        }
        combos.forEach { c ->
            val ic = InputComputer()
            val regA = 0 or c.first

            ic.regA =
            ic.run()
            println("${ic.output}")
        }

        //        val zero = 0L
        var regA = 0L
//        regA = 512 // regA or 31
//        regA = 1.shl(9) // regA or 31
//        regA = regA.shl(3)
//        regA = regA or 4
//        regA = regA.shl(3)
//        regA = regA or 0
//        regA = regA.shl(3)
//        regA = regA or 3
//        regA = regA.shl(3)

//        regA = regA.shl(3)
//        regA = regA or 4
//        regA = regA.shl(3)
        val ic = InputComputer()
        ic.regA = regA
        ic.run()
        println("${ic.output}")
    }
}