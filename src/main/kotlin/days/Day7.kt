package days

class Day7 : Day(7) {
    data class EquationPuzzle(val result: Long, val numbers: List<Long>)

    enum class Operator {
        ADD, MULTIPLY, CONCAT
    }

    private fun parseInput(): List<EquationPuzzle> {
        val puzzles = inputList.map { line ->
            val split = line.split(": ")
            val result = split[0].toLong()
            val numbers = split[1].split(" ").map { it.toLong() }
            EquationPuzzle(result, numbers)
        }

        return puzzles;
    }

    override fun partOne(): Long {
        val equations = parseInput();
        val sumOfSolvable = equations.sumOf { equation ->
            if (isSolvable(equation, listOf(Operator.ADD, Operator.MULTIPLY))) {
                equation.result
            } else {
                0L
            }
        }
        return sumOfSolvable
    }

    private fun isSolvable(puzzle: EquationPuzzle, allowedOperators: List<Operator>): Boolean {
        return isSolvableRecursive(puzzle.result, puzzle.numbers, allowedOperators)
    }

    private fun isSolvableRecursive(goal: Long, numbersLeft: List<Long>, allowedOperators: List<Operator>): Boolean {
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
            if (isSolvableRecursive(goal, newList, allowedOperators)) {
                return true;
            }
        }
        return false;
    }

    override fun partTwo(): Long {
        val equations = parseInput();
        val sumOfSolvable = equations.sumOf { equation ->
            if (isSolvable(equation, listOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCAT))) {
                equation.result
            } else {
                0L
            }
        }
        return sumOfSolvable
    }
}

