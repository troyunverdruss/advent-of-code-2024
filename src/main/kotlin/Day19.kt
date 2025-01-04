import java.io.File
import java.util.*

class Day19 : Day {
    override fun part1(): Long {
        val parts = File("inputs/day19.txt").readText().split("\n\n")
        val parsedInput = parseLines(parts)
        return computePart1(parsedInput)
    }

    data class ParsedInput(val patterns: List<String>, val designs: List<String>)

    fun parseLines(parts: List<String>): ParsedInput {
        val patterns = parts[0].split(",").map { it.trim() }
        val designs = parts[1].split("\n").filter { it.isNotEmpty() }
        return ParsedInput(patterns, designs)
    }

    fun computePart1(parsedInput: ParsedInput): Long {
        return parsedInput.designs
            .map { design -> isPossibleDesign(parsedInput.patterns, design) }
            .count { it }.toLong()
    }

    fun computePart2(parsedInput: ParsedInput): Long {
        return parsedInput.designs
            .map { design ->
                hydrateRemainderMemo(parsedInput.patterns, design)
                possiblePermutations(parsedInput.patterns, design)
            }
            .sum()
    }

    private val remainderMemo = mutableMapOf<String, Long>()
    private fun hydrateRemainderMemo(patterns: List<String>, design: String) {
        design.indices.reversed().forEach { i ->
            val target = design.slice(i..design.lastIndex)
            remainderMemo[target] = possiblePermutations(patterns, target)
        }
    }

    fun isPossibleDesign(patterns: List<String>, design: String): Boolean {
//        println("testing for design: $design")
        val roots = Stack<String>()
        val rootsSet = mutableSetOf<String>()
        val checked = mutableSetOf<String>()
        roots.addAll(patterns)
        rootsSet.addAll(patterns)
        while (roots.isNotEmpty()) {
            val curr = roots.pop()
            rootsSet.remove(curr)
            checked.add(curr)

            if (curr == design) {
                return true
            }
            if (curr != design.slice(curr.indices)) {
                continue
            }

            patterns.forEach { p ->
                val nextRoot = curr + p
                if (nextRoot !in checked && nextRoot.length <= design.length && !rootsSet.contains(nextRoot)) {
                    roots.push(nextRoot)
                }
            }
        }
        return false
    }

    data class Step(val tokens: List<String>) {
        fun str(): String {
            return tokens.joinToString("")
        }
    }

    fun possiblePermutations(patterns: List<String>, design: String): Long {
        println("testing for design: $design")
        val patternsLookup = patterns.groupBy { it.first() }
        val roots = Stack<Step>()
        val rootsSet = mutableSetOf<Step>()
        val checked = mutableSetOf<Step>()
        roots.addAll(
            patternsLookup[design.first()]
                ?.filter { it.length <= design.length }
                ?.map { Step(listOf(it)) } ?: emptyList()
        )
        rootsSet.addAll(roots)
        var solutions = 0L
        while (roots.isNotEmpty()) {
            val curr = roots.pop()
            rootsSet.remove(curr)
            checked.add(curr)

            if (curr.str() == design) {
                solutions += 1
                continue
            }
            if (curr.str() != design.slice(curr.str().indices)) {
                continue
            }

            val remainder = design.slice((curr.str().lastIndex + 1)..design.lastIndex)
            if (remainderMemo.containsKey(remainder)) {
                solutions += remainderMemo[remainder] ?: 0
                continue
            }
            val nextRequiredChar = design.get(curr.str().lastIndex + 1)
            patternsLookup[nextRequiredChar]?.forEach { p ->
                val nextRoot = Step(curr.tokens + p)
                if (nextRoot !in checked && nextRoot.str().length <= design.length && !rootsSet.contains(nextRoot)) {
                    roots.push(nextRoot)
                }
            }
        }
        return solutions
    }


    override fun part2(): Long {
        val parts = File("inputs/day19.txt").readText().split("\n\n")
        val parsedInput = parseLines(parts)
        return computePart2(parsedInput)
    }

    override fun part1ResultDescription() = "Count of valid designs"

    override fun part2ResultDescription() = "Count of all possible valid design combination"
}