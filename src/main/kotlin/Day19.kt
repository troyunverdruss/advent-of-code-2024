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

    fun isPossibleDesign(patterns: List<String>, design: String): Boolean {
        val patternsLookup = patterns.map { it.first() to it }.toMap()
        println("testing for design: $design")
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

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription() = "Count of valid designs"

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }
}