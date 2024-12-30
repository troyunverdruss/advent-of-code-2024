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
        val start = grid.filter { it.value == 'S' }.keys.first()
//        val possiblePaths = findPaths(grid, Step(start, Direction.RIGHT))
//        val paths = possiblePaths?.map { getPathFromEnd(it) } ?: throw RuntimeException("no path")

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
        val bfsUtils = Day16BFSUtils(grid)
        val solutions = bfsUtils.find()
        val best = solutions.map { it.score }.min()
        val uniquePoints = solutions.filter { it.score == best }.flatMap { Day16BFSUtils.getPathPoints(it) }.toSet()
        return uniquePoints.size.toLong()



//        val solutions = findSolutions(grid)
//        val cheapestScore = solutions.minByOrNull { it.score }!!.score
//        val cheapestSolutions = solutions.filter { it.score ==  cheapestScore}
//        val uniquePoints = cheapestSolutions.flatMap { getPathFromEnd(it).map { it.pos } }.toSet()
//        return uniquePoints.size.toLong()
//
//        val start = grid.filter { it.value == 'S' }.keys.first()
//        val end = grid.filter { it.value == 'E' }.keys.first()
//        val pathFinderUtil = PathFinderUtil(grid)
//        val paths = pathFinderUtil.find(start, end, setOf(), listOf())

        val dfsUtil = Day16DFSUtil(grid)
        val paths = dfsUtil.findPaths()

        val pathsToScores = paths.map { path ->
            path to computeSolutionScore(path.points)
        }
        val lowestScore = pathsToScores.minBy { it.second }.second

        return pathsToScores.filter { it.second == lowestScore }.flatMap { it.first.points }.toSet().size.toLong()




