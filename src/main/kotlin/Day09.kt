import java.io.File

class Day09 {
    fun part1(): Long {
        val input = parseInput(readInput())
        return computePart1(input)
    }

    fun computePart1(input: List<Long>): Long {
        val memory = createMemoryLayout(input)
        val defraggedMemory = defragMemory(memory)
        return computeChecksum(defraggedMemory)
    }

    fun computePart2(input: List<Long>): Long {
        val memory = createMemoryLayout(input)
        val defraggedMemory = defragMemoryKeepingFilesIntact(memory)
        return computeChecksum(defraggedMemory)
    }

    private fun computeChecksum(defraggedMemory: MutableList<Long?>): Long {
        return defraggedMemory.mapIndexed { index, v -> index * (v ?: 0) }.sum()
    }

    fun defragMemory(memory: MutableList<Long?>): MutableList<Long?> {
        var frontIndex = 0
        var tailIndex = memory.lastIndex
        while (true) {
            while (memory[frontIndex] != null) {
                frontIndex += 1
            }
            while (memory[tailIndex] == null) {
                tailIndex -= 1
            }
            // Don't accidentally swap back
            if (tailIndex < frontIndex) {
                break
            }
            memory[frontIndex] = memory[tailIndex]
            memory[tailIndex] = null
        }
        return memory
    }

    fun defragMemoryKeepingFilesIntact(memory: MutableList<Long?>): MutableList<Long?> {

        var tailIndexStart: Int? = null
        var tailIndexEnd: Int? = null

        var frontIndex = 0
        var frontSeekIndex = 0

        val highestFileId = memory.maxBy { it ?: 0 } ?: throw RuntimeException("No numbers found!")
        var targetId = highestFileId

        while (targetId >= 0) {
            while (frontIndex <= memory.lastIndex && memory[frontIndex] != null) {
                frontIndex += 1
            }
            frontSeekIndex = frontIndex
            while (frontSeekIndex <= memory.lastIndex && memory[frontSeekIndex] == null) {
                frontSeekIndex += 1
            }

            if (tailIndexStart == null || tailIndexEnd == null) {
                tailIndexStart = 0
                while (tailIndexStart <= memory.lastIndex && memory[tailIndexStart] != targetId) {
                    tailIndexStart += 1
                }
                tailIndexEnd = tailIndexStart
                while (tailIndexEnd <= memory.lastIndex && memory[tailIndexEnd] == targetId) {
                    tailIndexEnd += 1
                }
            }


            val freeSpace = frontSeekIndex - frontIndex
            val fileLength = tailIndexEnd - tailIndexStart

            if (frontIndex >= memory.lastIndex) {
                frontIndex = 0
                tailIndexStart = null
                targetId -= 1
            } else if (frontIndex >= tailIndexStart) {
                frontIndex = 0
                tailIndexStart = null
                targetId -= 1
            } else if (fileLength <= freeSpace) {
                (frontIndex..<frontIndex + fileLength).forEach {
                    memory[it] = targetId
                }

                (tailIndexStart..<tailIndexEnd).forEach {
                    memory[it] = null
                }
                frontIndex = 0
                tailIndexStart = null
                targetId -= 1
            } else {
                frontIndex = frontSeekIndex
            }
        }

        return memory
    }

    fun createMemoryLayout(input: List<Long>): MutableList<Long?> {
        var id = -1L
        val memory = input.flatMapIndexed { index, v ->
            if (index % 2 == 0) {
                id += 1
                (0..<v).map { id }
            } else {
                (0..<v).map { null }
            }
        }.toMutableList()
        return memory
    }

    fun part2(): Long {
        val input = parseInput(readInput())
        return computePart2(input)
    }

    fun parseInput(lines: String): List<Long> {
        return lines.map { "$it".toLong() }
    }

    private fun readInput(): String {
        return File("inputs/day09.txt").readLines().filter { it.isNotEmpty() }.first()
    }

}
