import Utils.Direction
import Utils.Point
import java.io.File
import java.util.*
import kotlin.math.min

class Day16 : Day {
    override fun part1(): Long {
        val grid = Utils.parseGrid(File("inputs/day16.txt").readLines())
        return computePart1(grid)
    }

    override fun part2(): Long {
        val grid = Utils.parseGrid(File("inputs/day16.txt").readLines())
        return computePart2(grid)
    }

    override fun part1ResultDescription() = "lowest possible score"

    override fun part2ResultDescription() = "Unique points on best paths"

    data class FindState(val pos: Point, val dir: Direction) {
        var score: Long = 0
        var prev: FindState? = null
        var next: FindState? = null
    }

    data class Step(val pos: Point, val dir: Direction) {
        var prev: Step? = null
        var next: Step? = null
    }

    fun computePart1(grid: Map<Point, Char>): Long {
        val solutions = findSolutions(grid)

        val solution = solutions.minByOrNull { it.score }

        // Print out solution
        val path = getPathFromEnd(solution)
        path.forEach {
            println("${it.pos}, ${it.dir}: ${it.score}")
        }

        return solution?.score ?: throw RuntimeException("no path found")
    }

    private fun getPathFromEnd(solution: FindState?): List<FindState> {
        val path = mutableListOf<FindState>()
        var curr = solution
        while (curr != null) {
            path.add(curr)
            curr = curr.prev
        }
        return path.reversed()
    }

    private fun getPathFromEnd(end: Step): List<Point> {
        val path = mutableListOf<Point>()
        var curr: Step? = end
        while (curr != null) {
            path.add(curr.pos)
            curr = curr.prev
        }
        return path.reversed()
    }

    fun computePart2(grid: Map<Point, Char>): Long {
        val solutions = findSolutions1(grid)
//        val uniquePoints = mutableSetOf<Point>()
//        solutions.forEach { solution ->
//            var curr: FindState? = solution
//            while (curr != null) {
//                uniquePoints.add(curr.pos)
//                curr = curr.prev
//            }
//        }
        val uniquePoints = solutions.flatten().toSet()
        return uniquePoints.size.toLong()
    }

    private fun findSolutions1(
        grid: Map<Point, Char>,
    ): List<List<Point>> {
        val start = grid.filter { it.value == 'S' }.keys.first()
        val end = grid.filter { it.value == 'E' }.keys.first()

        val stack = Stack<Step>()
        val solutions = mutableListOf<List<Point>>()
        val memo = mutableMapOf<Step, List<List<Step>>>()

        stack.push(Step(start, Direction.RIGHT))

        while (stack.isNotEmpty()) {
            val curr = stack.pop()
            if (grid[curr.pos] == 'E') {
                solutions.add(getPathFromEnd(curr))
                continue
            }

            val used = getPathFromEnd(curr).toSet()
            val straight = curr.pos + curr.dir.point
            val leftDir = Direction.turnLeft90(curr.dir)
            val left = curr.pos + leftDir.point
            val rightDir = Direction.turnRight90(curr.dir)
            val right = curr.pos + rightDir.point

            if (!used.contains(straight) && grid[straight] != '#') {
                val step = Step(straight, curr.dir)
                step.prev = curr
                stack.push(step)
            }
            if (!used.contains(left) && grid[left] != '#') {
                val step = Step(left, leftDir)
                step.prev = curr
                stack.push(step)
            }
            if (!used.contains(right) && grid[right] != '#') {
                val step = Step(right, rightDir)
                step.prev = curr
                stack.push(step)
            }
        }

        val solutionsToScores = solutions.map { solution ->
            solution to computeSolutionScore(solution)
        }
        val lowestScore = solutionsToScores.minBy { it.second }.second

        return solutionsToScores.filter { it.second == lowestScore }.map { it.first }
    }

