import Utils.Direction
import Utils.Point
import java.util.*

class Day16BFSUtils(val grid: Map<Point, Char>) {
    val start = grid.filter { it.value == 'S' }.keys.first()
    val end = grid.filter { it.value == 'E' }.keys.first()

    data class Step(val pos: Point, val dir: Direction) {
        var score: Long = 0
        var prev: Step? = null
    }

    fun find(): List<Step> {
        val solutions = mutableListOf<Step>()
        val visited = mutableMapOf<Step, Long>()
        val toVisit = LinkedList<Step>()
        val toVisitSet = mutableSetOf<Step>()

        val start = Step(start, Direction.RIGHT)
        toVisit.add(start)
        toVisitSet.add(start)

        while (toVisit.isNotEmpty()) {
            val curr = toVisit.removeFirst()
            toVisitSet.remove(curr)
            visited[curr] = curr.score

            if (curr.pos == end) {
                solutions.add(curr)
                continue
            }

            listOf(
                curr.pos + curr.dir.point to curr.dir,
                curr.pos + Direction.turnLeft90(curr.dir).point to Direction.turnLeft90(curr.dir),
                curr.pos + Direction.turnRight90(curr.dir).point to Direction.turnRight90(curr.dir)
            ).forEach { (pos, dir) ->
                val testState = Step(pos, dir)
                testState.score = curr.score + 1
                if (dir != curr.dir) {
                    testState.score += 1000
                }
                testState.prev = curr
                if (grid[testState.pos] != '#') {
                    val seen = visited[testState]

                    if ((seen == null || curr.score < seen)) {
                        toVisit.add(testState)
                        toVisitSet.add(testState)
                    }
                }
            }
        }
        return solutions
    }

    companion object {
         fun getPathPoints(step: Step): List<Point> {
            val path = mutableListOf<Point>()
            var curr: Step? = step
            while (curr != null) {
                path.add(curr.pos)
                curr = curr.prev
            }
            return path.reversed()
        }

    }
}