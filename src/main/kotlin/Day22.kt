import java.io.File

class Day22 : Day {
    override fun part1(): Long {
        val cache = mutableMapOf<List<Long>, Long>()
        return File("inputs/day22.txt").readLines()
            .map { it.toLong() }
            .map { genSecrets(it, cache) }
            .sum()
    }

    class Deltas {
        private var count = 0
        private val deltas = mutableListOf<Long>()
        fun offer(n: Long) {
            count += 1
            deltas.addLast(n)
            if (count == 5) {
                deltas.removeFirst()
                count -= 1
            }
        }

        fun ready() = count == 4
        fun key() = deltas.toList()
    }

    fun genSecrets(input: Long, cache: MutableMap<List<Long>, Long>): Long {
        val deltas = Deltas()
        val seenKeys = mutableSetOf<List<Long>>()
        var num = input
        repeat(2000) {
            val newNum = generateNextNumber(num)
            val d = (newNum % 10) - (num % 10)
//            if (d >= 10 || d <= -10) {
//                println("delta too large: $d")
//            }
            deltas.offer(d)
//            println("${num.toString().padStart(10, ' ')}: ${num % 10} ($d)")
            if (deltas.ready()) {
                val key = deltas.key()
                if (!seenKeys.contains(key)) {
                    val currVal = cache[key] ?: 0
                    cache[key] = currVal + newNum % 10
                }
                seenKeys.add(key)
            }
            num = newNum
        }
        return num
    }

    fun generateNextNumber(secretNumber: Long): Long {
        var num = secretNumber
        val modulo = 16777216L

        num = (num xor (num * 64)) % modulo
        num = (num xor (num / 32)) % modulo
        num = (num xor (num * 2048)) % modulo

        return num
    }

    override fun part2(): Long {
        val input = File("inputs/day22.txt")
            .readLines()
            .map { it.toLong() }

        return computePart2(input)
    }

    fun computePart2(input: List<Long>): Long {
        val cache = mutableMapOf<List<Long>, Long>()

        input.map { genSecrets(it, cache) }
        return cache.map { it.value }.maxOrNull()!!
    }

    override fun part1ResultDescription() = "Sum of all 2000th numbers"

    override fun part2ResultDescription() = "Max bananas that can be earned"
}
