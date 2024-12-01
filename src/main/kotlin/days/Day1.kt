package days

import kotlin.math.abs

class Day1 : Day(1) {

    override fun partOne(): Any {
        val list1 = inputList.map { it.split("   ")[0].toInt() }
            .sorted()
        val list2 = inputList.map { it.split("   ")[1].toInt() }
            .sorted()

        return list1.mapIndexed { index, value -> abs(value - list2[index]) }.sum()
    }

    override fun partTwo(): Any {
        val list1 = inputList.map { it.split("   ")[0].toInt() }
        val list2 = inputList.map { it.split("   ")[1].toInt() }

        return list1.sumOf { value -> list2.count { it == value } * value }
    }
}
