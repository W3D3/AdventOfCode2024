package days

class Day7 : Day(7) {
    data class EquationPuzzle(val result: Long, val numbers: List<Long>)

    enum class Operator {
        ADD, MULTIPLY, CONCAT
    }

    private fun parseInput(): List<EquationPuzzle> {
        val puzzles = inputList.map { line ->
            var split = line.split(": ")
            var result = split[0].toLong()
            var numbers = split[1].split(" ").map { it.toLong() }
            EquationPuzzle(result, numbers)
        }

        return puzzles;
    }

    override fun partOne(): Long {
        val input = parseInput();
        var cnt = 0L;
        for (puzzle in input) {
            val isSolvable = isSolvable(puzzle)
            if (isSolvable) {
                println(puzzle.toString() + "is solvable")
                cnt += puzzle.result
            } else {
                println(puzzle.toString() + "is unsolvable")

            }
        }
        return cnt
    }

    fun isSolvable(puzzle: EquationPuzzle): Boolean {
        return isSolvableRec(puzzle.result, puzzle.numbers, listOf(Operator.ADD, Operator.MULTIPLY))
    }

    fun isSolvableRec(goal: Long, numbersLeft: List<Long>, allowedOperators: List<Operator>): Boolean {
        require(numbersLeft. size >= 2) { "Cannot be! Wtf" }
        for (operator in allowedOperators) {
            val result = when (operator) {
                Operator.MULTIPLY -> {
                    numbersLeft[0] * numbersLeft[1]
                }
                Operator.ADD -> {
                    numbersLeft[0] + numbersLeft[1]
                }
                Operator.CONCAT -> {
                    (numbersLeft[0].toString() + numbersLeft[1].toString()).toLong()
                }
            }
            if (result == goal && numbersLeft.size == 2) {
                return true;
            }
            if (result > goal) {
                continue;
            }
            val newList = listOf(result) + numbersLeft.subList(2, numbersLeft.size)
            if (newList.size < 2) {
                continue;
            }
            if (isSolvableRec(goal, newList, allowedOperators)) {
                return true;
            }
        }
        return false;
    }

    override fun partTwo(): Long {
        val input = parseInput();
        var cnt = 0L;
        for (puzzle in input) {
            val isSolvable = isSolvableRec(puzzle.result, puzzle.numbers, listOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCAT))
            if (isSolvable) {
                println(puzzle.toString() + "is solvable")
                cnt += puzzle.result
            } else {
                println(puzzle.toString() + "is unsolvable")

            }
        }
        return cnt
    }
}

