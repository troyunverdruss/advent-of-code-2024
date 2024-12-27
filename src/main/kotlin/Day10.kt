class Day10 {
    fun part1(): Long {
        val grid = parseToLongsGrid(Utils.readGrid("inputs/day10.txt"))
        return computePart1(grid)
    }

    fun computePart1(grid: Map<Utils.Point, Long?>): Long {
        val starts = grid.filter { it.value == 0L }.map { it.key }
        return starts.map { computeTrailheadScore(grid, it) }.sum()
    }

    fun computePart2(grid: Map<Utils.Point, Long?>): Long {
        val starts = grid.filter { it.value == 0L }.map { it.key }
        return starts.map { computeTrailheadRating(grid, it) }.sum()
    }

    private val dirs = listOf(Utils.Direction.UP, Utils.Direction.DOWN, Utils.Direction.LEFT, Utils.Direction.RIGHT)

    private data class SearchPoint(val pos: Utils.Point, val height: Long)

    private fun computeTrailheadScore(grid: Map<Utils.Point, Long?>, trailheadStart: Utils.Point): Long {
        val start = SearchPoint(trailheadStart, 0)
        val visited = mutableSetOf<SearchPoint>()
        val toVisit = mutableListOf<SearchPoint>(start)

        var uniqueDestinations = mutableSetOf<Utils.Point>()
        while (toVisit.isNotEmpty()) {
            val cur = toVisit.removeFirst()
            visited.add(cur)
            if (cur.height == 9L) {
                uniqueDestinations.add(cur.pos)
                continue
            }

            dirs.forEach { dir ->
                val testPoint = SearchPoint(cur.pos + dir.point, cur.height + 1)


                if (grid[testPoint.pos] == testPoint.height && !visited.contains(testPoint) && !toVisit.contains(
                        testPoint
                    )
                ) {
                    toVisit.add(testPoint)
                }
            }
        }

        return uniqueDestinations.size.toLong()
    }

    private fun computeTrailheadRating(grid: Map<Utils.Point, Long?>, trailheadStart: Utils.Point): Long {
        val start = SearchPoint(trailheadStart, 0)
        val toVisit = mutableListOf<SearchPoint>(start)

        var rating = 0L

        while (toVisit.isNotEmpty()) {
            val cur = toVisit.removeFirst()
            if (cur.height == 9L) {
                rating += 1
                continue
            }

            dirs.forEach { dir ->
                val testPoint = SearchPoint(cur.pos + dir.point, cur.height + 1)

                if (grid[testPoint.pos] == testPoint.height) {
                    toVisit.add(testPoint)
                }
            }
        }

        return rating
    }

    fun parseToLongsGrid(readGrid: Map<Utils.Point, Char>): Map<Utils.Point, Long?> {
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
        val grid = parseToLongsGrid(Utils.readGrid("inputs/day10.txt"))
        return computePart2(grid)
    }

}
