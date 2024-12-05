package days

class Day5 : Day(5) {

    override fun partOne(): Int {
        val (rules, pages) = parse()

        val validPages = pages.filter { getViolatingRule(it, rules) == null }

        return validPages.sumOf { findMiddle(it) }
    }

    private fun getViolatingRule(page: List<Int>, rules: List<Pair<Int, Int>>): Pair<Int, Int>? {
        page.forEachIndexed { index, number ->
            rules.forEach { rule ->
                // if the second number of the rule is on the page, AND first number is as well, we check the rule
                if (number == rule.second && page.contains(rule.first)) {
                    // check if the first number of the rule is before the second number on the page,
                    // otherwise it's a violation and we return the rule
                    if (!page.subList(0, index).contains(rule.first)) {
                        return rule
                    }
                }
            }
        }
        return null
    }

    private fun findMiddle(page: List<Int>): Int {
        val middle = page.size / 2
        return page[middle]
    }

    override fun partTwo(): Int {
        val (rules, pages) = parse()

        val invalidPages = pages.filter { getViolatingRule(it, rules) != null }

        // correct these pages by swapping the numbers of the rules they violate
        val fixedPages = invalidPages.map { page ->
            var fixedPage = page
            var violatingRule = getViolatingRule(page, rules)
            while (violatingRule != null) {
                fixedPage =
                    swap(fixedPage, fixedPage.indexOf(violatingRule.second), fixedPage.indexOf(violatingRule.first))
                violatingRule = getViolatingRule(fixedPage, rules)
            }
            fixedPage
        }

        val middleValuesSum = fixedPages.sumOf { findMiddle(it) }

        return middleValuesSum
    }

    private fun swap(page: List<Int>, index1: Int, index2: Int): List<Int> {
        val swappedPage = page.toMutableList()
        val temp = swappedPage[index1]
        swappedPage[index1] = page[index2]
        swappedPage[index2] = temp
        return swappedPage
    }

    private fun parse(): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        // split inputList into two lists, denoted by an empty line
        val splitIndex = inputList.indexOf("")
        val rules = inputList.subList(0, splitIndex).map { Pair(it.split("|")[0].toInt(), it.split("|")[1].toInt()) }
        val manualPages = inputList.subList(splitIndex + 1, inputList.size).map { it.split(",").map { it.toInt() } }

        return Pair(rules, manualPages)
    }
}
