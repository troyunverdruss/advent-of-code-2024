package net.unverdruss

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
}