import java.io.File
import kotlin.math.pow

class Day17 : Day {
    override fun part1(): Long {
        val c = Computer()
        c.initialize(File("inputs/day17.txt").readLines())
        c.run()
        println(c.output.joinToString(","))
        return -1
    }

    override fun part2(): Long {
        TODO("Not yet implemented")
    }

    override fun part1ResultDescription() = "program output"

    override fun part2ResultDescription(): String {
        TODO("Not yet implemented")
    }


}

class Computer {
    enum class OpCodes(val code: Int, val movePointer: Boolean = true) {
        ADV(0),
        BXL(1),
        BST(2),
        JNZ(3, false),
        BXC(4),
        OUT(5),
        BDV(6),
        CDV(7)
    }

    val opCodeLookup = OpCodes.entries.associateBy { it.code }

    var regA = 0
    var regB = 0
    var regC = 0
    var pointer: Int = 0
    var instructions: List<Int> = listOf()

    val output = mutableListOf<Int>()

    fun initialize(input: List<String>) {
        input.forEach { line ->
            if (line.contains("Register A")) {
                regA = line.split(":")[1].trim().toInt()
            } else if (line.contains("Register B")) {
                regB = line.split(":")[1].trim().toInt()
            } else if (line.contains("Register C")) {
                regC = line.split(":")[1].trim().toInt()
            } else if (line.contains("Program")) {
                instructions = line.split(":")[1].trim().split(",").map { it.toInt() }
            } else {
                // noop
            }
        }
    }

    fun run() {
        while (pointer in 0..instructions.lastIndex) {
            val advancePointer = step()
            if (advancePointer) {
                pointer += 2
            }
        }
    }

    fun step(): Boolean {
        val opCode = opCodeLookup[instructions[pointer].toInt()] ?: throw RuntimeException("Unknown opcode")
        val operand = instructions[pointer + 1]
        when (opCode) {
            OpCodes.ADV -> fADV(combo(operand))
            OpCodes.BXL -> fBXL(operand)
            OpCodes.BST -> fBST(combo(operand))
            OpCodes.JNZ -> fJNZ(operand)
            OpCodes.BXC -> fBXC(operand)
            OpCodes.OUT -> fOUT(combo(operand))
            OpCodes.BDV -> fBDV(combo(operand))
            OpCodes.CDV -> fCDV(combo(operand))
        }

        return opCode.movePointer
    }

    // Combo operands 0 through 3 represent literal values 0 through 3.
    // Combo operand 4 represents the value of register A.
    // Combo operand 5 represents the value of register B.
    // Combo operand 6 represents the value of register C.
    // Combo operand 7 is reserved and will not appear in valid programs.
    private fun combo(operand: Int): Int {
        return when (operand) {
            0 -> 0
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> regA
            5 -> regB
            6 -> regC
            7 -> throw RuntimeException("not valid combo operand")
            else -> throw RuntimeException("not valid operand")
        }
    }

    fun fADV(operand: Int) {
        // The adv instruction (opcode 0) performs division. The numerator is the value in the A register. The denominator is found by raising 2 to the power of the instruction's combo operand. (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division operation is truncated to an integer and then written to the A register.
        val numerator = regA
        val denominator = 2.00.pow(operand.toDouble()).toInt()
        regA = numerator / denominator
    }

    fun fBXL(operand: Int) {
        // The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand, then stores the result in register B.
        regB = regB xor operand
    }

    fun fBST(operand: Int) {
        // The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.
        regB = operand % 8
    }

    fun fJNZ(operand: Int) {
        // The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
        if (regA == 0) {
            pointer += 2
        } else {
            pointer = operand.toInt()
        }
    }

    fun fBXC(operand: Int) {
        // The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
        regB = regB xor regC
    }

    fun fOUT(operand: Int) {
        // The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value. (If a program outputs multiple values, they are separated by commas.)
        output.add(operand % 8)
    }

    fun fBDV(operand: Int) {
        // The bdv instruction (opcode 6) works exactly like the adv instruction except that the result is stored in the B register. (The numerator is still read from the A register.)
        val numerator = regA
        val denominator = 2.00.pow(operand.toDouble()).toInt()
        regB = numerator / denominator
    }

    fun fCDV(operand: Int) {
        // The cdv instruction (opcode 7) works exactly like the adv instruction except that the result is stored in the C register. (The numerator is still read from the A register.)
        val numerator = regA
        val denominator = 2.00.pow(operand.toDouble()).toInt()
        regC = numerator / denominator
    }
}