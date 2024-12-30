import PathFinderUtil.Path
import Utils.Direction
import Utils.Point
import java.util.*

class DFSUtil(val grid: Map<Point, Char>) {
    private val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

    data class Step(val pos: Point, val end: Point, val visited: Set<Point>, val path: List<Point>)

    val start = grid.filter { it.value == 'S' }.keys.first()
    val end = grid.filter { it.value == 'E' }.keys.first()

    fun findPaths(): List<Path> {
        val paths = mutableListOf<Path>()
        val stack = Stack<Step>()
        stack.push(Step(start, end, setOf(), listOf()))
        while (stack.isNotEmpty()) {
            val (pos, end, visited, path) = stack.pop()

            val currPath = path + pos
            val currVisited = visited + pos

            if (pos == end) {
                paths.add(Path(currPath))
                continue
            }

            val neighbors = neighborDirections.map { pos + it.point }
                .filter { !visited.contains(it) }
                .filter { listOf('.', 'S', 'E').contains(grid[it]) }

            neighbors.forEach { nextPos ->
                stack.push(Step(nextPos, end, currVisited, currPath))
            }
        }

        return paths
    }
}