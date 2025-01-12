//import Utils.Direction
//import Utils.Point
//import java.io.File
//
//class Day21Busted : Day {
//    override fun part1(): Long {
//        val codes = File("inputs/Day21.txt").readLines().filter { it.isNotBlank() }
//        codes.map { code ->
//            computePart1(code)
//        }
//        TODO()
//    }
//
//    fun computePart1(code: String): Long {
//        val robotKeyPad = KeyPad()
//        val codeArray = code.map { it }
//        val robotKeyPadFullPath = robotKeyPad.fullPath(listOf(codeArray))
//        println(robotKeyPadFullPath)
//
////        val robotArrowPad1 = ArrowPad()
////        val possibleRobot1Paths = robotKeyPadFullPath.flatMap {
////            it.flatMap { robotArrowPad1.fullPath(it) }
////        }
//        val y =0
////        val robotArrowPadFullPath1 = robotArrowPad1.fullPath(robotKeyPadFullPath)
////        println(robotArrowPadFullPath1)
////
////        val robotArrowPad2 = ArrowPad()
////        val robotArrowPadFullPath2 = robotArrowPad2.fullPath(robotArrowPadFullPath1)
////
////        val myArrowPad = ArrowPad()
////        val myArrowPadFullPath = myArrowPad.fullPath(robotArrowPadFullPath2)
//
////        val lengthOfSequence = myArrowPadFullPath.size.toLong()
////        val numericValueOfSequence = code.replace("A", "").toLong()
////        return lengthOfSequence * numericValueOfSequence
//        return -1
//    }
//
//    override fun part2(): Long {
//        TODO("Not yet implemented")
//    }
//
//    override fun part1ResultDescription(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun part2ResultDescription(): String {
//        TODO("Not yet implemented")
//    }
//
//    class KeyPad : Pad(lines) {
//        private var currPos = Point(2, 3)
//
//        override fun pathToNext(next: Char): List<List<Direction>> {
//            val end = grid.filter { it.value == next }.keys.first()
//
//            if (currPos == end) {
//                return listOf(listOf())
//            }
//
////            if (memo.contains(Pair(currPos, end))) {
////                val memoVal = memo[Pair(currPos, end)]!!
////                currPos = end
////                return memoVal
////            }
////            memo[Pair(currPos, end)] = pathDetails.path
////            currPos = pathDetails.lastPos
////            return pathDetails.path
//
//            val pathDetails = findPaths(currPos, end)
//            val shortest = pathDetails.map { it.path.size }.min()
//            currPos = end
//            return pathDetails.filter { it.path.size == shortest }.map { it.path }
//        }
//
//        private val directionOrder = mapOf(
//            Direction.UP to 0,
//            Direction.RIGHT to 1,
//            Direction.DOWN to 2,
//            Direction.LEFT to 3,
//        )
//
//        override fun sortPath(path: List<Direction>): List<Direction> {
//            return path.sortedBy { directionOrder[it]!! }
//        }
//    }
//
//    companion object {
//        private val lines = """
//                789
//                456
//                123
//                #0A
//            """.trimIndent().lines()
//        private val memo = mutableMapOf<Pair<Point, Point>, List<Direction>>()
//    }
//}
//
//class ArrowPad : Pad(lines) {
//    private var currPos = Point(2, 0)
//
//    override fun pathToNext(next: Char): List<List<Direction>> {
//        val end = grid.filter { it.value == next }.keys.first()
//
//        if (currPos == end) {
//            return listOf(listOf())
//        }
//
////        if (memo.contains(Pair(currPos, end))) {
////            val memoVal = memo[Pair(currPos, end)]!!
////            currPos = end
////            return memoVal
////        }
//
//        val pathDetails = findPaths(currPos, end)
//        val shortest = pathDetails.map { it.path.size }.min()
//        currPos = end
//        return pathDetails.filter { it.path.size == shortest }.map { it.path }
////        memo[Pair(currPos, end)] = pathDetails.path
////        return pathDetails.path
//    }
//
//    private val directionOrder = mapOf(
//        Direction.RIGHT to 0,
//        Direction.UP to 1,
//        Direction.DOWN to 2,
//        Direction.LEFT to 3,
//    )
//
//    override fun sortPath(path: List<Direction>): List<Direction> {
//        return path.sortedBy { directionOrder[it]!! }
//    }
//
//    companion object {
//        private val lines = """
//                #^A
//                <v>
//            """.trimIndent().lines()
//        private val memo = mutableMapOf<Pair<Point, Point>, List<Direction>>()
//    }
//}
//
//
//abstract class Pad(lines: List<String>) {
//    val grid = Utils.parseGrid(lines).filter { it.value != '#' }
//
//    data class Step(val pos: Point) {
//        var prev: Step? = null
//    }
//
//    data class PathDetails(val path: List<Direction>, val lastPos: Point)
//
//    abstract fun pathToNext(next: Char): List<List<Direction>>
//    abstract fun sortPath(path: List<Direction>): List<Direction>
//
//    fun findPaths(start: Point, end: Point): List<PathDetails> {
//        if (start == end) {
//            return listOf(PathDetails(listOf(), start))
//        }
//
//        val toVisit = mutableListOf<Step>()
//        val visited = mutableSetOf<Step>()
//        toVisit.add(Step(start))
//        val solutions = mutableListOf<PathDetails>()
//
//        while (toVisit.isNotEmpty()) {
//            val curr = toVisit.removeFirst()
//            visited.add(curr)
//
//            if (curr.pos == end) {
//                val path = mutableListOf<Direction>()
//                var tmp: Step? = curr
//                while (tmp != null) {
//                    if (tmp.prev?.pos != null) {
//                        val dir = Direction.computeDirection(tmp.prev?.pos!!, tmp.pos)
//                        path.add(0, dir)
//                    }
//                    tmp = tmp.prev
//                }
//                solutions.add(PathDetails(path, curr.pos))
//                continue
//            }
//
//            sortPath(Utils.cardinalDirections).forEach { dir ->
//                val nextStep = Step(curr.pos + dir.point)
//                nextStep.prev = curr
//                if (grid[nextStep.pos] != null && !toVisit.contains(nextStep) && !visited.contains(nextStep)) {
//                    toVisit.add(nextStep)
//                }
//            }
//        }
//
//        val validPaths =  solutions.filter { pathIsValid(it.path) }
//        val shortestPath = validPaths.minBy { it.path.size }.path.size
//        val shortestPaths = validPaths.filter { it.path.size == shortestPath }
//
//        return shortestPaths
//    }
//
//    fun pathIsValid(path: List<Direction>): Boolean {
//        if (path.isEmpty()) {
//            return true
//        }
//
//        val first = path.first()
//        var firstChunk = true
//        var valid = true
//
//        for (dir in path) {
//            if (firstChunk && dir == first) {
//                continue
//            } else if (firstChunk && dir != first) {
//                firstChunk = false
//            } else if (!firstChunk && dir == first) {
//                valid = false
//            } else {
//                // noop
//            }
//        }
//
//        return valid
//    }
//
//    fun fullPath(code: List<List<Char>>): List<List<List<List<Char>>>> {
//        println("-----")
//        val path = mutableListOf<MutableList<MutableList<Char>>>()
//
//        return code.mapIndexed { index, macroStep ->
//            macroStep.map { c ->
////                println(index)
//                val pathsToNext = pathToNext(c)
//                 pathsToNext.map { pathToNext ->
//                    val dirs = pathToNext.map { Direction.dirToChar(it) }
//                    dirs + 'A'
//                }
//            //                for (dir in pathToNext) {
////                    println("$dir to $c")
////                    chars.add(Direction.dirToChar(dir))
////                }
////                chars.add('A')
////                println(chars)
////                println()
////                path.add(chars)
//            }
//        }
//    }
//}
//
