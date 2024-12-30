import Utils.Direction
import Utils.Point

class PathFinderUtil(val grid: Map<Point, Char>) {

    private val neighborDirections = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)
    private val memo = mutableMapOf<Set<Point>, List<Path>>()
    data class Path(val points: List<Point>)
    data class Edge(val a: Point, val b: Point, val points: List<Point>)
    data class Node(
        val pos: Point,
        var up: Edge? = null,
        var down: Edge? = null,
        var left: Edge? = null,
        var right: Edge? = null,
    )
    private val nodes = findNodes()
    fun findNodes(): List<Node> {
        val nodes = mutableListOf<Node>()
        val validPositions = grid.filter { listOf('S', '.', 'E').contains(it.value) }
        validPositions.forEach { (p, v) ->
            val neighborLookup = neighborDirections.map { dir ->
                dir to p + dir.point
            }.toMap()
            if (neighborLookup.map { grid[it.value] }.count { listOf('S', '.', 'E').contains(it) } >= 3) {
                nodes.add(Node(p))
            } else {
                val upDownVals = listOf(grid[neighborLookup[Direction.UP]], grid[neighborLookup[Direction.DOWN]])
                val leftRightVals = listOf(grid[neighborLookup[Direction.LEFT]], grid[neighborLookup[Direction.RIGHT]])
                if (upDownVals.all { it == '#' } || leftRightVals.all { it == '#' }) {
                    // We're in a passageway or a dead end
                } else {
                    nodes.add(Node(p))
                }
            }
        }

        connectNodes(nodes)

        return nodes
    }

    private fun connectNodes(nodes: MutableList<Node>) {

    }

    fun find(pos: Point, end: Point, visited: Set<Point>, path: List<Point>): List<Path> {
        val currPath = path + pos
        val currVisited = visited + pos

        if (pos == end) {
            return listOf(Path(currPath))
        }

        val neighbors = neighborDirections.map { pos + it.point }
            .filter { !visited.contains(it) }
            .filter { listOf('.', 'S','E').contains(grid[it]) }

        val paths = neighbors.flatMap { nextPos ->
            find(nextPos, end, currVisited, currPath)
        }

        return paths
    }

}