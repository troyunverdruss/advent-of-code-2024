package net.unverdruss

import java.io.File

class Day05 {
    fun part1(): Long {
        val input = readInput()
        return computePart1(input)
    }

    fun computePart1(input: ParseResult): Long {
        val rulesLookup = createRulesLookup(input)

        return input.updates
            .filter { isValid(rulesLookup, it) }
            .sumOf { it[it.size / 2] }
    }

    fun part2(): Long {
        val input = readInput()
        return computePart2(input)
    }

    fun computePart2(input: ParseResult): Long {
        val rulesLookup = createRulesLookup(input)
        val globalOrder = buildGlobalOrder(rulesLookup, input)
        println(globalOrder)
        println(isValid(rulesLookup, globalOrder))

        return input.updates
            .filter { !isValid(rulesLookup, it) }
            .map { correctInvalidUpdate(rulesLookup, globalOrder, it) }
            .sumOf { it[it.size / 2] }
    }

    fun buildGlobalOrder(rulesLookup: MutableMap<Long, MutableList<Rule>>, input: ParseResult): List<Long> {
        val allNums = input.rules.flatMap { listOf(it.first, it.second) }.distinct().sorted()
        val globalOrder = mutableListOf<Long>()
        allNums.forEach { v ->
            for (testIndex in 0..globalOrder.size) {
                val testList = globalOrder.toMutableList()
                testList.add(testIndex, v)
                if (isValid(rulesLookup, testList)) {
                    globalOrder.add(testIndex, v)
                    break
                }
            }
        }
        return globalOrder.distinct()
    }

    private fun correctInvalidUpdate(
        rulesLookup: MutableMap<Long, MutableList<Rule>>,
        globalOrder: List<Long>,
        update: List<Long>
    ): List<Long> {
//        // Really gross but theoretically works
//        val mutableUpdate = update.toMutableList()
//        while (!isValid(rulesLookup, mutableUpdate)) {
//            mutableUpdate.shuffle()
//        }
//        return mutableUpdate

//        println("in: $update")
//        val valid = globalOrder.filter { update.contains(it) }
//        println("valid: $valid")
//        return valid


        val properOrder = mutableListOf<Long>()

        update.forEach { v ->
            for (testIndex in 0..globalOrder.size) {
                val testList = properOrder.toMutableList()
                testList.add(testIndex, v)
                if (isValid(rulesLookup, testList)) {
                    properOrder.add(testIndex, v)
                    break
                }
            }
        }
        return properOrder

    }

    fun createRulesLookup(input: ParseResult): MutableMap<Long, MutableList<Rule>> {
        val rulesLookup = mutableMapOf<Long, MutableList<Rule>>()
        input.rules.forEach {
            val lookupList1 = rulesLookup[it.first] ?: mutableListOf()
            lookupList1.add(it)
            rulesLookup[it.first] = lookupList1

            val lookupList2 = rulesLookup[it.second] ?: mutableListOf()
            lookupList2.add(it)
            rulesLookup[it.second] = lookupList2
        }
        return rulesLookup
    }


    data class Rule(val first: Long, val second: Long)
    data class ParseResult(val rules: List<Rule>, val updates: List<List<Long>>)

    fun isValid(rulesLookup: Map<Long, List<Rule>>, update: List<Long>): Boolean {
        var valid = true
        update.forEachIndexed { index, v ->
            val relevantRules = rulesLookup[v] ?: listOf()
            val numbersRequiredToBeBefore = relevantRules.filter { it.second == v }.map { it.first }
            val endOfList = update.slice(index + 1..update.lastIndex)
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