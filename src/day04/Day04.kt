package day04

import println
import readInput

fun main() {
    fun part1(input: Pair<Array<CharArray>, Set<Pair<Int, Int>>>): Int {
        val (grid, xs) = input
        var sum = 0
        for (point in xs) {
            val neighbors = point.neighbors()
            val strings = neighbors.map { neighbor -> neighbor.joinToString("") { "${grid[it.first][it.second]}" } }
            sum += strings.count { "${grid[point.first][point.second]}$it" == "XMAS" }
        }
        return sum
    }

    fun part2(input: Pair<Array<CharArray>, Set<Pair<Int, Int>>>): Int {
        val (grid, aPositions) = input
        var sum = 0
        for (point in aPositions) {
            val (d1, d2) = point.xneighbors()
            if (((grid[d1[0].first][d1[0].second] == 'M' && grid[d1[2].first][d1[2].second] == 'S') ||
                (grid[d1[0].first][d1[0].second] == 'S' && grid[d1[2].first][d1[2].second] == 'M')) &&
                ((grid[d2[0].first][d2[0].second] == 'M' && grid[d2[2].first][d2[2].second] == 'S') ||
                (grid[d2[0].first][d2[0].second] == 'S' && grid[d2[2].first][d2[2].second] == 'M'))) {
                sum++
            }
        }
        return sum
    }

    val testInput1 = readGridInput("Day04_test", 'X')
    check(part1(testInput1) == 18)

    val testInput2 = readGridInput("Day04_test", 'A')
    check(part2(testInput2) == 9)

    val input1 = readGridInput("Day04", 'X')
    part1(input1).println()

    val input2 = readGridInput("Day04", 'A')
    part2(input2).println()
}

private fun readGridInput(name: String, note: Char): Pair<Array<CharArray>, Set<Pair<Int, Int>>> {
    val input = readInput(name)
    val grid = Array(input.size + 6) { CharArray(input.first().length + 6) { '#' } }
    val xs = mutableSetOf<Pair<Int, Int>>()
    for (i in input.indices) {
        for (j in input.first().indices) {
            val c = input[i][j]
            grid[i+3][j+3] = c
            if (c == note) {
                xs.add(i + 3 to j + 3)
            }
        }
    }
    return grid to xs
}

private fun Pair<Int, Int>.neighbors(): List<List<Pair<Int, Int>>> {
    val (x, y) = this

    val directions = listOf(
        { i: Int -> x to y - i },  // south
        { i: Int -> x - i to y },  // west
        { i: Int -> x - i to y - i },  // southwest
        { i: Int -> x to y + i },  // north
        { i: Int -> x + i to y },  // east
        { i: Int -> x + i to y + i },  // northeast
        { i: Int -> x + i to y - i },  // southeast
        { i: Int -> x - i to y + i }   // northwest
    )

    return directions.map { direction -> (1 .. 3).map { i -> direction(i) } }
}

private fun Pair<Int, Int>.xneighbors(): Pair<List<Pair<Int, Int>>, List<Pair<Int, Int>>> {
    val (x, y) = this
    return listOf(x - 1 to y - 1, this, x + 1 to y + 1) to listOf(x - 1 to y + 1, this, x + 1 to y - 1)
}
