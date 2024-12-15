import java.util.LinkedList

class Day12 : Day {
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
    fun debugPrint(region: Set<Pair<Day04.Point, Char>>, area: Int, sides: Long){
        val map = region.toMap()
        val minX = region.minBy { it.first.x }.first.x
        val minY = region.minBy { it.first.y }.first.y
        val maxX = region.maxBy { it.first.x }.first.x
        val maxY = region.maxBy { it.first.y }.first.y
        (minX..maxX).forEach { x ->
            (minY..maxY).forEach { y ->
                print(map[Day04.Point(x,y)] ?: '.')
            }
            println()
        }
        println("Area: $area")
        println("Sides: $sides")
    }

    fun computePart2(grid: Map<Day04.Point, Char>): Long {
        val regions = findRegions(grid)
        val costs = regions.map { region ->
            val area = region.size
            val sides = computeNumberOfSides(grid, region)
            debugPrint(region, area, sides)
            area * sides
        }
        return costs.sum()
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

    data class Edge(val start: Day04.Point, val end: Day04.Point) {
        fun slope(): Day04.Point {
            return Day04.Point(end.x - start.x, end.y - start.y)
        }
    }

    fun computeNumberOfSides(grid: Map<Day04.Point, Char>, region: Set<Pair<Day04.Point, Char>>): Long {
        val allEdges = region.flatMap { entry ->
            directions.map { dir ->
                when (dir) {
                    Day04.Direction.UP -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(entry.first, entry.first + Day04.Direction.RIGHT.point)
                        } else {
                            null
                        }
                    }

                    Day04.Direction.DOWN -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(
                                entry.first + Day04.Direction.DOWN.point,
                                entry.first + Day04.Direction.DOWN_RIGHT.point
                            )
                        } else {
                            null
                        }
                    }

                    Day04.Direction.LEFT -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(entry.first, entry.first + Day04.Direction.DOWN.point)
                        } else {
                            null
                        }
                    }

                    Day04.Direction.RIGHT -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(
                                entry.first + Day04.Direction.RIGHT.point,
                                entry.first + Day04.Direction.DOWN_RIGHT.point
                            )
                        } else {
                            null
                        }
                    }

                    else -> throw RuntimeException("Unknown direction")
                }
            }
        }.filterNotNull()

        val counts = mutableMapOf<Edge, Long>()
        allEdges.forEach { edge ->
            val count = counts[edge] ?: 0L
            counts[edge] = count + 1
        }

        val exteriorEdges = allEdges.filter { counts[it] == 1L }.toMutableSet()
        val usedEdges = mutableSetOf<Edge>()

        val startingEdge = exteriorEdges.first()
        usedEdges.add(startingEdge)

        var startingPoint = startingEdge.start
        var currentEdge = startingEdge
        var currentPoint = startingEdge.end
//        exteriorEdges.remove(currentEdge)
        var edgeCount = 1L
//        println(currentEdge)
        while (usedEdges.size != exteriorEdges.size) {
            while (currentPoint != startingPoint) {
                var nextEdge = exteriorEdges.find {
                    (currentPoint == it.start || currentPoint == it.end) && !usedEdges.contains(it)
                } ?: throw RuntimeException("Couldn't find next edge")
                val possibleNextEdges = exteriorEdges.filter {
                    (currentPoint == it.start || currentPoint == it.end) && !usedEdges.contains(it)
                }
                for (pe in possibleNextEdges) {
                    if (pe.slope() != currentEdge.slope()) {
                        nextEdge = pe
                        break
                    }
                }
//            exteriorEdges.remove(nextEdge)
//                println(nextEdge)
                if (currentEdge.slope() != nextEdge.slope()) {
                    edgeCount++
                }
                usedEdges.add(nextEdge)
                currentEdge = nextEdge
                currentPoint = if (currentPoint == nextEdge.start) {
                    nextEdge.end
                } else {
                    nextEdge.start
                }
            }
            if (exteriorEdges.minus(usedEdges).isEmpty()) {
                break
            }
            currentEdge = exteriorEdges.minus(usedEdges).first()
            usedEdges.add(currentEdge)
            startingPoint = currentEdge.start
            currentPoint = currentEdge.end
            edgeCount += 1

        }

        return edgeCount






        var combineSuccess = false
