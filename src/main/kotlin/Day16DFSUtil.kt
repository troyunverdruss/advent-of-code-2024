import Utils.Direction
import Utils.Point
import java.util.*
import kotlin.math.min

class Day16DFSUtil(val grid: Map<Point, Char>) {
    private val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
    private val memo = mutableMapOf<Step, List<Path>>()

    data class Step(val pos: Point, val end: Point, val visited: Set<Point>) {
        var path: List<Point> = emptyList()
    }

    data class Path(val points: List<Point>) {
        val score = Day16.computeSolutionScore(points)
    }

    val start = grid.filter { it.value == 'S' }.keys.first()
    val end = grid.filter { it.value == 'E' }.keys.first()

    fun findPaths(): List<Path> {
        val paths = mutableListOf<Path>()
        var bestScore = Long.MAX_VALUE
        val stack = Stack<Step>()

        stack.push(Step(start, end, setOf()))
        while (stack.isNotEmpty()) {
            val step = stack.pop()
            val (pos, end, visited) = step
            val path = step.path

            if (memo.containsKey(step)) {
                paths.addAll(memo[step]!!)
                continue
            }

            val currPath = path + pos
            val currPathCost = Day16.computeSolutionScore(currPath)
            val currVisited = visited + pos

//            if (currPathCost > bestScore) {
//                continue
//            }

            if (pos == end) {
                val solutionPath = Path(currPath)
                paths.add(solutionPath)
                bestScore = min(solutionPath.score, bestScore)
                val existingSteps = memo[step] ?: listOf()
                memo[step] = existingSteps + solutionPath
                continue
            }

            val neighbors = neighborDirections.map { pos + it.point }
                .filter { !visited.contains(it) }
                .filter { listOf('.', 'S', 'E').contains(grid[it]) }

            if (neighbors.isEmpty()) {
                memo[step] = listOf()
            } else {
                neighbors.forEach { nextPos ->
                    val nextStep = Step(nextPos, end, currVisited)
                    nextStep.path = currPath
                    stack.push(nextStep)
                }
            }

        }

        return paths
    }
}