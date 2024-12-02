package net.unverdruss

import java.io.File

class Day01 {
    fun part1(): Long {
        val (list1, list2) = loadAndSortLists()

        return list1.mapIndexed { index, value1 ->
            val value2 = list2[index]
            Math.abs(value1 - value2)
        }.sum()
    }

    fun part2(): Long {
        val (list1, list2) = loadLists()
        val counts = list2.fold(mutableMapOf<Long, Long>()) { acc, value ->
            acc[value] = (acc[value] ?: 0) + 1
            acc
        }

        return list1.sumOf { value1 -> value1 * (counts[value1] ?: 0) }
    }

    private fun loadAndSortLists(): Pair<List<Long>, List<Long>> {
        val (list1, list2) = loadLists()

        return Pair(list1.sorted(), list2.sorted())
    }

    private fun loadLists(): Pair<List<Long>, List<Long>> {
        val pairs = File("inputs/day01.txt")
            .readLines()
            .filter { it.isNotBlank() }
            .map { it.split(Regex("\\s+")) }
            .map { Pair(it.first(), it.last()) }
        val list1 = pairs.map { it.first }.map { it.toLong() }
        val list2 = pairs.map { it.second }.map { it.toLong() }

        return Pair(list1, list2)
    }

}