package day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input.readLists().zipWithSmallestNeighbor()
        return pairs.sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val (list1, list2) = input.readLists()
        return list1.map { number -> number to list2.count { it == number } }.sumOf { it.first * it.second }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun Pair<List<Int>, List<Int>>.zipWithSmallestNeighbor(): List<Pair<Int, Int>> {
    return this.first.sorted().zip(this.second.sorted())
}

private fun List<String>.readLists(): Pair<List<Int>, List<Int>> {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    for (line in this) {
        val (first, second) = line.split("   ").map { it.toInt() }
        list1.add(first)
        list2.add(second)
    }

    return list1 to list2
}
