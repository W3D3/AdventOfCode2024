package days

import util.datastructures.*
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

        val tempMap = mutableMapOf<Point, Char>()
        var actor: Actor? = null;
        inputList.forEachIndexed { y, row ->
            row.forEachIndexed { x, content ->
                if (content == '^') {
                    actor = Actor(Position(Point(x, y), Direction.UP), 'X')
                }
                tempMap[Point(x, y)] = if (content == '^') '.' else content
            }
        }
        val map = NavigatableMap<Char>(actor!!)
        map.loadMap(tempMap)

        println(map)

        var inBounds = true
        while (inBounds) {
            val result = map.moveActor()
            inBounds = result != MovementResult.OUT_OF_BOUNDS
            if (result == MovementResult.WALL) {
                map.turnActorRight()
            }
        }
        println(map)
        // filter path for only the points the actor has visited twice in different directions
        val intersectionPoints = actor!!.path.filter { point -> actor!!.path.count { it == point } > 1 }

        val startingPos = actor!!.path.first().point
        val validPath = actor!!.path.toList()
        var loopCount = 0
        // make a list of list of all the points the actor has visited and set one to be an obstacle
        validPath.forEachIndexed { index, potentialTurn ->
            map.resetActor();
//            println("Trying to turn at ${potentialTurn}")
//            println(map)
            if (potentialTurn.next().point == startingPos) {
                println("Cannot place obstacle at ${potentialTurn.next().point}")
                // skip turning if we would need to put an obstacle at the starting point
                return@forEachIndexed
            }

            var inBounds = true
            while (inBounds) {
                if (map.actor.pos == potentialTurn && potentialTurn.next().point != startingPos) {
//                    println("Pretending to be a wall at ${potentialTurn}")
                    map.turnActorRight()
                };
                val result = map.moveActor()
                inBounds = result != MovementResult.OUT_OF_BOUNDS

                if (result == MovementResult.WALL) {
                    map.turnActorRight()
                }
                if (result == MovementResult.LOOPING) {
                    println("Actor looping at ${actor!!.pos.point}")
                    loopCount++;
                    break;
                }
            }
//            println(map)
//            println("Actor left map at ${actor!!.pos.point}")

        }





        return loopCount;
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
