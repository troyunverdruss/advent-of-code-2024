import Utils.Direction
import Utils.Point
import java.io.File

class Day21 : Day {
    override fun part1(): Long {
        val inputCodes = File("inputs/day21.txt").readLines()
        return computePart1(inputCodes)
    }

    data class ResultPart1(val shortestLength: Long, val numericComponent: Long) {
        fun complexity(): Long {
            return shortestLength * numericComponent
        }
    }

    fun computePart1(inputCodes: List<String>): Long {
        return inputCodes.map { computeResultPart1(it) }.sumOf { it.complexity() }
    }

    fun computeResultPart1(inputCode: String): ResultPart1 {
        val shortestPaths = inputCodeToPairs(inputCode).map { startEndPair ->
            val paths =
                filterForShortestPaths(findPaths(startEndPair.first, startEndPair.second, keypadGrid)).map { it + 'A' }
            val arrowPaths1 = filterForShortestPaths(paths.flatMap { inputCodeToPaths(it, arrowpadGrid) })
            val arrowPaths2 = filterForShortestPaths(arrowPaths1.flatMap { inputCodeToPaths(it, arrowpadGrid) })

            arrowPaths2
        }
        val shortestLengthPerPath = shortestPaths
            .map { filterForShortestPaths(it) }
            .sumOf { it.first().length }.toLong()

        return ResultPart1(
            shortestLengthPerPath,
            inputCode.replace("A", "").toLong()
        )
    }

    fun inputCodeToPaths(inputCode: String, grid: Map<Point, Char>): List<String> {
        val paths = inputCodeToPairs(inputCode).map { startEndPair ->
            val x = findPaths(startEndPair.first, startEndPair.second, grid)
            println(x)
            x
        }

        val cpaths = cartesianProduct(paths)
        return filterForShortestPaths(cpaths)
    }

    fun inputCodeToPairs(inputCode: String): List<Pair<Char, Char>> {
        return inputCode.indices.map { i ->
            if (i == 0) {
                Pair('A', inputCode[i])
            } else {
                Pair(inputCode[i - 1], inputCode[i])
            }
        }
    }

    fun filterForShortestPaths(paths: List<String>): List<String> {
        val shortestLength = paths.minByOrNull { it.length }?.length ?: 0
        return paths.filter { it.length == shortestLength }
    }

    fun allPaths(paths: List<List<String>>): List<String> {
        if (paths.size == 1) {
            return paths[0].map { it + 'A' }
        }

        val firstPath = paths.first()
        val fullOptions = mutableListOf<String>()
        for (option in firstPath) {
            allPaths(paths.slice(1..paths.lastIndex)).forEach { subPath ->
                fullOptions.add(option + 'A' + subPath)
            }
        }

        return fullOptions
    }

    fun cartesianProduct(lists: List<List<String>>): List<String> {
        // Start with an empty list containing an empty string (acts as a base case for the iteration)
        var result = listOf("")

        // Iterate through each list in the input
        for (list in lists) {
            val tempResult = mutableListOf<String>()

            // For each string in the current result, combine it with each string in the current list
            for (prefix in result) {
                for (suffix in list) {
                    tempResult.add(prefix + suffix + 'A')
                }
            }

            // Update result for the next iteration
            result = tempResult
        }

        return result
    }


    data class SearchStep(val pos: Point) {
        var visited = setOf<Point>()
        var dirsUsed = setOf<Direction>()
        var prev: SearchStep? = null
        var direction: Direction? = null
    }

    fun findPaths(startKey: Char, endKey: Char, keypad: Map<Point, Char>): List<String> {
        val start = keypad.filter { it.value == startKey }.keys.first()
        val end = keypad.filter { it.value == endKey }.keys.first()


        val startStep = SearchStep(start)
        startStep.visited = setOf(start)
        val toVisit = mutableListOf<SearchStep>()
        val toVisitSet = mutableSetOf<SearchStep>()
        toVisit.add(startStep)
        toVisitSet.add(startStep)

        val paths = mutableListOf<String>()

        while (toVisit.isNotEmpty()) {
            val curr = toVisit.removeFirst()
            toVisitSet.remove(curr)

            if (curr.pos == end) {
                val thisPath = mutableListOf<Char>()
                var currStep: SearchStep? = curr
                while (currStep != null) {
                    if (currStep.prev?.pos != null) {
                        val calculatedDir = Direction.computeDirection(currStep.prev?.pos!!, currStep.pos)
                        thisPath.add(0, Direction.dirToChar(calculatedDir))
                    }
                    currStep = currStep.prev
                }
                paths.add(thisPath.joinToString(""))
            }

            Utils.cardinalDirections.forEach { direction ->
                val nextStep = SearchStep(curr.pos + direction.point)
                nextStep.prev = curr
                nextStep.direction = direction
                nextStep.dirsUsed = curr.dirsUsed + direction
                nextStep.visited = curr.visited + nextStep.pos

                if (
                    keypad[nextStep.pos] != null &&
                    !toVisitSet.contains(nextStep) &&
                    !curr.visited.contains(nextStep.pos)
                    && (!curr.dirsUsed.contains(direction) || curr.direction == direction)
                ) {
                    toVisit.add(nextStep)
                }
            }
        }

        return paths
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription() = "Sum of complexities"

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

    companion object {
        val keypad = """
                789
                456
                123
                #0A
            """.trimIndent().lines()
        val arrowpad = """
                #^A
                <v>
            """.trimIndent().lines()

        val keypadGrid = Utils.parseGrid(keypad).filter { it.value != '#' }
        val arrowpadGrid = Utils.parseGrid(arrowpad).filter { it.value != '#' }

    }

}