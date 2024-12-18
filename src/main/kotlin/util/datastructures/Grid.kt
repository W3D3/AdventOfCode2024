package util.datastructures

class Grid<T>(val width: Int, val height: Int, val items: List<GridItem<T>>) {

    fun getRows(): List<List<GridItem<T>>> {
        return items.groupBy { item -> item.pos.y }.map { it.value }
    }

    fun getColumns(): List<List<GridItem<T>>> {
        return items.groupBy { item -> item.pos.x }.map { it.value }
    }

    fun getDiagonals(): List<List<GridItem<T>>> {
        return items.groupBy { item -> item.pos.x - item.pos.y }.map { it.value }
    }

    fun getInverseDiagonals(): List<List<GridItem<T>>> {
        return items.groupBy { item -> item.pos.x + item.pos.y }.map { it.value }
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y)
        operator fun times(other: Int) = Point(x * other, y * other)
    }

    data class GridItem<T>(val pos: Point, val value: T)

    private fun notExceedingWidth(num : Int): Boolean {
        return num < (width?.minus(1) ?: Int.MAX_VALUE);
    }
    private fun notExceedingHeight(num : Int): Boolean {
        return num < (height?.minus(1) ?: Int.MAX_VALUE);
    }

    fun getValue(pos: Point): GridItem<T>? {
        return items.find { it.pos == pos }
    }


    fun getPoint(value: T): Point {
        return items.find { it.value == value }!!.pos
    }

    fun getAdjecentGridPoints(point: Point, diagonal: Boolean): List<Point> {
        val ( x, y ) = point;
        val ajecentPoints = buildList {
            if (x > 0) add(Point(x - 1, y))
            if (notExceedingWidth(x)) add(Point(x + 1, y))
            if (y > 0) add(Point(x, y - 1))
            if (notExceedingHeight(y)) add(Point(x, y + 1))
            if (diagonal) {
                if (x > 0 && y > 0) add(Point(x - 1, y - 1))
                if (notExceedingWidth(x) && y > 0) add(Point(x + 1, y - 1))
                if (x > 0 && notExceedingHeight(y)) add(Point(x - 1, y + 1))
                if (notExceedingWidth(x) && notExceedingHeight(y)) add(Point(x + 1, y + 1))
            }
        }

        return ajecentPoints
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in getRows()) {
            for (item in row) {
                sb.append(item.value)
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}