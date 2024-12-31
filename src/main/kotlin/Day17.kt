import java.io.File
import java.lang.ArithmeticException
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
        val c = Computer()
        c.initialize(File("inputs/day17.txt").readLines())
        return c.runUntilOutputMatchesInstructions().toLong()
    }

    override fun part1ResultDescription() = "program output"

    override fun part2ResultDescription() = "a register val to output program input"


}

class InputComputer {
    var regA = 0L
    var regB = 0L
    var regC = 0L
    val output = mutableListOf<Int>()

    fun run() {
        while (true) {
            // Program:
            // 2 => 2 BST
            // 4
            regB = regA % 8
            // 1 => 1 BXL
            // 5
            regB = regB xor 5 // 0101
            // 7 => 7 CDV
            // 5
            regC = regA / (2.00.pow(regB.toDouble())).toInt()
            // 1 => 1 BXL
            // 6
            regB = regB xor 6 // 0110
            // 0 => 0 ADV
            // 3
            regA = regA / 8
            // 4 => 4 BXC
            // 0
            regB = regB xor regC
            // 5 => 5 OUT
            // 5
            output.add((regB % 8L).toInt())
            // 3 => 3 JNZ
            // 0
            if (regA == 0L) {
                break
            }
        }
        val x = 0
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

    var regA = 0L
    var regB = 0L
    var regC = 0L
    var pointer: Int = 0
    var instructions: List<Int> = listOf()

    val output = mutableListOf<Int>()

    fun initialize(input: List<String>) {
        input.forEach { line ->
            if (line.contains("Register A")) {
                regA = line.split(":")[1].trim().toLong()
            } else if (line.contains("Register B")) {
                regB = line.split(":")[1].trim().toLong()
            } else if (line.contains("Register C")) {
                regC = line.split(":")[1].trim().toLong()
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

    fun runUntilOutputMatchesInstructions(): Long {
        var startingA = 0L


        // 14381459866
//        val startingAs = mutableListOf<Int>(8, 64, 512, 4096, 32768, 262144, 2097152, 16777216)
        val startingAs = mutableListOf<Int>(16777216, 16777217, 16777218)
        var digitsToMatch = 1
        while (output != instructions || startingAs.isEmpty()) {
            startingA += 1
//            val initRegA = startingAs.removeFirst().toLong()
            reset(startingA)
//            println("Initial => A: $regA, B: $regB, C: $regC, :: ${output.joinToString(",")}")

            while (pointer in 0..instructions.lastIndex) {
                val advancePointer = step()
                if (advancePointer) {
                    pointer += 2
                }

//                if (output != instructions.slice(0..<output.size)) {
////                    if (output.size > 1) {
////                        println("Partial match, regA: $startingA: $output ~~ $instructions")
////                    }
////                    break
//                }
            }
            val outputDigits: List<Int>
            val instructionDigits = try {
                outputDigits = output.slice((output.size - digitsToMatch)..output.lastIndex)
                instructions.slice((instructions.size - digitsToMatch)..instructions.lastIndex)
            } catch (e: Exception) {
                continue
            }
            if (outputDigits == instructionDigits) {
                println("${output.joinToString(",")}")
                println("${outputDigits.joinToString(",")} == ${instructionDigits.joinToString(",")}")
                println("input: $startingA => $output")

                digitsToMatch += 1
                if (output.size == digitsToMatch) {
                    startingA = startingA shl 3
                }
            }
//            println("$initRegA => ${output.joinToString(",")}")
        }
        return startingA
    }

    fun reset(initRegA: Long) {
        regA = initRegA
        regB = 0
        regC = 0
        pointer = 0
        output.clear()
    }

    fun step(): Boolean {
        val opCode = opCodeLookup[instructions[pointer].toInt()] ?: throw RuntimeException("Unknown opcode")
        val operand = instructions[pointer + 1].toLong()
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

//        println("${opCode.name}(${combo(operand)}) => A: $regA, B: $regB, C: $regC, :: ${output.joinToString(",")}")

        return opCode.movePointer
    }

    // Combo operands 0 through 3 represent literal values 0 through 3.
    // Combo operand 4 represents the value of register A.
    // Combo operand 5 represents the value of register B.
    // Combo operand 6 represents the value of register C.
    // Combo operand 7 is reserved and will not appear in valid programs.
    private fun combo(operand: Long): Long {
        return when (operand) {
            0L -> 0L
            1L -> 1L
            2L -> 2L
            3L -> 3L
            4L -> regA
            5L -> regB
            6L -> regC
            7L -> throw RuntimeException("not valid combo operand")
            else -> throw RuntimeException("not valid operand")
        }
    }

    fun fADV(operand: Long) {
        // The adv instruction (opcode 0) performs division. The numerator is the value in the A register. The denominator is found by raising 2 to the power of the instruction's combo operand. (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division operation is truncated to an integer and then written to the A register.
        val numerator = regA
        val denominator = 2.00.pow(operand.toDouble()).toInt()
        try {
            regA = numerator / denominator
        } catch (e: ArithmeticException) {
            output.addAll(0..10)
        }
    }

    fun fBXL(operand: Long) {
        // The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand, then stores the result in register B.
        regB = regB xor operand
    }

    fun fBST(operand: Long) {
        // The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.
        regB = operand % 8
    }

    fun fJNZ(operand: Long) {
        // The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
        if (regA == 0L) {
            pointer += 2
        } else {
            pointer = operand.toInt()
        }
    }

    fun fBXC(operand: Long) {
        // The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
        regB = regB xor regC
    }

    fun fOUT(operand: Long) {
        // The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value. (If a program outputs multiple values, they are separated by commas.)
        output.add((operand % 8L).toInt())
    }

    fun fBDV(operand: Long) {
        // The bdv instruction (opcode 6) works exactly like the adv instruction except that the result is stored in the B register. (The numerator is still read from the A register.)
        val numerator = regA
        val denominator = 2.00.pow(operand.toDouble()).toInt()
        try {
            regB = numerator / denominator
        } catch (e: ArithmeticException) {
            output.addAll(0..10)
        }
    }

    fun fCDV(operand: Long) {
        // The cdv instruction (opcode 7) works exactly like the adv instruction except that the result is stored in the C register. (The numerator is still read from the A register.)
        val numerator = regA
        val denominator = 2.00.pow(operand.toDouble()).toInt()
        try {
            regC = numerator / denominator
        } catch (e: ArithmeticException) {
            output.addAll(0..10)
        }
    }
}