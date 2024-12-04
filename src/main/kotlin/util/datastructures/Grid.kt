package util.datastructures

class Grid<T>(private val width: Int?, private val height: Int?, private val items: List<GridItem<T>>) {

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

    data class Point(val x: Int, val y: Int)

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
}