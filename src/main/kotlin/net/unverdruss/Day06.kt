package net.unverdruss.net.unverdruss

import net.unverdruss.Day04

class Day06 {
    fun part1(): Long {
        val grid = Day04.readGrid("inputs/day06.txt")
        runSimulation(grid)


        TODO()
    }

     fun runSimulation(grid: Map<Day04.Point, Char>) {
         val start = grid.entries.filter { it.value == '^' }[0].key
         var pos = start.copy()
         while (grid.keys.contains(start)) {

         }

    }

    fun part2(): Long {
        TODO()
    }


}
