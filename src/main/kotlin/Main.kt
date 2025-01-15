package net.unverdruss

import Day01
import Day04
import Day07
import Day08
import Day09
import Day10
import Day11
import Day12
import Day13
import Day14
import Day15
import Day16
import Day17
import Day18
import Day19
import Day20
import Day21
import Day22

fun main() {
//    println("Day 1")
//    val d1 = Day01()
//    val d1r1 = d1.part1()
//    println("Part 1, total distance between lists: $d1r1")
//    val d1r2 = d1.part2()
//    println("Part 2, similarity score: $d1r2")
//
//    println("Day 2")
//    val d2 = Day02()
//    val d2r1 = d2.part1()
//    println("Part 1, total safe reports: $d2r1")
//    val d2r2 = d2.part2()
//    println("Part 2, total safe reports: $d2r2")
//
//    println("Day 3")
//    val d3 = Day03()
//    val d3r1 = d3.part1()
//    println("Part 1, sum of all valid mul(): $d3r1")
//    val d3r2 = d3.part2()
//    println("Part 2, sum of all enabled and valid mul(): $d3r2")
//
//    println("Day 4")
//    val d4 = Day04()
//    val d4r1 = d4.part1()
//    println("Part 1, total XMASes: $d4r1")
//    val d4r2 = d4.part2()
//    println("Part 2, total X-MASes: $d4r2")
//
//    println("Day 5")
//    val d5 = Day05()
//    val d5r1 = d5.part1()
//    println("Part 1, sum of valid updates: $d5r1")
//    val d5r2 = d5.part2()
//    println("Part 2, sum of newly valid updates: $d5r2")
//
//    println("Day 6")
//    val d6 = Day06()
//    val d6r1 = d6.part1()
//    println("Part 1, all visited squares: $d6r1")
//    val d6r2 = d6.part2()
//    println("Part 2, total number of possible obstruction locations: $d6r2")
//
//    println("Day 7")
//    val d7 = Day07()
//    val d7r1 = d7.part1()
//    println("Part 1, total calibration result (+ *): $d7r1")
//    val d7r2 = d7.part2()
//    println("Part 2, total calibration result (+ * ||): $d7r2")
//
//    println("Day 8")
//    val d8 = Day08()
//    val d8r1 = d8.part1()
//    println("Part 1, distinct antinode locations: $d8r1")
//    val d8r2 = d8.part2()
//    println("Part 2, distinct antinode locations (with resonant harmonics): $d8r2")
//
//    println("Day 9")
//    val d9 = Day09()
//    val d9r1 = d9.part1()
//    println("Part 1, checksum of defragged drive (fragging files): $d9r1")
//    val d9r2 = d9.part2()
//    println("Part 2, checksum of defragged drive (keeping files intact): $d9r2")
//
//    println("Day 10")
//    val d10 = Day10()
//    val d10r1 = d10.part1()
//    println("Part 1, sum of trailhead scores: $d10r1")
//    val d10r2 = d10.part2()
//    println("Part 2, sum of trailhead ratings: $d10r2")
//
//    println("Day 11")
//    val d11 = Day11()
//    val d11r1 = d11.part1()
//    println("Part 1, stones after 25 blinks: $d11r1")
//    val d11r2 = d11.part2()
//    println("Part 2, stones after 75 blinks: $d11r2")
//
//
//    println("Day 12")
//    val d12 = Day12()
//    val d12r1 = d12.part1()
//    println("Part 1, cost of fence: $d12r1")
//    val d12r2 = d12.part2()
//    println("Part 2, cost of fence: $d12r2")
//
//    println("Day 13")
//    val d13 = Day13()
//    val d13r1 = d13.part1()
//    println("Part 1, ${d13.part1ResultDescription()}: $d13r1")
//    val d13r2 = d13.part2()
//    println("Part 2, ${d13.part2ResultDescription()}: $d13r2")
//
//    println("Day 14")
//    val d14 = Day14()
//    val d14r1 = d14.part1()
//    println("Part 1, ${d14.part1ResultDescription()}: $d14r1")
//    val d14r2 = d14.part2()
//    println("Part 2, ${d14.part2ResultDescription()}: $d14r2")
//
//    println("Day 15")
//    val d15 = Day15()
//    val d15r1 = d15.part1()
//    println("Part 1, ${d15.part1ResultDescription()}: $d15r1")
//    val d15r2 = d15.part2()
//    println("Part 2, ${d15.part2ResultDescription()}: $d15r2")


//    println("Day 16")
//    val d16 = Day16()
//    val d16r1 = d16.part1()
//    println("Part 1, ${d16.part1ResultDescription()}: $d16r1")
//    val d16r2 = d16.part2()
//    println("Part 2, ${d16.part2ResultDescription()}: $d16r2")
//
//    println("Day 17")
//    val d17 = Day17()
//    val d17r1 = d17.part1()
//    println("Part 1, ${d17.part1ResultDescription()}: $d17r1")
//    val d17r2 = d17.part2()
//    println("Part 2, ${d17.part2ResultDescription()}: $d17r2")

//    println("Day 18")
//    val d18 = Day18()
//    val d18r1 = d18.part1()
//    println("Part 1, ${d18.part1ResultDescription()}: $d18r1")
//    val d18r2 = d18.part2()
//    println("Part 2, ${d18.part2ResultDescription()}: $d18r2")

//    println("Day 19")
//    val d19 = Day19()
//    val d19r1 = d19.part1()
//    println("Part 1, ${d19.part1ResultDescription()}: $d19r1")
//    val d19r2 = d19.part2()
//    println("Part 2, ${d19.part2ResultDescription()}: $d19r2")

//    println("Day 20")
//    val d20 = Day20()
//    val d20r1 = d20.part1()
//    println("Part 1, ${d20.part1ResultDescription()}: $d20r1")
//    val d20r2 = d20.part2()
//    println("Part 2, ${d20.part2ResultDescription()}: $d20r2")

//    println("Day 21")
//    val d21 = Day21()
//    val d21r1 = d21.part1()
//    println("Part 1, ${d21.part1ResultDescription()}: $d21r1")
//    val d21r2 = d21.part2()
//    println("Part 2, ${d21.part2ResultDescription()}: $d21r2")

    println("Day 22")
    val d22 = Day22()
    val d22r1 = d22.part1()
    println("Part 1, ${d22.part1ResultDescription()}: $d22r1")
    val d22r2 = d22.part2()
    println("Part 2, ${d22.part2ResultDescription()}: $d22r2")
}