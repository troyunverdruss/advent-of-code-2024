class Day10 {
    fun part1(): Long {
        val grid = parseToLongsGrid(Day04.readGrid("inputs/day10.txt"))
        return computePart1(grid)
    }

    fun computePart1(grid: Map<Day04.Point, Long?>): Long {
        val starts = grid.filter { it.value == 0L }.map { it.key }
        return starts.map { computeTrailheadScore(grid, it).score }.sum()
    }

    private val dirs = listOf(Day04.Direction.UP, Day04.Direction.DOWN, Day04.Direction.LEFT, Day04.Direction.RIGHT)

    private data class SearchPoint(val pos: Day04.Point, val height: Long)

    data class TrailheadData(val score: Long, val rating: Long)
    fun computeTrailheadScore(grid: Map<Day04.Point, Long?>, trailheadStart: Day04.Point): TrailheadData {
        val start = SearchPoint(trailheadStart, 0)
        val visited = mutableSetOf<SearchPoint>()
        val toVisit = mutableListOf<SearchPoint>(start)

        var uniqueDestinations = mutableSetOf<Day04.Point>()
        while (toVisit.isNotEmpty()) {
            val cur = toVisit.removeFirst()
            visited.add(cur)
            if (cur.height == 9L) {
                uniqueDestinations.add(cur.pos)
                continue
            }

            dirs.forEach { dir ->
                val testPoint = SearchPoint(cur.pos + dir.point, cur.height + 1)


                if (grid[testPoint.pos] == testPoint.height && !visited.contains(testPoint) && !toVisit.contains(testPoint)) {
                    toVisit.add(testPoint)
                }
            }
        }

        return TrailheadData(uniqueDestinations.size.toLong(), -1)
    }

    fun parseToLongsGrid(readGrid: Map<Day04.Point, Char>): Map<Day04.Point, Long?> {
        return readGrid.map { it ->
            val value = if (it.value == '.') {
                null
            } else {
                "${it.value}".toLong()
            }
            Pair(it.key, value)
        }.toMap()
    }

    fun part2(): Long {
        TODO()
    }

}
