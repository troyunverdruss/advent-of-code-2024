import java.io.File

class Day23 : Day {
    override fun part1(): Long {
        val edges = File("inputs/day23.txt")
            .readLines()
            .map { it.split("-") }
            .map { Edge(it[0], it[1]) }
        val networks = findNetworks(edges)
//        findComputerConnections(edges)
        groupByNumberOfCommonNodes(networks, edges)
        return networks.count { it.hasTComp() }.toLong()
    }

    fun groupByNumberOfCommonNodes(networks: List<Network>, edges: List<Edge>) {
        val allComputers = edges.flatMap { it.computers() }.toSet().toSet()
        val computerToConnected = allComputers.associateWith { c ->
            edges.filter { it.computers().contains(c) }.flatMap { it.computers() }.toSet() - c
        }

        val networkToCommonOtherComputers = networks.map { network ->
            val computers = (network.e1.computers() + network.e2.computers() + network.e3.computers()).toList()
            if (computers.size != 3) {
                throw RuntimeException("Expected 3 computers")
            }
            val c1 = computerToConnected[computers[0]] ?: emptySet()
            val c2 = computerToConnected[computers[1]] ?: emptySet()
            val c3 = computerToConnected[computers[2]] ?: emptySet()

            network to c1.union(c2).union(c3)
        }
        val x = 9
    }

    data class Edge(val a: String, val b: String) {
        fun includes(computer: String): Boolean {
            return a == computer || b == computer
        }

        fun computers(): Set<String> {
            return setOf(a, b)
        }

        fun equivalentTo(other: Edge): Boolean {
            return setOf(a, b) == setOf(other.a, other.b)
        }
    }

    data class Network(val e1: Edge, val e2: Edge, val e3: Edge) {
        private val computers: Set<String> = listOf(e1, e2, e3).flatMap { it.computers() }.toSet()
        fun hasTComp(): Boolean {
            return computers.any { it.startsWith("t") }
        }

        fun equivalentTo(other: Network): Boolean {
            return setOf(e1, e2, e3) == setOf(other.e1, other.e2, other.e3)
        }

        fun toSet(): Set<Edge> {
            return setOf(e1, e2, e3)
        }
    }

    data class VariableSizeNetwork(val computers: Set<String>)

    fun findComputerConnections(edges: List<Edge>) {
        val allComputers = edges.flatMap { it.computers() }.toSet().toSet()
        val computerConnections = allComputers.associateWith { c -> edges.count { it.includes(c) } }
        val x = 9
    }

    fun traceEdges(edges: List<Edge>) {
        val start = edges.first().a
        val toVisit = mutableListOf(start)
        val visited = mutableSetOf<String>()
        while (toVisit.isNotEmpty()) {
            val current = toVisit.removeAt(0)
            visited.add(current)
            val connectedEdges = edges.filter { it.includes(current) }
            connectedEdges.forEach { edge ->
                if (visited.contains(edge.a) && visited.contains(edge.b)) {
                    // Don't include it
                } else {
                    if (!toVisit.contains(edge.a)) {
                        toVisit.add(edge.a)
                    }
                    if (!toVisit.contains(edge.b)) {
                        toVisit.add(edge.b)
                    }
                }
            }
        }

        val allComputers = edges.flatMap { it.computers() }.toSet().toSet()
        val x = 9
    }

    val growNetworkMemo = mutableMapOf<Set<String>, Set<Set<String>>>()
    fun growNetworkByOne(network: Set<String>, edges: List<Set<String>>): Set<Set<String>> {
        val memoVal = growNetworkMemo[network]
        if (memoVal != null) {
            return memoVal
        }

        val allOtherComputers = edges.flatten().toSet().minus(network)

        val result = allOtherComputers.mapIndexedNotNull { index, extraComputer ->
            val combos = (network + extraComputer).combos()
            if (edges.containsAll(combos)) {
                network + extraComputer
            } else {
                null
            }
        }.toSet()
        growNetworkMemo[network] = result
        return result
    }

    val comboMemo = mutableMapOf<Set<String>, Set<Set<String>>>()
    fun Set<String>.combos(): Set<Set<String>> {
        val memoVal = comboMemo[this]
        if (memoVal != null) {
            return memoVal
        }
        val tmp = this.toList()
        val result = tmp.flatMapIndexed { index, s1 ->
            tmp.slice((index + 1)..tmp.lastIndex).map { s2 ->
                setOf(s1, s2)
            }
        }.toSet()
        comboMemo[this] = result
        return result
    }

    fun findNetworks(edges: List<Edge>): List<Network> {
        return edges.flatMap { primaryEdge ->
            val secondaryEdges = edges.filter { it.includes(primaryEdge.b) && it != primaryEdge }
            secondaryEdges.mapNotNull { secondaryEdge ->
                val cSet = secondaryEdge.computers().minus(primaryEdge.b)
                if (cSet.size > 1) {
                    throw RuntimeException("Unexpected final edges found: $cSet")
                }
                val c = cSet.first()

                val tertiaryEdge =
                    edges.firstOrNull { it.includes(primaryEdge.a) && it.includes(c) && it != secondaryEdge }
                if (tertiaryEdge != null) {
                    Network(primaryEdge, secondaryEdge, tertiaryEdge)
                } else {
                    null
                }
            }
        }.distinctBy { it.toSet() }
    }

    override fun part2(): Long {
        val edgeSets = File("inputs/day23.txt")
            .readLines()
            .map { it.split("-").toSet() }

        computePart2(edgeSets)
        return -1
    }

    fun computePart2(edgeSets: List<Set<String>>) {
        val graph = edgeSets.flatten().map { it to Node(it) }.toMap()
        edgeSets.forEach { edge ->
            if (edge.size != 2) throw RuntimeException("edge set contains too many nodes")
            val list = edge.toList()
            val a = list[0]
            val b = list[1]
            graph[a]?.neighbors?.add(b)
            graph[b]?.neighbors?.add(a)
        }

        var networks = graph.keys.mapNotNull { growByOne(setOf(it), graph) }.flatten().toSet()
        while (networks.size > 1) {
            networks = networks.mapNotNull { growByOne(it, graph) }.flatten().toSet()
        }
        println(networks.first().toList().sorted().joinToString(","))
    }

    fun growByOne(clique: Set<String>, graph: Map<String, Node>): Set<Set<String>>? {
        val sharedOptions = mutableSetOf<String>()
        clique.forEachIndexed { index, nodeId ->
            if (index == 0) {
                sharedOptions.addAll(graph[nodeId]?.neighbors ?: emptyList())
            } else {
                val newOptions = sharedOptions.intersect((graph[nodeId]?.neighbors ?: emptySet()).toSet())
                sharedOptions.clear()
                sharedOptions.addAll(newOptions)
            }
        }

        return if (sharedOptions.isEmpty()) {
            null
        } else {
            sharedOptions.map { shared -> clique + shared }.toSet()
        }
    }

    data class Node(val id: String) {
        val neighbors = mutableSetOf<String>()
    }


    override fun part1ResultDescription() = "Count of triads with 't'"

    override fun part2ResultDescription() = "See above for alphabetized LAN party"

}