    private fun computeSolutionScore(solution: List<Point>): Long {
        var dir = Direction.RIGHT
        var last = solution[0]
        var score = 0L

        solution.slice(1..solution.lastIndex).forEach { curr ->
            when (curr) {
                last + dir.point -> {
                    score += 1
                    last = curr
                }

                last + Direction.turnLeft90(dir).point -> {
                    score += 1001
                    last = curr
                    dir = Direction.turnLeft90(dir)
                }

                last + Direction.turnRight90(dir).point -> {
                    score += 1001
                    last = curr
                    dir = Direction.turnRight90(dir)
                }

                else -> throw RuntimeException("path problem!")
            }
        }
        return score
    }

    private fun findSolutions(grid: Map<Point, Char>): List<FindState> {
        val start = grid.filter { it.value == 'S' }.keys.first()
        val end = grid.filter { it.value == 'E' }.keys.first()
        val visited = mutableSetOf<FindState>()
        val startingState = FindState(start, Direction.RIGHT)

        val toVisit = LinkedList(listOf(startingState))
        val solutions = mutableListOf<FindState>()
        var bestScore = Long.MAX_VALUE

        while (toVisit.isNotEmpty()) {
            val curr = toVisit.removeFirst()
            visited.add(curr)

            if (curr.pos == end) {
                solutions.add(curr)
                toVisit.removeIf { it.score >= curr.score }
                bestScore = min(curr.score, bestScore)
                continue
            }

            listOf(
                curr.pos + curr.dir.point to curr.dir,
                curr.pos + Direction.turnLeft90(curr.dir).point to Direction.turnLeft90(curr.dir),
                curr.pos + Direction.turnRight90(curr.dir).point to Direction.turnRight90(curr.dir)
            ).forEach { (pos, dir) ->
                val testState = FindState(pos, dir)

                testState.score = curr.score + 1
                if (dir != curr.dir) {
                    testState.score += 1000
                }
                testState.prev = curr
                if (grid[testState.pos] != '#') {
                    val alreadyVisitedCheaper = visited.filter { it == testState && it.score <= testState.score }
                    val toBeVisitedCheaper = toVisit.filter { it == testState && it.score <= testState.score }

                    if (alreadyVisitedCheaper.isEmpty() && toBeVisitedCheaper.isEmpty() && testState.score < bestScore) {
                        toVisit.removeIf { it == testState && it.score > testState.score }
                        toVisit.add(testState)
                    }
                }
            }
        }
        return solutions.filter { it.score == bestScore }
    }

    fun brokenComputePart1(grid: Map<Point, Char>): Long {
        val pathFinder = PathFinder(grid)
//        val memoizer = Memoizer(pathFinder.findPath)
        return pathFinder.findPath(FindPathArgs(pathFinder.start, Direction.LEFT, 0))
            ?: throw RuntimeException("no path found")
    }

}

data class FindPathArgs(
//    val visited: MutableSet<Pair<Point, Direction>>,
    val pos: Point,
    val dir: Direction,
    val score: Long
)

class PathFinder(val grid: Map<Point, Char>) {
    val start = grid.filter { it.value == 'S' }.keys.first()
    private val end = grid.filter { it.value == 'E' }.keys.first()


    fun findPath(findPathArgs: FindPathArgs): Long? {
        val pos = findPathArgs.pos
        val dir = findPathArgs.dir
        val score = findPathArgs.score
//        visited.add(Pair(pos, dir))

        if (pos == end) {
            return score
        }

        if (!(grid[pos] == '.' || grid[pos] == 'E' || grid[pos] == 'S')) {
            return null
        }

        // Straight
        val next = pos + dir.point
//        if (visited.contains(Pair(next, dir))) {
//            return null
//        }
        val straight = findPath(FindPathArgs(next, dir, score + 1))
        // Left
        val left = findPath(FindPathArgs(pos, Direction.turnLeft90(dir), score + 1000))
        // Right
        val right = findPath(FindPathArgs(pos, Direction.turnRight90(dir), score + 1000))

        return listOfNotNull(straight, left, right).minOrNull()
    }
}

class Memoizer<T, R>(val fn: (T) -> R) {
    private val memo = mutableMapOf<T, R>()

    fun call(arg: T): R {
        val result = memo[arg]
        if (result != null) return result

        val r = this.fn(arg)
        memo[arg] = r
        return r
    }
}