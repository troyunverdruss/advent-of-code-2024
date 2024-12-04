package net.unverdruss

import java.io.File

class Day03 {
    fun part1(): Long {
        val corruptedInput = readInput()
        return findValidValues(corruptedInput).map { it.first * it.second }.sum()
    }

    fun part2(): Long {
        TODO("Not yet implemented")
    }

    private fun readInput(): String {
        return File("inputs/day03.txt").readText()
    }

    fun findValidValues(corruptedInput: String): List<Pair<Long, Long>> {
        val validPairs = mutableListOf<Pair<Long, Long>>()
        (0..corruptedInput.lastIndex).forEach { index ->
            if (corruptedInput.slice(index..((index + 3).coerceAtMost(corruptedInput.lastIndex))) == "mul(") {
                val indexOfNextCloseParen = corruptedInput.drop(index + 4).indexOfFirst { it == ')' }
                val possibleMulString = corruptedInput.slice(index..(index+4+indexOfNextCloseParen))
                if (Regex("mul\\((\\d+),(\\d+)\\)").matches(possibleMulString)) {
                    val nums = possibleMulString.replace("mul(", "").replace(")", "").split(",")
                    validPairs.add(Pair(nums[0].toLong(), nums[1].toLong()))
                }
            }
        }
        return validPairs
    }

}


