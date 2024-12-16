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
                val newCurr = currStr.slice(0..<(currStr.length / 2))
                val newNext = currStr.slice(currStr.length / 2..currStr.lastIndex)
                iter.set(newCurr.toLong())
                iter.add(newNext.toLong())
            } else {
                iter.set(curr * 2024L)
            }
        }
    }

    override fun part2(): Long {
        val input = readInput()
        return computePart2(input, blinks = 75)
    }

    override fun part1ResultDescription(): String {
        TODO("Not yet implemented")
    }

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

    private fun computePart2(input: List<Long>, blinks: Int = 25): Long {
        val lastMap = mutableMapOf<Long, Long>()
        input.forEach {
            val curr = lastMap[it] ?: 0
            lastMap[it] = curr + 1
        }

        repeat(blinks) {
            val nextMap = mutableMapOf<Long, Long>()
            lastMap.forEach { (number, count) ->
                val currStr = number.toString()
                if (number == 0L) {
                    val existingCount = nextMap[1] ?: 0
                    nextMap[1] = existingCount + (count)
                } else if (currStr.length % 2 == 0) {
                    val firstHalf = currStr.slice(0..<(currStr.length / 2)).toLong()
                    val secondHalf = currStr.slice(currStr.length / 2..currStr.lastIndex).toLong()
                    val existingCountFirstHalf = nextMap[firstHalf] ?: 0
                    nextMap[firstHalf] = existingCountFirstHalf + count
                    val existingCountSecondHalf = nextMap[secondHalf] ?: 0
                    nextMap[secondHalf] = existingCountSecondHalf + count
                } else {
                    val existingCount = nextMap[number * 2024] ?: 0
                    nextMap[number * 2024] = existingCount + count
                }
            }
            lastMap.clear()
            lastMap.putAll(nextMap)
        }

        return lastMap.map { it.value }.sum()
    }

    fun readInput(): List<Long> {
        return File("inputs/day11.txt").readLines().filter { it.isNotEmpty() }.flatMap { it.split(" ") }
            .map { it.toLong() }
    }
}