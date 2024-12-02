package days

class Day2 : Day(2) {

    override fun partOne(): Int {
        val reports = inputList.map { it.split(" ").map { num -> num.toInt() } }

        return reports.count { isValid(it) }
    }

    override fun partTwo(): Int {
        val reports = inputList.map { it.split(" ").map { num -> num.toInt() } }

        return reports.count { hasValidVariant(it) }
    }

    private fun hasValidVariant(report: List<Int>): Boolean {
        if (isValid(report)) {
            return true
        }

        report.indices.forEach { i ->
            val variantList = report.toMutableList()
            variantList.removeAt(i)
            if (isValid(variantList)) {
                return true
            }
        }

        return false;
    }

    private fun isValid(report: List<Int>): Boolean {
        val diff = report.zipWithNext { a, b -> b - a }
        val validDecreasing = diff.all { it in 1..3 }
        val validIncreasing = diff.all { it in -3..-1 }
        return validDecreasing || validIncreasing;
    }
}
