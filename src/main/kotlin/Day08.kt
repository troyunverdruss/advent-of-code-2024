class Day08 {
    fun part1(): Long {
        val grid = Utils.readGrid("inputs/day08.txt")
        return computePart1(grid)
    }

    fun computePart1(grid: Map<Utils.Point, Char>): Long {
        val frequencies = grid.values.distinct().filter { it != '.' }
        val antiNodeLocations = mutableSetOf<Utils.Point>()
        frequencies.forEach { frequency ->
            val freqLocs = grid.filter { it.value == frequency }.map { it.key }
            combinations(freqLocs).forEach { (a, b) ->
                val slope = slope(a, b)
                val possibleAntiNodes = listOf(
                    a + slope,
                    a + invertPoint(slope),
                    b + slope,
                    b + invertPoint(slope),
                )
                possibleAntiNodes
                    .filter { it != a }
                    .filter { it != b }
                    .filter { grid[it] != null }
                    .forEach { antiNodeLocations.add(it) }
            }
        }
        return antiNodeLocations.size.toLong()
    }

    fun part2(): Long {
        val grid = Utils.readGrid("inputs/day08.txt")
        return computePart2(grid)
    }

    fun computePart2(grid: Map<Utils.Point, Char>): Long {
        val frequencies = grid.values.distinct().filter { it != '.' }
        val antiNodeLocations = mutableSetOf<Utils.Point>()
        frequencies.forEach { frequency ->
            val freqLocs = grid.filter { it.value == frequency }.map { it.key }
            combinations(freqLocs).forEach { (a, b) ->
                val slope = slope(a, b)
                val invertedSlope = invertPoint(slope)
                antiNodeLocations.add(a)
                var testPoint = a
                while (grid[testPoint + slope] != null) {
                    antiNodeLocations.add(testPoint + slope)
                    testPoint += slope

                }
                testPoint = a
                while (grid[testPoint + invertedSlope] != null) {
                    antiNodeLocations.add(testPoint + invertedSlope)
                    testPoint += invertedSlope
                }
            }
        }
        return antiNodeLocations.size.toLong()
    }

    fun <T> combinations(list: List<T>): Sequence<Pair<T, T>> {
        return sequence {
            list.forEachIndexed { index, a ->
                list.slice(index + 1..list.lastIndex).forEach { b ->
                    yield(Pair(a, b))
                }
            }
        }
    }

    fun slope(a: Utils.Point, b: Utils.Point): Utils.Point {
        return Utils.Point(a.x - b.x, a.y - b.y)
    }

    fun invertPoint(point: Utils.Point): Utils.Point {
        return Utils.Point(-point.x, -point.y)
    }
}
