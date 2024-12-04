package days

import util.datastructures.Grid
import util.datastructures.Grid.GridItem

class Day4 : Day(4) {

    override fun partOne(): Int {
        val grid = parseGridFromInput()

        val searchValues = "XMAS".toCharArray().toList();

        return countInGrid(grid, searchValues) + countInGrid(grid, searchValues.reversed())
    }

    /**
     * Count the number of times the searchValues appear in the grid
     * Will search horizontally, vertically, diagonally and inverse diagonally
     * and also count the reversed searchValues (i.e. if the searchValues are "XMAS" it will also count "SAMX")
     * @param grid the grid to search in
     * @param searchValues the values to search for
     */
    private fun countInGrid(
        grid: Grid<Char>,
        searchValues: List<Char>
    ): Int {
        val horizontalSearch = countInList(grid.getRows(), searchValues);
        val verticalSearch = countInList(grid.getColumns(), searchValues);
        val diagonalSearch = countInList(grid.getDiagonals(), searchValues);
        val invDiagonalSearch = countInList(grid.getInverseDiagonals(), searchValues);

        return horizontalSearch + verticalSearch + diagonalSearch + invDiagonalSearch;
    }

    private fun <T> countInList(list: List<List<GridItem<T>>>, searchValues: List<T>): Int {
        return list.sumOf { row ->
            val found = row.map { it.value }.windowed(searchValues.size).filter { it == searchValues }
            found.size
        };
    }

    override fun partTwo(): Int {
        val grid = parseGridFromInput()

        val searchValues = "MAS".toCharArray().toList();

        // search diagonally for MAS in both directions
        val diagonalSearch = grid.getDiagonals().flatMap { row ->
            findCenterCoordinatesOfSearchTerm(row, searchValues)
        };
        val invDiagonalSearch = grid.getInverseDiagonals().flatMap { row ->
            findCenterCoordinatesOfSearchTerm(row, searchValues)
        }

        // find overlap between both diagonal lines, those are our A's
        val overlap = diagonalSearch.intersect(invDiagonalSearch.toSet());

        return overlap.size
    }

    private fun findCenterCoordinatesOfSearchTerm(
        row: List<GridItem<Char>>,
        searchValues: List<Char>
    ): List<GridItem<Char>> {
        val found = row.windowed(searchValues.size)
            .filter { itemList -> itemList.map { it.value } == searchValues || itemList.map { it.value } == searchValues.reversed() }
        return found.map { it[searchValues.size/2] }
    }

    private fun parseGridFromInput(): Grid<Char> {
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
