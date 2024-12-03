package days

class Day3 : Day(3) {

    override fun partOne(): Int {
        return executeMults(inputString).sum()
    }

    override fun partTwo(): Int {
        // remove all text between don't() and do(), making sure only to take the closes ones
        val disabledPartRegex = Regex("""don't\(\)[\s\S]*?do\(\)""")
        val sanitizedInput = (inputString + "do()").replace(disabledPartRegex, "")

        return executeMults(sanitizedInput).sum()
    }

    /**
     * Execute all valid multiplications in the input string
     * @param input the input string
     * @return a sequence of all multiplications results
     */
    private fun executeMults(input: String): Sequence<Int> {
        val regex = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")
        val matches = regex.findAll(input)
        return matches.map {
            try {
                it.groupValues[1].toInt() * it.groupValues[2].toInt()
            } catch (e: Exception) {
                null
            }
        }.filterNotNull()
    }
}
