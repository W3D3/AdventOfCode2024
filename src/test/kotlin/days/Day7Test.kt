package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.hamcrest.core.IsNull.notNullValue
import org.junit.jupiter.api.Test

class Day7Test {

    private val daySeven = Day7()

    @Test
    fun testPartOne() {
        assertThat(daySeven.partOne(), `is`(3749))
    }

    @Test
    fun testPartTwo() {
        val partTwo = daySeven.partTwo()
        assertThat(partTwo, `is`(11387))
    }
}
