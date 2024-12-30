import Utils.Direction
import Utils.Point

class PathFinderUtil(val grid: Map<Point, Char>) {

    private val memo = mutableMapOf<Set<Point>, List<Path>>()
    data class Path(val points: List<Point>)

    fun find(pos: Point, end: Point, visited: Set<Point>, path: List<Point>): List<Path> {
        val currPath = path + pos
        val currVisited = visited + pos

        if (pos == end) {
            return listOf(Path(currPath))
        }

        val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
        val neighbors = neighborDirections.map { pos + it.point }
            .filter { !visited.contains(it) }
            .filter { listOf('.', 'S','E').contains(grid[it]) }

        val paths = neighbors.flatMap { nextPos ->
            find(nextPos, end, currVisited, currPath)
        }

        return paths
    }

}