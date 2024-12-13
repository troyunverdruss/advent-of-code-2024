import java.util.LinkedList

class Day12 : Day{
    override fun part1(): Long {
        val grid = Day04.readGrid("inputs/day12.txt")

return computePart1(grid)
    }

     fun computePart1(grid: Map<Day04.Point, Char>): Long {
        val regions = findRegions(grid)
        return regions.map { region ->
            val area = region.size
            val perimeter = computePerimeter(grid, region)
            area * perimeter
        }.sum()
    }

    private fun computePerimeter(grid: Map<Day04.Point, Char>, region: Set<Pair<Day04.Point, Char>>): Long {
        return region.flatMap { entry ->
            directions.map { dir ->
                if (grid[entry.first + dir.point] != entry.second) {
                    1L
                } else {
                    0L
                }
            }
        }.sum()
    }

    private fun findRegions(grid: Map<Day04.Point, Char>): List<Set<Pair<Day04.Point, Char>>> {
        val used = mutableSetOf<Day04.Point>()
        return grid.entries.map { entry ->
            if (used.contains(entry.key)) {
                // Nothing to do for these, they've already been handled
                null
            } else {
                val region = findSingleRegion(grid, entry.key)
                used.addAll(region.map { it.first })
                region
            }
        }.filterNotNull()
    }

    val directions = listOf(Day04.Direction.UP, Day04.Direction.DOWN, Day04.Direction.LEFT, Day04.Direction.RIGHT)
    private fun findSingleRegion(grid: Map<Day04.Point, Char>, point: Day04.Point): Set<Pair<Day04.Point, Char>> {
        val visited = mutableSetOf<Day04.Point>()
        val toVisit = LinkedList<Day04.Point>()
        toVisit.add(point)
        val plantType = grid[point] ?: throw RuntimeException("Starting point is not valid")

        val region = mutableSetOf<Pair<Day04.Point, Char>>()

        while (toVisit.isNotEmpty()) {
            val curr = toVisit.pop()
            visited.add(curr)
            region.add(Pair(curr, plantType))

            directions.forEach { dir ->
                val testPoint = curr + dir.point
                if (grid[testPoint] != null && grid[testPoint] == plantType && !toVisit.contains(testPoint) && !visited.contains(testPoint)) {
                    toVisit.add(testPoint)
                }
            }
        }
        return region
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }


}
