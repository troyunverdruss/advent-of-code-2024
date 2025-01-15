import java.io.File

class Day23 : Day {
    override fun part1(): Long {
        val edges = File("inputs/day23.txt")
            .readLines()
            .map { it.split("-") }
            .map { Edge(it[0], it[1]) }
        val networks = findNetworks(edges)
        return networks.count { it.hasTComp() }.toLong()
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
            return setOf(e1,e2,e3) == setOf(other.e1, other.e2, other.e3)
        }
        fun toSet(): Set<Edge> {
            return setOf(e1,e2,e3)
        }
    }

    fun findNetworks(edges: List<Edge>): List<Network> {
        return edges.flatMap { primaryEdge ->
            val secondaryEdges = edges.filter { it.includes(primaryEdge.b) && it != primaryEdge }
            secondaryEdges.mapNotNull { seconaryEdge ->
                val cSet = seconaryEdge.computers().minus(primaryEdge.b)
                if (cSet.size > 1) {
                    throw RuntimeException("Unexpected final edges found: $cSet")
                }
                val c = cSet.first()

                val tertiaryEdge = edges.firstOrNull { it.includes(primaryEdge.a) && it.includes(c) && it != seconaryEdge }
                if (tertiaryEdge != null) {
                    Network(primaryEdge, seconaryEdge, tertiaryEdge)
                } else {
                    null
                }
            }
        }.distinctBy { it.toSet() }
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription() = "Count of triads with 't'"

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }

}
