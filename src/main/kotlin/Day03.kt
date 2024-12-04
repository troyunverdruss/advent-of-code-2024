package net.unverdruss

import java.io.File

class Day03 {
    fun part1(): Long {
        val corruptedInput = readInput()
        return findValidValues(corruptedInput).map { it.first * it.second }.sum()
    }

    fun part2(): Long {
        val corruptedInput = readInput()
        return findValidValues(corruptedInput, true).map { it.first * it.second }.sum()
    }

    private fun readInput(): String {
        return File("inputs/day03.txt").readText()
    }

    fun findValidValues(corruptedInput: String, enableConditionals: Boolean = false): List<Pair<Long, Long>> {
        var enabled = true

        val validPairs = mutableListOf<Pair<Long, Long>>()
        (0..corruptedInput.lastIndex).forEach { index ->
            if (enableConditionals) {
                if (enabled && corruptedInput.slice(index..((index + 6).coerceAtMost(corruptedInput.lastIndex))) == "don't()") {
                    enabled = false
                } else if (!enabled && corruptedInput.slice(index..((index + 3).coerceAtMost(corruptedInput.lastIndex))) == "do()") {
                    enabled = true
                } else {
                    // noop
                }
            }

            if (enabled && corruptedInput.slice(index..((index + 3).coerceAtMost(corruptedInput.lastIndex))) == "mul(") {
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


