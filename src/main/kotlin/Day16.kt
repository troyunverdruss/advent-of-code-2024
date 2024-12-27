import Utils.Direction
import Utils.Point
import java.io.File

class Day16 : Day {
    override fun part1(): Long {
        val grid = Utils.parseGrid(File("inputs/day16.txt").readLines())
        return computePart1(grid)

    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription() = "lowest possible score"

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

    fun computePart1(grid: Map<Point, Char>): Long {
        val pathFinder = PathFinder(grid)
//        val memoizer = Memoizer(pathFinder.findPath)
        return pathFinder.findPath(FindPathArgs( pathFinder.start, Direction.LEFT, 0))
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
        val straight = findPath(FindPathArgs( next, dir, score + 1))
        // Left
        val left = findPath(FindPathArgs( pos, Direction.turnLeft90(dir), score + 1000))
        // Right
        val right = findPath(FindPathArgs( pos, Direction.turnRight90(dir), score + 1000))

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