package day03

import println
import readStringInput

fun main() {
    fun part1(input: String): Int {
        val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
        return regex.findAll(input).sumOf { it.value.performMultiplication() }
    }

    fun part2(input: String): Int {
        val multiplicationPattern = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()

        // pattern 1: between the beginning of the string and the first do() OR don't()
        val firstPattern = "^.*?(do\\(\\)|don't\\(\\))".toRegex()

        // pattern 2: between do() and don't()
        val secondPattern = "do\\(\\).*?(don't\\(\\)|$)".toRegex()

        var sum = 0

        val firstRunnablePart = firstPattern.find(input)?.value ?: ""
        sum += multiplicationPattern.findAll(firstRunnablePart).sumOf { it.value.performMultiplication() }

        val secondRunnablePart = secondPattern.findAll(input).map { it.value }
        sum += secondRunnablePart.sumOf { runnablePart ->
            multiplicationPattern.findAll(runnablePart).sumOf { it.value.performMultiplication() }
        }

        return sum
    }

    val testInput1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    check(part1(testInput1) == 161)

    val testInput2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
    check(part2(testInput2) == 48)

    val input = readStringInput("Day03").replace("\n", "")
    part1(input).println()
    part2(input).println()
}

private fun String.performMultiplication(): Int {
    val (a, b) = this.removePrefix("mul(").removeSuffix(")").split(",").map { it.toInt() }
    return a * b
}