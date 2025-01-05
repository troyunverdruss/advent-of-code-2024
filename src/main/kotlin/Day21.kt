import Utils.Direction
import Utils.Point
import java.io.File

class Day21 : Day {
    override fun part1(): Long {
        val codes = File("inputs/Day21.txt").readLines().filter { it.isNotBlank() }
        codes.map { code ->
            computePart1(code)
        }
        TODO()
    }

    fun computePart1(code: String): Long {
        val robotKeyPad = KeyPad()
        val codeArray = code.map { it.toChar() }
        val robotKeyPadFullPath = robotKeyPad.fullPath(listOf(codeArray))
        println(robotKeyPadFullPath)

        val robotArrowPad1 = ArrowPad()
        val robotArrowPadFullPath1 = robotArrowPad1.fullPath(robotKeyPadFullPath)
        println(robotArrowPadFullPath1)

        val robotArrowPad2 = ArrowPad()
        val robotArrowPadFullPath2 = robotArrowPad2.fullPath(robotArrowPadFullPath1)

        val myArrowPad = ArrowPad()
        val myArrowPadFullPath = myArrowPad.fullPath(robotArrowPadFullPath2)

        val lengthOfSequence = myArrowPadFullPath.size.toLong()
        TODO("need to get numeric val from code and mul()")
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription(): String {
        TODO("Not yet implemented")
    }

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

    class KeyPad : Pad(lines) {
        private var currPos = Point(2, 3)

        override fun pathToNext(next: Char): List<Direction> {
            val end = grid.filter { it.value == next }.keys.first()

            if (currPos == end) {
                return listOf()
            }

            if (memo.contains(Pair(currPos, end))) {
                return memo[Pair(currPos, end)]!!
            }
            val pathDetails = findPath(currPos, end)
            memo[Pair(currPos, end)] = pathDetails.path
            currPos = pathDetails.lastPos
            return pathDetails.path
        }
    }

    companion object {
        private val lines = """
                789
                456
                123
                #0A
            """.trimIndent().lines()
        private val memo = mutableMapOf<Pair<Point, Point>, List<Direction>>()
    }
}

class ArrowPad : Pad(lines) {
    private var currPos = Point(2, 0)

    override fun pathToNext(next: Char): List<Direction> {
        val end = grid.filter { it.value == next }.keys.first()

        if (currPos == end) {
            return listOf()
        }

        if (memo.contains(Pair(currPos, end))) {
            return memo[Pair(currPos, end)]!!
        }
        val pathDetails = findPath(currPos, end)
        memo[Pair(currPos, end)] = pathDetails.path
        currPos = pathDetails.lastPos
        return pathDetails.path
    }

    companion object {
        private val lines = """
                #^A
                <v>
            """.trimIndent().lines()
        private val memo = mutableMapOf<Pair<Point, Point>, List<Direction>>()
    }
}


abstract class Pad(lines: List<String>) {
    val grid = Utils.parseGrid(lines).filter { it.value != '#' }

    data class Step(val pos: Point) {
        var prev: Step? = null
    }

    data class PathDetails(val path: List<Direction>, val lastPos: Point)

    abstract fun pathToNext(next: Char): List<Direction>

    fun findPath(start: Point, end: Point): PathDetails {
        if (start == end) {
            return PathDetails(listOf(), start)
        }

        val toVisit = mutableListOf<Step>()
        val visited = mutableSetOf<Step>()
        toVisit.add(Step(start))
        while (toVisit.isNotEmpty()) {
            val curr = toVisit.removeFirst()
            if (curr.pos == end) {
                val path = mutableListOf<Direction>()
                var tmp: Step? = curr
                while (tmp != null) {
                    if (tmp.prev?.pos != null) {
                        val dir = Direction.computeDirection(tmp.prev?.pos!!, tmp.pos)
                        path.add(0, dir)
                    }
                    tmp = tmp.prev
                }
                return PathDetails(path.sorted(), curr.pos)
            }

            Utils.cardinalDirections.forEach { dir ->
                val nextStep = Step(curr.pos + dir.point)
                nextStep.prev = curr
                if (grid[nextStep.pos] != null && !toVisit.contains(nextStep) && !visited.contains(nextStep)) {
                    toVisit.add(nextStep)
                }
            }
        }
        throw RuntimeException("no path found")
    }

    fun fullPath(code: List<List<Char>>): List<List<Char>> {
        println("-----")
        val path = mutableListOf<List<Char>>()
        code.forEach { macroStep ->
            macroStep.forEach { c ->
                val chars = mutableListOf<Char>()
                val pathToNext = pathToNext(c)
                for (dir in pathToNext) {
                    println("$dir to $c")
                    chars.add(Direction.dirToChar(dir))
                }
                chars.add('A')
                path.add(chars)
            }
        }
//        return code.map { c ->
//            val chars = mutableListOf<Char>()
//            val pathToNext = pathToNext(c)
//            for (dir in pathToNext) {
//                println("$dir to $c")
//                chars.add(Direction.dirToChar(dir))
//            }
//            chars.add('A')
//            chars
//        }
        return path
    }
}

