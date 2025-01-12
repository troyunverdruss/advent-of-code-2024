//import Day21.*
//import Utils.Direction
//import Utils.Point
//import java.io.File
//
//class Day21 : Day {
//    override fun part1(): Long {
//        val codes = File("inputs/Day21.txt").readLines().filter { it.isNotBlank() }
//        codes.map { code ->
//            computePart1(TargetCode.fromString(code))
//        }
//        TODO()
//    }
//
//    data class TargetCode(val chars: List<Char>) {
//
//        companion object {
//            fun fromString(string: String): TargetCode {
//                return TargetCode(string.map { it })
//            }
//        }
//    }
//
//    data class Path(val chars: List<Char>) {
//
//    }
//
//    fun computePart1(targetCode: TargetCode): Long {
//        targetCode.chars.indices.forEach { i ->
//
//            val robotKeyPad = KeyPad()
//            val from = if (i == 0) 'A' else targetCode.chars[i - 1]
//            val to = targetCode.chars[i]
//            val kpSolutionPaths = robotKeyPad.pathsBetween(from, to).map { it.toTargetCode() }
////            val kpNewTargetCodes = allPaths(kpSolutionPaths)
//
//
//            val ap1SolutionPaths = kpSolutionPaths.map { path ->
//                val steps = path.chars.indices.flatMap { i ->
//                    val from = if (i == 0) 'A' else path.chars[i - 1]
//                    val to = path.chars[i]
//                    val robotArrowPad1 = ArrowPad()
//                    val x = robotArrowPad1.pathsBetween(from, to)
//                    x
//                }
//
//                TargetCode(steps.flatMap { it.toTargetCode().chars }.toList())
////                steps
//            }
//
//            val ap2SolutionPaths = ap1SolutionPaths.map { path ->
//                val steps = path.chars.indices.map { i ->
//                    val from = if (i == 0) 'A' else path.chars[i - 1]
//                    val to = path.chars[i]
//                    val robotArrowPad1 = ArrowPad()
//                    val x = robotArrowPad1.pathsBetween(from, to)
//                    x
//                }
//
////                TargetCode(steps.flatMap { it.toTargetCode().chars }.toList())
//                steps
//            }
//
////            val userApPaths = ap2SolutionPaths.map { path ->
////                val steps = path.chars.indices.flatMap { i ->
////                    val from = if (i == 0) 'A' else path.chars[i - 1]
////                    val to = path.chars[i]
////                    val robotArrowPad1 = ArrowPad()
////                    val x = robotArrowPad1.pathsBetween(from, to)
////                    x
////                }
////
////                TargetCode(steps.flatMap { it.toTargetCode().chars }.toList())
////            }
//
////            val ap1NewTargetCodes = allPaths(ap1SolutionGroups)
//
////            val ap2SolutionGroups = ap1SolutionPaths.flatMap {
////                val robotArrowPad2 = ArrowPad()
////                robotArrowPad2.allPaths(it.toTargetCode())
////            }
////            ap2SolutionGroups.indices.reversed().forEach { allPaths(ap2SolutionGroups.slice(it..ap2SolutionGroups.lastIndex)) }
////            val ap2NewTargetCodes = allPaths(ap2SolutionGroups)
////
////            val userSolutionGroups = ap2NewTargetCodes.flatMap {
////                val userArrowPad = ArrowPad()
////                userArrowPad.allPaths(it)
////            }
////            val userTargetCodes = allPaths(userSolutionGroups)
//        val y = 0
//        }
//
//
////        val possibleRobot1Paths = robotKeyPadFullPath.flatMap {
////            it.flatMap { robotArrowPad1.fullPath(it) }
////        }
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
//    val allPathsMemo = mutableMapOf<List<Pad.SolutionPathGroup>, List<TargetCode>>()
//    fun allPaths(solutionPathGroups: List<Pad.SolutionPathGroup>): List<TargetCode> {
//
//        if (allPathsMemo.contains(solutionPathGroups)) {
//            return allPathsMemo[solutionPathGroups]!!
//        }
//
//        if (solutionPathGroups.size == 1) {
//            val result = solutionPathGroups[0].paths.map { it.toTargetCode() }
//            allPathsMemo[solutionPathGroups] = result
//            return result
//        }
//
//        val curr = solutionPathGroups.first()
//        val result = curr.paths.flatMap { p ->
//            val subPaths = allPaths(solutionPathGroups.slice(1..solutionPathGroups.lastIndex))
//            subPaths.map { subPath -> TargetCode(p.toTargetCode().chars + subPath.chars) }
//        }
//        allPathsMemo[solutionPathGroups] = result
//        return result
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
////        private var currPos = Point(2, 3)
//
//        override fun pathsBetween(from: Char, to: Char): List<SolutionPath> {
//            val start = grid.filter { it.value == from }.keys.first()
//            val end = grid.filter { it.value == to }.keys.first()
//
//            if (start == end) {
//                return listOf(SolutionPath.noopSolution(start))
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
//            val pathDetails = findPaths(start, end)
//            val shortest = pathDetails.map { it.path.size }.min()
////            currPos = end
//            return pathDetails.filter { it.path.size == shortest }
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
////    private var currPos = Point(2, 0)
//
//    override fun pathsBetween(from: Char, to: Char): List<SolutionPath> {
//        val start = grid.filter { it.value == from }.keys.first()
//        val end = grid.filter { it.value == to }.keys.first()
//
//        if (start == end) {
//            return listOf(SolutionPath.noopSolution(start))
//        }
//
////        if (memo.contains(Pair(currPos, end))) {
////            val memoVal = memo[Pair(currPos, end)]!!
////            currPos = end
////            return memoVal
////        }
//
//        val pathDetails = findPaths(start, end)
//        val shortest = pathDetails.map { it.path.size }.min()
////        currPos = end
//        return pathDetails.filter { it.path.size == shortest }
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
//
//        return path.sortedBy { directionOrder[it]!! }
//    }
//
//    companion object {
//        private val lines = """
//                #^A
//                <v>
//            """.trimIndent().lines()
//
//        private val memo = mutableMapOf<Pair<Point, Point>, List<Direction>>()
//    }
//
//}
//
//
//abstract class Pad(lines: List<String>) {
//    val grid = Utils.parseGrid(lines).filter { it.value != '#' }
//
//    data class Step(val pos: Point) {
//        var visited = setOf<Point>()
//        var usedDirs = setOf<Direction>()
//        var prev: Step? = null
//        var dir: Direction? = null
//    }
//
//    data class SolutionPath(val path: List<Direction>, val points: List<Point>) {
//        var lastPos: Point? = if (points.isEmpty()) null else points.last()
//
//        fun toTargetCode(): TargetCode {
//            val chars = path.map { dir ->
//                Direction.dirToChar(dir)
//            }
//            return TargetCode(chars + 'A')
//        }
//
//        companion object {
//            fun noopSolution(pos: Point): SolutionPath {
//                val solutionPath = SolutionPath(listOf(), listOf())
//                solutionPath.lastPos = pos
//                return solutionPath
//            }
//        }
//    }
//
//    abstract fun pathsBetween(from: Char, to: Char): List<SolutionPath>
//    abstract fun sortPath(path: List<Direction>): List<Direction>
//
//    fun findPaths(start: Point, end: Point): List<SolutionPath> {
//        if (start == end) {
//            val solution = SolutionPath(listOf(), listOf())
//            solution.lastPos = start
//            return listOf(solution)
//        }
//
//        val toVisit = mutableListOf<Step>()
//        val startStep = Step(start)
//        startStep.visited = setOf(start)
//
//        toVisit.add(startStep)
//        val solutions = mutableListOf<SolutionPath>()
//
//        while (toVisit.isNotEmpty()) {
//            val curr = toVisit.removeFirst()
////            visited.add(curr)
//            val visited = curr.visited
//            val usedDirs = curr.usedDirs
//
//            if (curr.pos == end) {
//                val path = mutableListOf<Direction>()
//                val pointPath = mutableListOf<Point>()
//                var tmp: Step? = curr
//                while (tmp != null) {
//                    if (tmp.prev?.pos != null) {
//                        val dir = Direction.computeDirection(tmp.prev?.pos!!, tmp.pos)
//                        path.add(0, dir)
//                        pointPath.add(0, tmp.pos)
//                    }
//                    tmp = tmp.prev
//                }
//                solutions.add(SolutionPath(path, pointPath))
//                continue
//            }
//
//            Utils.cardinalDirections.forEach { dir ->
//                val nextStep = Step(curr.pos + dir.point)
//                nextStep.prev = curr
//                nextStep.visited = curr.visited + curr.pos
//                nextStep.usedDirs = curr.usedDirs + dir
//                nextStep.dir = dir
//                if (grid[nextStep.pos] != null && !visited.contains(nextStep.pos) && (!usedDirs.contains(dir) || dir == curr.dir)) {
//                    toVisit.add(nextStep)
//                }
//            }
//        }
//
//        val shortestPath = solutions.minBy { it.path.size }.path.size
//        val shortestPaths = solutions.filter { it.path.size == shortestPath }
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
//    data class SolutionPathGroup(val paths: List<SolutionPath>) {
////        fun allPaths(): List<SolutionPath> {
////
////        }
//    }
//
//    fun allPaths(code: TargetCode): List<SolutionPathGroup> {
//        println("-----")
//        val path = mutableListOf<MutableList<MutableList<Char>>>()
//
//        val solutionGroups = code.chars.map { char ->
////            SolutionPathGroup(pathsBetween(hi, char))
//        }
//
//        TODO()
////        return solutionGroups
//
//
////
////        return code.mapIndexed { index, macroStep ->
////            macroStep.map { c ->
//////                println(index)
////                val pathsToNext = pathToNext(c)
////                pathsToNext.map { pathToNext ->
////                    val dirs = pathToNext.map { Direction.dirToChar(it) }
////                    dirs + 'A'
////                }
////                //                for (dir in pathToNext) {
//////                    println("$dir to $c")
//////                    chars.add(Direction.dirToChar(dir))
//////                }
//////                chars.add('A')
//////                println(chars)
//////                println()
//////                path.add(chars)
////            }
////        }
//    }
//}
//
