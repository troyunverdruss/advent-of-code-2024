package net.unverdruss

import java.io.File

class Day05 {
    fun part1(): Long {
        val input = readInput()
        return computePart1(input)
    }

    fun computePart1(input: ParseResult): Long {
        val rulesLookup = mutableMapOf<Long, MutableList<Rule>>()
        input.rules.forEach {
            val lookupList1 = rulesLookup[it.first] ?: mutableListOf()
            lookupList1.add(it)
            rulesLookup[it.first] = lookupList1

            val lookupList2 = rulesLookup[it.second] ?: mutableListOf()
            lookupList2.add(it)
            rulesLookup[it.second] = lookupList2
        }

        return input.updates
            .filter { isValid(rulesLookup, it) }
            .sumOf { it[it.size / 2] }
    }

    fun part2(): Long {
        TODO("Not yet implemented")
    }

    data class Rule(val first: Long, val second: Long)
    data class ParseResult(val rules: List<Rule>, val updates: List<List<Long>>)

    fun isValid(rulesLookup: Map<Long, List<Rule>>, update: List<Long>): Boolean {
        var valid = true
        update.forEachIndexed { index, v ->
            val relevantRules = rulesLookup[v] ?: listOf()
            val numbersRequiredToBeBefore = relevantRules.filter { it.second == v }.map { it.first }
            val endOfList = update.slice(index+1..update.lastIndex)
            numbersRequiredToBeBefore.forEach {
                if (endOfList.contains(it)) {
                    valid = false
                }
            }


            val numbersRequiredToBeAfter = relevantRules.filter { it.first == v }.map { it.second }
            val frontOfList = update.slice(0..<index)
            numbersRequiredToBeAfter.forEach {
                if (frontOfList.contains(it)) {
                    valid = false
                }
            }
        }
        return valid
    }

    fun readInput(): ParseResult {
        val lines = File("inputs/day05.txt")
            .readLines()

        return parseInputString(lines)
    }

    fun parseInputString(lines: List<String>): ParseResult {
        val rules = lines.filter { it.contains("|") }
            .map { it.split("|") }
            .map { Rule(it.first().toLong(), it.last().toLong()) }

        val updates = lines.filter { it.contains(",") }
            .map { it.split(",") }
            .map { it.map { it.toLong() } }

        return ParseResult(rules, updates)
    }
}