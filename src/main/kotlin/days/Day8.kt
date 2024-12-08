package days

import util.datastructures.Grid
import util.datastructures.Grid.GridItem
import kotlin.math.max

class Day8 : Day(8) {

    private fun parseInputGrid(): Grid<Char> {
        // split inputList into two lists, denoted by an empty line
        val height = inputList.size;
        val width = inputList.first().length;

        val gridItems = inputList.flatMapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                GridItem(Grid.Point(x, y), cell)
            }
        }

        return Grid(width, height, gridItems);
    }

    override fun partOne(): Int {
        val grid = parseInputGrid()
        println(grid)

        val locationsByFrequency = grid.items.groupBy { it.value }.filterKeys { it != '.' }

        val allInterferencePoints: MutableSet<Grid.Point> = mutableSetOf()
        for ((frequency, location) in locationsByFrequency) {
            val points = location.map { it.pos }
            println("${frequency} $points")
            val pairs: List<Pair<Grid.Point, Grid.Point>> = allPairs(points)
            val interferencePositions = pairs.map { it.first + it.first - it.second } + pairs.map { it.second + it.second - it.first }
//            println(interferencePositions)

            val interferencePositionsOnMap = interferencePositions.filter { (x, y) -> x >= 0 && x < grid.width && y >= 0 && y < grid.height }
            allInterferencePoints.addAll(interferencePositionsOnMap)
            println(interferencePositionsOnMap)
        }
        return allInterferencePoints.size
    }

    private fun allPairs(points: List<Grid.Point>): List<Pair<Grid.Point, Grid.Point>> {
        // generate all possible combination of pairs from the given list
        return points.flatMapIndexed { i: Int, pointA: Grid.Point ->
            points.mapIndexed { j: Int, pointB: Grid.Point ->
                if (i != j) pointA to pointB else null
            }.filterNotNull()
        }
    }

    override fun partTwo(): Int {
        val grid = parseInputGrid()
        println(grid)

        val locationsByFrequency = grid.items.groupBy { it.value }.filterKeys { it != '.' }

        val allInterferencePoints: MutableSet<Grid.Point> = mutableSetOf()
        for ((frequency, location) in locationsByFrequency) {
            val points = location.map { it.pos }
            println("${frequency} $points")
            val pairs: List<Pair<Grid.Point, Grid.Point>> = allPairs(points)
            val interferencePositions: MutableSet<Grid.Point> = mutableSetOf()

            for (i in 0..max(grid.width, grid.height)) {
                interferencePositions.addAll(pairs.map { it.first + (it.first - it.second) * i } + pairs.map { it.second + (it.second - it.first) * i })
            }
//            println(interferencePositions)

            val interferencePositionsOnMap = interferencePositions.filter { (x, y) -> x >= 0 && x < grid.width && y >= 0 && y < grid.height }
            allInterferencePoints.addAll(interferencePositionsOnMap)
            println(interferencePositionsOnMap)
        }
        return allInterferencePoints.size

    }
}