//        val possiblePaths = findPaths(grid, start, setOf())
////        val paths = possiblePaths?.map { getPathFromEnd(it) } ?: throw RuntimeException("no path")
//
////        val pathsToScores = paths.map { path ->
////            path to computeSolutionScore(path)
////        }
////        val lowestScore = pathsToScores.minBy { it.second }.second
////
////        val bestPaths = pathsToScores.filter { it.second == lowestScore }.map { it.first }
////        val uniquePoints = bestPaths.flatten().toSet()
////        return uniquePoints.size.toLong()
//
//
//        val intersections = grid.map { (p, v) ->
//            val neighbors = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
//            val vals = neighbors.mapNotNull { grid[p + it.point] }.filter { it != '#' }
//            if (vals.size > 2) {
//                p
//            } else {
//                null
//            }
//        }.filterNotNull()
//
//TODO()
////        val solutions = findSolutions1(grid)
////        val uniquePoints = mutableSetOf<Point>()
////        solutions.forEach { solution ->
////            var curr: FindState? = solution
////            while (curr != null) {
////                uniquePoints.add(curr.pos)
////                curr = curr.prev
////            }
////        }
////        val uniquePoints = solutions.flatten().toSet()
////        return uniquePoints.size.toLong()
    }

    private fun findSolutions1(
        grid: Map<Point, Char>,
    ): List<List<Point>> {
        val start = grid.filter { it.value == 'S' }.keys.first()

        val stack = Stack<Step>()
        val solutions = mutableListOf<List<Point>>()
        val memo = mutableMapOf<Point, List<List<Point>>>()
        grid.forEach { (p, v) ->
            if (v == '#') {
                memo[p] = listOf(listOf())
            }
        }


        stack.push(Step(start, Direction.RIGHT))

        while (stack.isNotEmpty()) {
            val curr = stack.pop()
            if (grid[curr.pos] == 'E') {
                solutions.add(getPathFromEnd(curr))
                memo[curr.prev!!.pos] = listOf(listOf(curr.pos))
                continue
            }

            val used = getPathFromEnd(curr).toSet()
            val straight = curr.pos + curr.dir.point
            val leftDir = Direction.turnLeft90(curr.dir)
            val left = curr.pos + leftDir.point
            val rightDir = Direction.turnRight90(curr.dir)
            val right = curr.pos + rightDir.point

            val stepStraight = Step(straight, curr.dir)
            stepStraight.prev = curr
            val stepLeft = Step(left, leftDir)
            stepLeft.prev = curr
            val stepRight = Step(right, rightDir)
            stepRight.prev = curr

//            if (
//                memo.containsKey(straight) &&
//                memo.containsKey(left) &&
//                memo.containsKey(right)
//            ) {
//                val tailsStraight = memo[straight]
//                val tailsLeft = memo[left]
//                val tailsRight = memo[right]
//                val newTailsStraight = updateTails(tailsStraight, curr)
//                val newTailsLeft = updateTails(tailsLeft, curr)
//                val newTailsRight = updateTails(tailsRight, curr)
//                val newTails = mutableListOf<List<Point>>()
//                newTails.addAll(newTailsStraight ?: listOf())
//                newTails.addAll(newTailsLeft ?: listOf())
//                newTails.addAll(newTailsRight ?: listOf())
//                memo[curr.pos] = newTails
//

//
//
//
//
//
//                val tails = memo[stepStraight]!!
//                    val newTails = tails.map { tail ->
//                        val sol = mutableListOf(curr)
//                        sol.addAll(tail)
//                        sol
//                    }
//                    memo[curr] = newTails
//            } else {
            if (!used.contains(straight) && grid[straight] != '#') {
                stack.push(stepStraight)
            }
            if (!used.contains(left) && grid[left] != '#') {
                stack.push(stepLeft)
            }
            if (!used.contains(right) && grid[right] != '#') {
                stack.push(stepRight)
            }
//            }
        }

        val solutionsToScores = solutions.map { solution ->
            solution to computeSolutionScore(solution)
        }
        val lowestScore = solutionsToScores.minBy { it.second }.second

        return solutionsToScores.filter { it.second == lowestScore }.map { it.first }
    }

    private fun updateTails(
        tailsStraight: List<List<Point>>?,
        curr: Step
    ) = tailsStraight?.map { tail ->
        val sol = mutableListOf(curr.pos)
        sol.addAll(tail)
        sol
    }


    private val findPathMemo = mutableMapOf<Point, List<List<Point>>?>()

    private fun findPaths(grid: Map<Point, Char>, pos: Point, visited: Set<Point>): List<List<Point>>? {
        if (findPathMemo.containsKey(pos)) {
            return findPathMemo[pos]
        }

        if (grid[pos] == 'E') {
            findPathMemo[pos] = listOf(listOf(pos))
            return listOf(listOf(pos))
        }
        if (grid[pos] == '#') {
            findPathMemo[pos] = null
            return null
        }

        val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
        val neighbors = neighborDirections.map { pos + it.point }
        val paths = neighbors
            .filter { !visited.contains(it) }
            .flatMap { findPaths(grid, it, visited + it) ?: listOf() }

        findPathMemo[pos] = paths
        return paths
    }

    val findPathMemo2 =
        mutableMapOf<Pair<Point, Set<Point>>, List<List<Point>>?>()

    private fun findPathsPoints(grid: Map<Point, Char>, pos: Point, visited: Set<Point>): List<List<Point>>? {
        if (findPathMemo2.containsKey(Pair(pos, visited))) {
            return findPathMemo[pos]
        }

        if (grid[pos] == 'E') {
            findPathMemo[pos] = listOf(listOf(pos))
            return listOf(listOf(pos))
        }
        if (grid[pos] == '#') {
            findPathMemo[pos] = null
            return null
        }

        val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
        val neighbors = neighborDirections.map { pos + it.point }
        val paths = neighbors
            .filter { !visited.contains(it) }
            .flatMap { findPaths(grid, it, visited + it) ?: listOf() }

        findPathMemo[pos] = paths
        return paths
    }


    private fun findSolutions(grid: Map<Point, Char>): List<FindState> {
        val start = grid.filter { it.value == 'S' }.keys.first()
        val end = grid.filter { it.value == 'E' }.keys.first()
        val visited = mutableSetOf<FindState>()
        val startingState = FindState(start, Direction.RIGHT)

        val toVisit = LinkedList(listOf(startingState))
        val solutions = mutableListOf<FindState>()
        var bestScore = Long.MAX_VALUE

        val bestScoreAtNode = mutableMapOf<Pair<Point, Direction>, Long>()

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

    companion object {

        fun computeSolutionScore(path: List<Point>): Long {
            var dir = Direction.RIGHT
            var last = path[0]
            var score = 0L

            path.slice(1..path.lastIndex).forEach { curr ->
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