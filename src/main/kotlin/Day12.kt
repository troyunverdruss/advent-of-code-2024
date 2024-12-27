import Utils.debugPrint
import java.util.LinkedList

class Day12 : Day {
    override fun part1(): Long {
        val grid = Utils.readGrid("inputs/day12.txt")
        return computePart1(grid)
    }

    fun computePart1(grid: Map<Utils.Point, Char>): Long {
        val regions = findRegions(grid)
        return regions.map { region ->
            val area = region.size
            val perimeter = computePerimeter(grid, region)
            area * perimeter
        }.sum()
    }


    fun computePart2(grid: Map<Utils.Point, Char>): Long {
        val regions = findRegions(grid)
        val costs = regions.map { region ->
            val area = region.size
            val sides = computeNumberOfSides(grid, region)
            debugPrint(region, area, sides)
            area * sides
        }
        return costs.sum()
    }

    private fun computePerimeter(grid: Map<Utils.Point, Char>, region: Set<Pair<Utils.Point, Char>>): Long {
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

    data class Edge(val start: Utils.Point, val end: Utils.Point) {
        fun slope(): Utils.Point {
            return Utils.Point(end.x - start.x, end.y - start.y)
        }
    }

    fun computeNumberOfSides(grid: Map<Utils.Point, Char>, region: Set<Pair<Utils.Point, Char>>): Long {
        var sideCount = 0L
        val points = region.map { it.first }

        points.forEach { p ->
            // xP
            // PP
            if (points.contains(p.up()) && points.contains(p.left()) && !points.contains(p.upLeft())) {
                sideCount += 1
            }
            if (points.contains(p.up()) && points.contains(p.right()) && !points.contains(p.upRight())) {
                sideCount += 1
            }
            if (points.contains(p.down()) && points.contains(p.left()) && !points.contains(p.downLeft())) {
                sideCount += 1
            }
            if (points.contains(p.down()) && points.contains(p.right()) && !points.contains(p.downRight())) {
                sideCount += 1
            }
            // ?x
            // xP
            if (!points.contains(p.up()) && !points.contains(p.left())) {
                sideCount += 1
            }
            if (!points.contains(p.up()) && !points.contains(p.right())) {
                sideCount += 1
            }
            if (!points.contains(p.down()) && !points.contains(p.left())) {
                sideCount += 1
            }
            if (!points.contains(p.down()) && !points.contains(p.right())) {
                sideCount += 1
            }
        }
        return sideCount
    }


    fun computeNumberOfSidesBroken(grid: Map<Utils.Point, Char>, region: Set<Pair<Utils.Point, Char>>): Long {
        val allEdges = region.flatMap { entry ->
            directions.map { dir ->
                when (dir) {
                    Utils.Direction.UP -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(entry.first, entry.first + Utils.Direction.RIGHT.point)
                        } else {
                            null
                        }
                    }

                    Utils.Direction.DOWN -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(
                                entry.first + Utils.Direction.DOWN.point,
                                entry.first + Utils.Direction.DOWN_RIGHT.point
                            )
                        } else {
                            null
                        }
                    }

                    Utils.Direction.LEFT -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(entry.first, entry.first + Utils.Direction.DOWN.point)
                        } else {
                            null
                        }
                    }

                    Utils.Direction.RIGHT -> {
                        if (grid[entry.first + dir.point] != entry.second) {
                            Edge(
                                entry.first + Utils.Direction.RIGHT.point,
                                entry.first + Utils.Direction.DOWN_RIGHT.point
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
//            val newEdges = mutableSetOf<Pair<Utils.Point, Utils.Point>>()
//            val processedEdges = mutableSetOf<Pair<Utils.Point, Utils.Point>>()
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
////        if (grid[start + Utils.Direction.UP.point] == grid[start] || grid[start + Utils.Direction.LEFT.point] == grid[start]) {
////            throw RuntimeException("Expected to be in top left corner and wasn't")
////        }
////
////        var sides = 1
////        var pos = start
////        var currDir = Utils.Direction.RIGHT
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

    private fun getSlope(edge: Pair<Utils.Point, Utils.Point>): Utils.Point {
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

        return Utils.Point(newX, newY)
    }


    fun findRegions(grid: Map<Utils.Point, Char>): List<Set<Pair<Utils.Point, Char>>> {
        val used = mutableSetOf<Utils.Point>()
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

    val directions = listOf(Utils.Direction.UP, Utils.Direction.DOWN, Utils.Direction.LEFT, Utils.Direction.RIGHT)
    private fun findSingleRegion(grid: Map<Utils.Point, Char>, point: Utils.Point): Set<Pair<Utils.Point, Char>> {
        val visited = mutableSetOf<Utils.Point>()
        val toVisit = LinkedList<Utils.Point>()
        toVisit.add(point)
        val plantType = grid[point] ?: throw RuntimeException("Starting point is not valid")

        val region = mutableSetOf<Pair<Utils.Point, Char>>()

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
        val grid = Utils.readGrid("inputs/day12.txt")
        return computePart2(grid)
    }

    override fun part1ResultDescription(): String {
        TODO("Not yet implemented")
    }

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }



}
