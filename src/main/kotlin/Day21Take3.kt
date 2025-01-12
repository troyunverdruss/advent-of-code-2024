//import Utils.Direction
//import Utils.Point
//import java.io.File
//
//class Day21 : Day {
//    override fun part1(): Long {
//        val codes = File("inputs/Day21.txt").readLines().filter { it.isNotBlank() }
//        codes.map { code ->
//            computePart1(code)
//        }
//        TODO()
//    }
//
//    val keypad = """
//                789
//                456
//                123
//                #0A
//            """.trimIndent().lines()
//    val arrowpad = """
//                #^A
//                <v>
//            """.trimIndent().lines()
//
//    val kpLookup = buildKeypadLookup()
//    val apLookup = buildArrowPadLookup()
////    val lookup = kpLookup + apLookup
//
//    fun computePart1(code: String): Long {
//        //solve(inputList, maxDepth):
//        //    create the numpad graph
//        //    create the dirpad graph
//        //    foreach keys in the inputList:
////        for (key in code) {
////
////            //        build the sequence list for the numpad keys
////            val sequenceList = buildSequences("$key", 0, 'A', "")
////            for (sequence in sequenceList) {
////
////                //        for each sequence in the list:
////
////                //            find the minimum of lowestSeq(sequence, maxDepth, cache)
////                //        add to the total multiplied by num part of keys
////                //    return total
////            }
////        }
//
//        val result = mutableListOf<String>()
//        buildSequencesKP(code, 0, 'A', "", result)
//
//        val allPossible = result.map { sequence ->
//            shortestSequence(sequence, 1, mutableMapOf())
//        }
//
//        val allPossible2 = allPossible.map { sequence ->
//            shortestSequence(sequence, 1, mutableMapOf())
//        }
//
//
//        val x = shortestSequence("^A", 1, mutableMapOf())
//        TODO()
//
//    }
//
//    // buildSeq(keys, index, prevKey, currPath, result):
//    fun buildSequences(keys: String, index: Int, prevKey: Char, currPath: String, result: MutableList<String>) {
//
//        //    if index is the length of keys:
//        //        add the current path to the result
//        //        return
//        if (index == keys.length) {
//            result.add(currPath)
//            return
//        }
//
//        //    foreach path in the keypad graph from prevKey to the currKey:
//        //        buildSeq(keys, index+1, keys[index], currPath + path + 'A', result)
//
//        apLookup[Pair(prevKey, keys[index])]!!.forEach { path ->
//            buildSequences(keys, index + 1, keys[index], currPath + path + 'A', result)
//        }
//    }
//    fun buildSequencesKP(keys: String, index: Int, prevKey: Char, currPath: String, result: MutableList<String>) {
//
//        //    if index is the length of keys:
//        //        add the current path to the result
//        //        return
//        if (index == keys.length) {
//            result.add(currPath)
//            return
//        }
//
//        //    foreach path in the keypad graph from prevKey to the currKey:
//        //        buildSeq(keys, index+1, keys[index], currPath + path + 'A', result)
//
//        kpLookup[Pair(prevKey, keys[index])]!!.forEach { path ->
//            buildSequencesKP(keys, index + 1, keys[index], currPath + path + 'A', result)
//        }
//    }
//
//    fun shortestSequence(keys: String, depth: Int, cache: MutableMap<Pair<String, Int>, String>): String {
//        if (depth == 0) {
//            return keys
//        }
//
//        if (cache.containsKey(Pair(keys, depth))) {
//            return cache[Pair(keys, depth)]!!
//        }
//        var fullString = ""
//
//        keys.split('A', limit = keys.count { it == 'A' }).forEach { subKey ->
//
//            val result = mutableListOf<String>()
//            buildSequences(subKey, 0, 'A', "", result)
//            val minLength = result.map { path -> shortestSequence(path, depth - 1, cache) }
//            fullString += minLength.joinToString("")
//        }
//        cache[Pair(keys, depth)] = fullString
//        return fullString
//    }
//
//    //solve(inputList, maxDepth):
//    //    create the numpad graph
//    //    create the dirpad graph
//    //    foreach keys in the inputList:
//    //        build the sequence list for the numpad keys
//    //        for each sequence in the list:
//    //            find the minimum of lowestSeq(sequence, maxDepth, cache)
//    //        add to the total multiplied by num part of keys
//    //    return total
//
//    //shortestSeq(keys, depth, cache):
//    //    if depth is 0:
//    //        return the length of keys
//    //    if keys, depth in the cache:
//    //        return that value in cache
//    //    split the keys into subKeys at 'A'
//    //    foreach subKey in the subKeys list:
//    //        build the sequence list for the subKey (buildSeq)
//    //        for each sequence in the list:
//    //            find the minimum of shortestSeq(sequence, depth-1, cache)
//    //        add the minimum to the total
//    //    set the total at keys, depth in the cache
//    //    return total
//
//    // buildSeq(keys, index, prevKey, currPath, result):
//    //    if index is the length of keys:
//    //        add the current path to the result
//    //        return
//    //    foreach path in the keypad graph from prevKey to the currKey:
//    //        buildSeq(keys, index+1, keys[index], currPath + path + 'A', result)
//
//    fun buildKeypadLookup(): Map<Pair<Char, Char>, List<String>> {
//        val digits = listOf('A', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0')
//        val combos = getCombos(digits)
//        return combos.map { pair ->
//            val kp = Pad(keypad)
//            val paths = kp.findPaths(pair.first, pair.second)
//            pair to paths
//        }.toMap()
//    }
//
//    fun buildArrowPadLookup(): Map<Pair<Char, Char>, List<String>> {
//        val digits = listOf('A', '^', 'v', '<', '>')
//        val combos = getCombos(digits)
//        return combos.map { pair ->
//            val ap = Pad(arrowpad)
//            val paths = ap.findPaths(pair.first, pair.second)
//            pair to paths
//        }.toMap()
//    }
//
//    private fun getCombos(digits: List<Char>) = digits.flatMap { a ->
//        digits.flatMap { b ->
//            listOf(Pair(a, b), Pair(b, a))
//        }
//    }
//
//
////    fun apPaths(path: String): List<String> {
////        val paths = path.indices.map { idx ->
////            val ap = Pad(arrowpad)
////            ap.findPaths(path.getOrNull(idx - 1) ?: 'A', path[idx])
////        }
////        return allPaths(paths)
////    }
//
////    fun allPaths(paths: List<List<String>>): List<String> {
////
////        if (paths.size == 1) {
////            paths[0]
////        }
////
////        val curr = paths.first()
////        val subPaths = allPaths(paths.slice(1..paths.lastIndex))
////        return subPaths.map { curr + it }
////    }
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
//
//    class Pad(lines: List<String>) {
//        val grid = Utils.parseGrid(lines).filter { it.value != '#' }
//
//        data class Step(val pos: Point) {
//            var visited = setOf<Point>()
//            var usedDirs = setOf<Direction>()
//            var prev: Step? = null
//            var dir: Direction? = null
//        }
//
//        fun findPaths(startChar: Char = 'A', endChar: Char): List<String> {
//            val start = grid.filter { it.value == startChar }.keys.first()
//            val end = grid.filter { it.value == endChar }.keys.first()
//
//            if (start == end) {
//                val solution = ""
//                return listOf(solution)
//            }
//
//            val toVisit = mutableListOf<Step>()
//            val startStep = Step(start)
//            startStep.visited = setOf(start)
//
//            toVisit.add(startStep)
//            val solutions = mutableListOf<String>()
//
//            while (toVisit.isNotEmpty()) {
//                val curr = toVisit.removeFirst()
//                val visited = curr.visited
//                val usedDirs = curr.usedDirs
//
//                if (curr.pos == end) {
//                    val path = mutableListOf<Direction>()
//                    val pointPath = mutableListOf<Point>()
//                    var tmp: Step? = curr
//                    while (tmp != null) {
//                        if (tmp.prev?.pos != null) {
//                            val dir = Direction.computeDirection(tmp.prev?.pos!!, tmp.pos)
//                            path.add(0, dir)
//                            pointPath.add(0, tmp.pos)
//                        }
//                        tmp = tmp.prev
//                    }
//
//                    solutions.add(path.map { dir ->
//                        Direction.dirToChar(dir)
//                    }.joinToString(""))
//                    continue
//                }
//
//                Utils.cardinalDirections.forEach { dir ->
//                    val nextStep = Step(curr.pos + dir.point)
//                    nextStep.prev = curr
//                    nextStep.visited = curr.visited + curr.pos
//                    nextStep.usedDirs = curr.usedDirs + dir
//                    nextStep.dir = dir
//                    if (grid[nextStep.pos] != null && !visited.contains(nextStep.pos) && (!usedDirs.contains(dir) || dir == curr.dir)) {
//                        toVisit.add(nextStep)
//                    }
//                }
//            }
//
//            val shortestPath = solutions.minBy { it.length }.length
//            val shortestPaths = solutions.filter { it.length == shortestPath }
//
//            return shortestPaths
//        }
//    }
//}
//