//        var first = true
//        while (first || combineSuccess) {
//            first = false
//            combineSuccess = false
//            val newEdges = mutableSetOf<Pair<Day04.Point, Day04.Point>>()
//            val processedEdges = mutableSetOf<Pair<Day04.Point, Day04.Point>>()
//
//            for (edge in exteriorEdges) {
//                if (processedEdges.contains(edge)) {
//                    continue
//                }
//
//                val slope = getSlope(edge)
//                var crawling = true
//                var lastEdge = edge
//                var nextEdge = edge
//                processedEdges.add(edge)
//                while (crawling) {
//                    nextEdge = Pair(lastEdge.second, lastEdge.second + slope)
//                    if (!exteriorEdges.contains(nextEdge)) {
//                        crawling = false
//                        newEdges.add(Pair(edge.first, lastEdge.second))
//                    } else {
//                        processedEdges.add(nextEdge)
//                    }
//                    lastEdge = nextEdge
//                }
//                if (exteriorEdges.contains(nextEdge)) {
//                    newEdges.add(Pair(edge.first, edge.second + slope))
//                    processedEdges.add(edge)
//                    processedEdges.add(nextEdge)
//                    combineSuccess = true
//                } else {
//                    newEdges.add(edge)
//                    processedEdges.add(edge)
//                }
//            }
//            exteriorEdges.clear()
//            exteriorEdges.addAll(newEdges)
//        }
//
//        return exteriorEdges.size.toLong()
//        //        val minX = grid.keys.minOf { it.x }
////        val start = grid.keys.filter { it.x == minX }.minBy { it.y }
////
////        // Make sure that we're in an upper left corner of the shape
////        if (grid[start + Day04.Direction.UP.point] == grid[start] || grid[start + Day04.Direction.LEFT.point] == grid[start]) {
////            throw RuntimeException("Expected to be in top left corner and wasn't")
////        }
////
////        var sides = 1
////        var pos = start
////        var currDir = Day04.Direction.RIGHT
////        while (sides == 1 || pos != start) {
////            var nextStep =
////        }

        val plantType = region.first().second

        // ...
        // .X.
        // ...
        if (region.size == 1) {
            return 4
        }

        // ...
        // .X.
        // .x.
        val peninsulas = region.filter { point ->
            directions.map { dir ->
                grid[point.first + dir.point]
            }.count { it != plantType } == 3
        }

        // ...
        // .Xx
        // .xx
        val outerCorners = region.filter { point ->
            directions.map { dir ->
                grid[point.first + dir.point]
            }.count { it != plantType } == 2
        }

        // .xx
        // xXx
        // xxx
//        val innerCorners = region.filter { point ->
//            val neighbors = directions.map { dir ->
//                grid[point.first + dir.point]
//            }
//            if (neighbors.count { it == plantType } == 4 && ne
//        }


//        TODO()
    }

    private fun getSlope(edge: Pair<Day04.Point, Day04.Point>): Day04.Point {
        val x = edge.second.x - edge.first.x
        val y = edge.second.y - edge.first.y

        val newX = if (x == 0L) {
            0L
        } else {
            1L
        }

        val newY = if (y == 0L) {
            0L
        } else {
            1L
        }

        return Day04.Point(newX, newY)
    }


     fun findRegions(grid: Map<Day04.Point, Char>): List<Set<Pair<Day04.Point, Char>>> {
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
                if (grid[testPoint] != null && grid[testPoint] == plantType && !toVisit.contains(testPoint) && !visited.contains(
                        testPoint
                    )
                ) {
                    toVisit.add(testPoint)
                }
            }
        }
        return region
    }

    override fun part2(): Long {
        val grid = Day04.readGrid("inputs/day12.txt")
        return computePart2(grid)
    }


}
