package days

class Day5 : Day(5) {

    override fun partOne(): Int {
        val (rules, pages) = parse()

        val validPages = mutableListOf<List<Int>>()
        pages.forEach { page ->
            if (isValidPage(page, rules)) {
                validPages.add(page)
            }
        }

        println()
        println(validPages)

        val middleValuesSum = validPages.sumOf { findMiddle(it) }

        return middleValuesSum
    }

    private fun isValidPage(page: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        page.forEachIndexed { index, number ->
            rules.forEach { (first, second) ->
                if (number == second && page.contains(first) && !page.subList(0, index).contains(first)) {
                    println("Page: $page has $number with missing preceding number: $first")
                    return false
                }
            }
        }
        return true
    }

    private fun getViolatingRule(page: List<Int>, rules: List<Pair<Int, Int>>): Pair<Int, Int>? {
        page.forEachIndexed { index, number ->
            rules.forEach { rule ->
                if (number == rule.second && page.contains(rule.first)) {
                    if (!page.subList(0, index).contains(rule.first)) {
                        println("Page: $page has $number with missing preceding number: ${rule.first}")
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

        val invalidPages = mutableListOf<List<Int>>()
        pages.forEach { page ->
            if (!isValidPage(page, rules)) {
                invalidPages.add(page)
            }
        }

        println()
        println(invalidPages)
        val fixedPages = mutableListOf<List<Int>>()

        // correct these pages by swapping the numbers of the rules they violate
        invalidPages.forEach { page ->
            var violatingRule = getViolatingRule(page, rules)
            var fixedPage = page;
            while (violatingRule != null) {
                fixedPage =
                    swap(fixedPage, fixedPage.indexOf(violatingRule.second), fixedPage.indexOf(violatingRule.first))
                println("Page: $page violates $violatingRule")
                println("Fixed page: $fixedPage")

                violatingRule = getViolatingRule(fixedPage, rules)
            }
            fixedPages.add(fixedPage)
        }

        val middleValuesSum = fixedPages.sumOf { findMiddle(it) }

        return middleValuesSum
    }

    fun swap(page: List<Int>, index1: Int, index2: Int): List<Int> {
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
        println(rules)
        println(manualPages)
        return Pair(rules, manualPages)
    }
}
