class Day08 {
    fun part1(): Long {
        val grid = Day04.readGrid("inputs/day08.txt")
        return computePart1(grid)

    }

     fun computePart1(grid: Map<Day04.Point, Char>): Long {
        val frequencies = grid.values.distinct().filter { it != '.'}
        val antiNodeLocations = mutableSetOf<Day04.Point>()
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
                    .filter { it != a  }
                    .filter { it != b }
                    .filter { grid[it] != null}
                    .forEach { antiNodeLocations.add(it)}
            }
        }
        return antiNodeLocations.size.toLong()
    }

    fun part2(): Long {
        TODO("Not yet implemented")
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

    fun slope(a: Day04.Point, b: Day04.Point): Day04.Point {
        return Day04.Point(a.x - b.x, a.y - b.y)
    }

    fun invertPoint(point: Day04.Point): Day04.Point {
        return Day04.Point(-point.x, -point.y)
    }
}
