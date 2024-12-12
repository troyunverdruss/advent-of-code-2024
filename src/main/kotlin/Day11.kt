import java.io.File
import java.util.LinkedList

class Day11 : Day {
    override fun part1(): Long {
        val input = readInput()
        val finalList = computePart1(input)

        return finalList.size.toLong()
    }

    fun computePart1(input: List<Long>, blinks: Int = 25): LinkedList<Long> {
        val ll = LinkedList<Long>(input)

        repeat(blinks) {
            blinkLinkedList(ll)
            println("$it}: ${ll.size}")
        }

        return ll
    }

    private fun blinkLinkedList(ll: LinkedList<Long>) {
        val iter = ll.listIterator()
        while (iter.hasNext()) {
            val curr = iter.next()
            val currStr = curr.toString()
            if (curr == 0L) {
                iter.set(1)
            } else if (currStr.length % 2 == 0) {
                val newCurr = currStr.slice(0..<(currStr.length/2))
                val newNext = currStr.slice(currStr.length/2..currStr.lastIndex)
                iter.set(newCurr.toLong())
                iter.add(newNext.toLong())
            } else {
                iter.set(curr * 2024L)
            }
        }
    }

    override fun part2(): Long {
        val input = readInput()
        val finalList = computePart1(input, blinks = 75)

        return finalList.size.toLong()
    }

    fun readInput(): List<Long> {
        return File("inputs/day11.txt").readLines().filter { it.isNotEmpty() }.flatMap { it.split(" ") }.map { it.toLong() }
    }
}