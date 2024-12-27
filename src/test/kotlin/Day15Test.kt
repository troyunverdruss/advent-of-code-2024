import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    @Test
    fun `part 1, larger example`() {
        val input = """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########

            <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
            vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
            ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
            <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
            ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
            ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
            >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
            <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
            ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
            v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
        """.trimIndent()
        val d = Day15()
        val day15Input = d.parseRawInput(input)

        assertEquals(10092, d.computePart1(day15Input))
    }

    @Test
    fun `part 1, smaller example`() {
        val input = """
            ########
            #..O.O.#
            ##@.O..#
            #...O..#
            #.#.O..#
            #...O..#
            #......#
            ########

            <^^>>>vv<v>>v<<
        """.trimIndent()
        val d = Day15()
        val day15Input = d.parseRawInput(input)
        assertEquals(2028, d.computePart1(day15Input))
    }

    @Test
    fun `part 2, smaller example`() {
        val input = """
            ##########
            #..O..O.O#
            #......O.#
            #.OO..O.O#
            #..O@..O.#
            #O#..O...#
            #O..O..O.#
            #.OO.O.OO#
            #....O...#
            ##########

            <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
            vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
            ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
            <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
            ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
            ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
            >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
            <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
            ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
            v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
        """.trimIndent()
        val d = Day15()
        val day15Input = d.parseRawInput(input, scaleUp=true)
        Utils.debugPrint(day15Input.grid)
        assertEquals(9021, d.computePart2(day15Input))

    }

    @Test
    fun `part 2 small example`() {
        val input = """
            #######
            #...#.#
            #.....#
            #..OO@#
            #..O..#
            #.....#
            #######

            <vv<<^^<<^^
        """.trimIndent()
        val d = Day15()
        val day15Input = d.parseRawInput(input, scaleUp=true)
        Utils.debugPrint(day15Input.grid)
        assertEquals(9021, d.computePart2(day15Input))
    }

    @Test
    fun `test parsing and printing grid`() {
        val lines = """
            12
            34
        """.trimIndent().lines()
        val grid = Utils.parseGrid(lines)
        Utils.debugPrint(grid)
    }

    @Test
    fun `test moving scaled boxes up`() {
        val input = """
            ####
            #..#
            #.O#
            #.@#
            ####
            
            ^
        """.trimIndent()
        val d = Day15()
        val day15Input = d.parseRawInput(input, scaleUp=true)
        Utils.debugPrint(day15Input.grid)
        assertEquals(9021, d.computePart2(day15Input))
    }

    @Test
    fun `part 2 failing downward case`() {
        val input = """
            ####################
            ##[]..[]....[]..[]##
            ##[].......@..[]..##
            ##........[][][][]##
            ##....[]..[]..[]..##
            ##..##....[]......##
            ##...[].......[]..##
            ##.....[]..[].[][]##
            ##........[]......##
            ####################
            
            v
        """.trimIndent()
        val d = Day15()
        val day15Input = d.parseRawInput(input, scaleUp=false)
        Utils.debugPrint(day15Input.grid)
        assertEquals(9021, d.computePart2(day15Input))
    }
}