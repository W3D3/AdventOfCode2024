package days

import util.datastructures.Grid
import util.datastructures.Grid.GridItem

class Day6 : Day(6) {

    override fun partOne(): Int {
        val grid = parseInputGrid();

        var guardPos = grid.getPoint('^')
        println("Guard is at ${guardPos.x}, ${guardPos.y}")

        // up, right, down, left
        var directions = arrayOf(Grid.Point(0, -1), Grid.Point(1, 0), Grid.Point(0, 1), Grid.Point(-1, 0))

        var i = 0;
        var visited = mutableSetOf(guardPos)
        var currentDirection = directions[0];
        while (true) {
            val nextPos = guardPos + currentDirection;
            if (grid.getValue(nextPos) == null) {
                println("Leaving map at ${nextPos.x}, ${nextPos.y}")
                break;
            }
            if (grid.getValue(nextPos)?.value == '#') {
                println("Found wall at ${nextPos.x}, ${nextPos.y}")
                i++;
                currentDirection = directions[(i) % directions.size]
                println("Changing direction to ${currentDirection}")
            } else {
                println("Moving to ${nextPos.x}, ${nextPos.y}")
                visited.add(nextPos)
                guardPos = nextPos;
            }

        }

        println(grid)

        return visited.size;
    }


    data class GuardPos(val pos: Grid.Point, var direction: Grid.Point = Grid.Point(0, -1)) {
        private val directions = arrayOf(Grid.Point(0, -1), Grid.Point(1, 0), Grid.Point(0, 1), Grid.Point(-1, 0))

        fun rotateRight(): GuardPos {
            val newDirection = directions[(directions.indexOf(direction) + 1) % directions.size]
            return GuardPos(pos, newDirection)
        }

        override fun toString(): String {
            val dirPrintable = when (directions.indexOf(direction)) {
                0 -> "UP"
                1 -> "RIGHT"
                2 -> "DOWN"
                3 -> "LEFT"
                else -> "FAIL"
            }
            return "(${pos.x}, ${pos.y}) -> $dirPrintable"
        }

        fun move(): GuardPos {
            return GuardPos(pos + direction, direction)
        }
    }

    override fun partTwo(): Int {
        val grid = parseInputGrid();


        // up, right, down, left

        var turnedAt = mutableSetOf<GuardPos>()

        var guardPos = GuardPos(grid.getPoint('^'))

        val path = mutableListOf(guardPos)
        println("Guard is at ${guardPos.pos.x}, ${guardPos.pos.y}")


        while (true) {
            val nextPos = guardPos.pos + guardPos.direction;
            if (grid.getValue(nextPos) == null) {
//                println("Leaving map at ${nextPos.x}, ${nextPos.y}")
                break;
            }
            if (grid.getValue(nextPos)?.value == '#') {
//                println("Found wall at ${nextPos.x}, ${nextPos.y}")
                turnedAt.add(guardPos)
                guardPos = guardPos.rotateRight()
//                println("Changing direction to ${guardPos.direction}")
            } else {
//                println("Moving to ${nextPos.x}, ${nextPos.y}")
                guardPos = GuardPos(nextPos, guardPos.direction);
            }
            path.add(guardPos)
        }

        val gridItems = grid.items.map {
            GridItem(
                it.pos,
                //it.pos in turnedAt.map { turnedPos -> turnedPos.pos }
                if (it.pos in turnedAt.map { turnedPos -> turnedPos.pos }) {
                    '+'
                } else if (it.pos in path.map { path -> path.pos }) {
                    '-'
                } else {
                    it.value
                }
            )
        }
        val height = inputList.size;
        val width = inputList.first().length;
        val gridWithPath = Grid(width, height, gridItems);

        println(grid)
        println(gridWithPath)
//        println(path)
        println(turnedAt)

        val visited = mutableListOf<GuardPos>()
        val validLoopPositons = mutableListOf<GuardPos>()
        for (currentPath in path) {
            visited.add(currentPath);
            val potentialTurn = currentPath.rotateRight();
            var walkingPos = potentialTurn

            while (true) {
                if (visited.contains(potentialTurn)) {
                    println("Found loop at $potentialTurn")
                    validLoopPositons.add(potentialTurn)
                    break;
                }
                walkingPos = walkingPos.move();
                if (grid.getValue(walkingPos.pos) == null) {
//                    println("Leaving map")
                    break;
                }
            }
        }

        return validLoopPositons.size;
    }

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
}
