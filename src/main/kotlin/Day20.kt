import Utils.Direction
import Utils.Point
import java.util.*

class Day20 : Day {
    override fun part1(): Long {
        val grid = Utils.readGrid("inputs/day20.txt")
        val path = findThePath(grid)
        val cheats = findCheats(path, 2)
        return cheats.count { c -> c.savings >= 100L }.toLong()
    }

    private fun findCheats(path: List<Point>, maxDistance: Long): List<Cheat> {
        val pathPointToIndex = path.mapIndexed { index, point -> point to index }.toMap()

        return path.mapIndexed { cheatStartIndex, cheatStart ->
            path.slice((cheatStartIndex + maxDistance.toInt())..path.lastIndex).mapNotNull { cheatEnd ->
                val currDistance = Utils.distance(cheatStart, cheatEnd)
                val cheatEndIndex =
                    pathPointToIndex[cheatEnd] ?: throw RuntimeException("couldn't find index for point")
                val savings = cheatEndIndex - cheatStartIndex - currDistance.toInt()
                if (currDistance <= maxDistance && savings > currDistance) {
                    Cheat(cheatStart, cheatEnd, savings)
                } else {
                    null
                }
            }
        }.flatten()
    }

    private data class Cheat(val start: Point, val end: Point, val savings: Int)

    private data class Step(val pos: Point) {
        var prev: Step? = null
    }

    private fun findThePath(grid: Map<Point, Char>): List<Point> {
        val track = listOf('S', 'E', '.')
        val toVisit = Stack<Step>()
        val toVisitSet = mutableSetOf<Step>()
        val visited = mutableSetOf<Step>()

        // Start, End
        val start = grid.entries.find { it.value == 'S' }?.key ?: throw RuntimeException("no start")
        val end = grid.entries.find { it.value == 'E' }?.key ?: throw RuntimeException("no end")

        toVisit.push(Step(start))
        toVisitSet.addAll(toVisit)

        while (toVisit.isNotEmpty()) {
            val curr = toVisit.pop()
            toVisitSet.remove(curr)
            visited.add(curr)

            if (curr.pos == end) {
                val path = mutableListOf<Point>()
                var last: Step? = curr
                while (last != null) {
                    path.add(last.pos)
                    last = last.prev
                }

                return path.reversed()
            }
            Utils.cardinalDirections.forEach { dir: Direction ->
                val nextStep = Step(curr.pos + dir.point)
                nextStep.prev = curr

                if (!visited.contains(nextStep) && !toVisitSet.contains(nextStep) && track.contains(grid[nextStep.pos])) {
                    toVisit.add(nextStep)
                    toVisitSet.add(nextStep)
                }
            }
        }
        throw RuntimeException("no path found")
    }

    override fun part2(): Long {
        val grid = Utils.readGrid("inputs/day20.txt")
        val path = findThePath(grid)
        val cheats = findCheats(path, 20)
        return cheats.count { c -> c.savings >= 100L }.toLong()
    }

    override fun part1ResultDescription() = "Number of cheats that save 100+ picoseconds (with <=2 cheat)"

    override fun part2ResultDescription() = "Number of cheats that save 100+ picoseconds (with <=20 cheat)"
}