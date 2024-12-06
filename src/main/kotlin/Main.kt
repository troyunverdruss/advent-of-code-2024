package net.unverdruss

import Day07

fun main() {
    println("Day 1")
    val d1 = Day01()
    val d1r1 = d1.part1()
    println("Part 1, total distance between lists: $d1r1")
    val d1r2 = d1.part2()
    println("Part 2, similarity score: $d1r2")

    println("Day 2")
    val d2 = Day02()
    val d2r1 = d2.part1()
    println("Part 1, total safe reports: $d2r1")
    val d2r2 = d2.part2()
    println("Part 2, total safe reports: $d2r2")

    println("Day 3")
    val d3 = Day03()
    val d3r1 = d3.part1()
    println("Part 1, sum of all valid mul(): $d3r1")
    val d3r2 = d3.part2()
    println("Part 2, sum of all enabled and valid mul(): $d3r2")

    println("Day 4")
    val d4 = Day04()
    val d4r1 = d4.part1()
    println("Part 1, total XMASes: $d4r1")
    val d4r2 = d4.part2()
    println("Part 2, total X-MASes: $d4r2")

    println("Day 5")
    val d5 = Day05()
    val d5r1 = d5.part1()
    println("Part 1, sum of valid updates: $d5r1")
    val d5r2 = d5.part2()
    println("Part 2, sum of newly valid updates: $d5r2")

    println("Day 6")
    val d6 = Day06()
    val d6r1 = d6.part1()
    println("Part 1, all visited squares: $d6r1")
    val d6r2 = d6.part2()
    println("Part 2, total number of possible obstruction locations: $d6r2")

    println("Day 7")
    val d7 = Day07()
    val d7r1 = d7.part1()
    println("Part 1, total calibration result (+ *): $d7r1")
    val d7r2 = d7.part2()
    println("Part 2, total calibration result (+ * ||): $d7r2")
}