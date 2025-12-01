package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var position = 50
        var zeroCounter = 0

        for (instruction in input) {
            val rotation = parseInstruction(instruction)
            position = normalizePosition(position + rotation)
            if (position == 0) zeroCounter++
        }

        return zeroCounter
    }

    fun part2(input: List<String>): Int {
        var position = 50
        var zeroCounter = 0

        for (instruction in input) {
            val rotation = parseInstruction(instruction)
            val intermediatePositions = getLockPosition(position, rotation)
            zeroCounter += intermediatePositions.dropLast(1).count { it == 0 } // don't count the final position
            position = intermediatePositions.last()
        }

        if (position == 0) zeroCounter++
        return zeroCounter
    }


    check(part1(exampleInput.lines()) == 3)
    check(part2(exampleInput.lines()) == 6)

    val input = readInput("day01/input")
    part1(input).println()
    part2(input).println()
}

private fun parseInstruction(instruction: String): Int {
    val (direction, steps) = instruction.first() to instruction.drop(1).toInt()
    return if (direction == 'R') steps else -steps
}

private fun normalizePosition(position: Int): Int {
    val rawPosition = position % 100
    return if (rawPosition < 0) rawPosition + 100 else rawPosition
}

private fun getLockPosition(position: Int, rotation: Int): List<Int> {
    val finalRawPosition = position + rotation
    val positionRange = IntProgression.fromClosedRange(
        rangeStart = position,
        rangeEnd = finalRawPosition,
        step = if (position < finalRawPosition) 1 else -1
    )
    return sequence {
        for (rawPosition in positionRange) yield(normalizePosition(rawPosition))
    }.toList()
}

private val exampleInput = """
    L68
    L30
    R48
    L5
    R60
    L55
    L1
    L99
    R14
    L82
""".trimIndent()