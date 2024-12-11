package day05

import println
import readInput

fun main() {
    fun part1(input: Pair<Map<Int, Set<Int>>, List<List<Int>>>): Int {
        val (rules, pages) = input
        return pages.sumOf { numbers ->
            if (numbers == numbers.sortedWith(PageComparator(rules))) {
                return@sumOf numbers.middlePage()
            }
            return@sumOf 0
        }
    }

    fun part2(input: Pair<Map<Int, Set<Int>>, List<List<Int>>>): Int {
        val (rules, pages) = input
        return pages.sumOf { numbers ->
            val ordered = numbers.sortedWith(PageComparator(rules))
            if (numbers != ordered) {
                return@sumOf ordered.middlePage()
            }
            return@sumOf 0
        }
    }

    val testInput = readDayInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readDayInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun readDayInput(name: String): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
    val rules = mutableMapOf<Int, MutableSet<Int>>()
    val pages = mutableListOf<List<Int>>()

    val lines = readInput(name)
    for (line in lines) {
        if (line.isEmpty()) continue

        if (line.contains("|")) {
            val (a, b) = line.split("|").map { it.toInt() }
            if (rules.containsKey(b)) {
                rules[b]!!.add(a)
            } else {
                rules[b] = mutableSetOf(a)
            }
        } else {
            pages.add(line.split(",").map { it.toInt() })
        }
    }

    return rules to pages
}

private fun List<Int>.middlePage(): Int {
    return this.elementAt(this.size/2)
}

private class PageComparator(private val rules: Map<Int, Set<Int>>): Comparator<Int> {
    override fun compare(o1: Int?, o2: Int?): Int {
        val mustBeAfter = rules.getOrDefault(o1, emptySet())
        return if (o2 in mustBeAfter) 1 else -1
    }
}
