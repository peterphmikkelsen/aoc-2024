package day02

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var safe = 0
        outer@ for (line in input) {
            val levels = line.split(" ").map { it.toInt() }

            var low = 0
            var high = 1

            var wasIncreasing = (levels[low] - levels[high]).isIncreasing()

            do {
                val distance = levels[low] - levels[high]
                if (distance.isUnsafe(wasIncreasing)) {
                    continue@outer
                }

                wasIncreasing = distance.isIncreasing()
                low++; high++
            } while (high <= levels.lastIndex)

            safe++
        }
        return safe
    }

    fun part2(input: List<String>): Int {
        var safe = 0
        outer@ for (line in input) {
            val levels = line.split(" ").map { it.toInt() }.toMutableList()

            levels.println()
//            levels.zipWithNext().println()

            var low = 0
            var high = 1

            var wasIncreasing = (levels[low] - levels[high]).isIncreasing()
            var skipped = false

            do {
                val distance = levels[low] - levels[high]
                if (distance.isUnsafe(wasIncreasing)) {

                    if (low != 0 && high != levels.lastIndex) {
                        val skipLow = (levels[low - 1] - levels[high])
                        val skipHigh = (levels[low] - levels[high + 1])

                        when {
                            skipLow.isUnsafe(wasIncreasing) && skipHigh.isUnsafe(wasIncreasing) -> {
                                continue@outer
                            }
                            !skipLow.isUnsafe(wasIncreasing) && skipHigh.isUnsafe(wasIncreasing) -> {
                                if (skipped) continue@outer
                                levels.removeAt(low)
                                skipped = true
                                low--; continue
                            }
                            skipLow.isUnsafe(wasIncreasing) && !skipHigh.isUnsafe(wasIncreasing) -> {
                                if (skipped) continue@outer
                                levels.removeAt(high)
                                skipped = true
                                high++; continue
                            }
                            else -> {
                                safe++
                                continue@outer
                            }
                        }
                    }

                    if (low == 0) {
                        val skipHigh = (levels[low] - levels[high + 1])
                        if (skipHigh.isUnsafe(wasIncreasing)) {
                            continue@outer
                        }
                        if (skipped) continue@outer
                        levels.removeAt(high)
                        skipped = true
                        high++; continue
                    }

                    if (high == levels.lastIndex) {
                        val skipLow = (levels[low - 1] - levels[high])
                        if (skipLow.isUnsafe(wasIncreasing)) {
                            continue@outer
                        }
                        if (skipped) continue@outer
                        levels.removeAt(low)
                        skipped = true
                        low--; continue
                    }
                }

                wasIncreasing = distance.isIncreasing()
                skipped = false
                low++; high++
            } while (high <= levels.lastIndex)

            safe++
        }
        return safe
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun Int.isUnsafe(wasIncreasing: Boolean): Boolean {
    return this.isOutOfRange() || (wasIncreasing && !this.isIncreasing()) || (!wasIncreasing && this.isIncreasing())
}

private fun Int.isOutOfRange(): Boolean {
    return this == 0 || abs(this) > 3 || abs(this) < 1
}

private fun Int.isIncreasing(): Boolean {
    return this > 0
}