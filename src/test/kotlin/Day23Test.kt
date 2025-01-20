import kotlin.test.Test

class Day23Test {
    val edges = """
        kh-tc
        qp-kh
        de-cg
        ka-co
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        yn-cg
        kh-ub
        ta-co
        de-co
        tc-td
        tb-wq
        wh-td
        ta-ka
        td-qp
        aq-cg
        wq-ub
        ub-vc
        de-ta
        wq-aq
        wq-vc
        wh-yn
        ka-de
        kh-ta
        co-tc
        wh-qp
        tb-vc
        td-yn
    """.trimIndent().lines().map { it.split("-") }
        .map { Day23.Edge(it[0], it[1]) }
    @Test
     fun `part 1 example`() {
        val d = Day23()
        val networks = d.findNetworks(edges)
//        d.groupByNumberOfCommonNodes(networks, edges)
        val edgeSets = edges.map { it.computers() }
        d.computePart2(edgeSets)


        val threeNetworks = edges.flatMap { edge ->
            d.growNetworkByOne(edge.computers(), edgeSets)
        }.toSet()

        val fourNetworks = threeNetworks.flatMap { threeNetwork ->
            d.growNetworkByOne(threeNetwork, edgeSets)
        }.toSet()


        val x = 0
     }


 }