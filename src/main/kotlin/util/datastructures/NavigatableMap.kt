package util.datastructures

class NavigatableMap<T>(val actor: Actor) {

    private val map = mutableMapOf<Point, T>()

    fun put(point: Point, value: T) {
        map[point] = value
    }

    fun get(point: Point): T? {
        return map[point]
    }

    fun getKeys(): Set<Point> {
        return map.keys
    }

    fun getValues(): Collection<T> {
        return map.values
    }

    fun getEntries(): Set<Map.Entry<Point, T>> {
        return map.entries
    }


    override fun toString(): String {
        val maxX = map.keys.maxOf { it.x }
        val maxY = map.keys.maxOf { it.y }

        val sb = StringBuilder()
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val point = Point(x, y)
                val actorIsHere = actor.pos.point == point
                val relevantPath = actor.path.filter { it.point == point }
                if (actorIsHere) {
                    sb.append(actor.value)
                } else if (relevantPath.isNotEmpty()) {
                    // put - if path direction was horizontal, or | if path direction was vertical, or + if both
                    val isVertical =
                        relevantPath.find { it.direction == Direction.DOWN || it.direction == Direction.UP } != null
                    val isHorizontal =
                        relevantPath.find { it.direction == Direction.LEFT || it.direction == Direction.RIGHT } != null
                    if (isHorizontal && isVertical) {
                        sb.append("+")
                    } else if (isHorizontal) {
                        sb.append('-')
                    } else {
                        sb.append("|")
                    }
                } else {
                    sb.append(map[point] ?: ".")
                }
            }
            sb.append("\n")
        }

        return sb.toString()
    }

    private fun getLimitX(): Int {
        return map.keys.maxOf { it.x }
    }

    private fun getLimitY(): Int {
        return map.keys.maxOf { it.y }
    }

    // TODO let caller pass in obstacle function
    fun moveActor(): MovementResult {
        map.filter { it.value == '#' }.keys.let { obstacles ->
            return actor.move(getLimitX(), getLimitY(), obstacles);
        }
    }

    fun resetActor() {
        actor.reset()
    }

    fun turnActorRight() {
        actor.turnRight()
    }

    fun loadMap(tempMap: Map<Point, T>) {
        map.putAll(tempMap)
    }
}

enum class MovementResult {
    WALL, MOVED, OUT_OF_BOUNDS, LOOPING
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

class Direction private constructor(val x: Int, val y: Int) {
    companion object {
        val UP = Direction(0, -1)
        val DOWN = Direction(0, 1)
        val LEFT = Direction(-1, 0)
        val RIGHT = Direction(1, 0)
    }

    override fun toString(): String {
        return when (this) {
            UP -> "UP"
            DOWN -> "DOWN"
            LEFT -> "LEFT"
            RIGHT -> "RIGHT"
            else -> {
                throw IllegalArgumentException("Invalid direction")
            }
        }
    }

    fun turnRight(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            else -> {
                throw IllegalArgumentException("Invalid direction")
            }
        }
    }

    fun turnLeft(): Direction {
        return when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
            else -> {
                throw IllegalArgumentException("Invalid direction")
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Direction

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

data class Position(val point: Point, val direction: Direction) {
    operator fun plus(other: Point) = Position(point + other, direction)
    operator fun plus(other: Position) =
        Position(point + other.point, direction)

    operator fun plus(other: Direction) =
        Position(Point(point.x + other.x, point.y + other.y), direction)

    fun next(): Position {
        return this + direction;
    }
}

data class Actor(var pos: Position, val value: Any) {
    val initalPos = pos.copy();
    var path = mutableListOf(pos.copy())

    fun reset() {
        pos = initalPos.copy()
        path = mutableListOf(pos.copy())
    }

    fun move(limitX: Int, limitY: Int, obstacles: Set<Point>): MovementResult {
        val newPos = pos.next()
        val result = tryMoveTo(newPos, limitX, limitY, obstacles)
        if (result.first == MovementResult.MOVED) {
            path.add(newPos.copy())
            pos = newPos;
        }
        return result.first
    }

    private fun tryMoveTo(newPos: Position, limitX: Int, limitY: Int, obstacles: Set<Point>): Pair<MovementResult, Position> {
        if (newPos.point in obstacles) {
            return MovementResult.WALL to pos
        }
        if (path.contains(newPos)) {
            return MovementResult.LOOPING to pos
        }
        if (newPos.point.x < 0 || newPos.point.y < 0 || newPos.point.x > limitX || newPos.point.y > limitY) {
            return MovementResult.OUT_OF_BOUNDS to pos
        }
        return MovementResult.MOVED to pos
    }

    fun turnRight() {
        pos = Position(pos.point, pos.direction.turnRight())
        path.add(pos.copy())
    }

    fun turnLeft() {
        pos = Position(pos.point, pos.direction.turnLeft())
        path.add(pos.copy())
    }
}