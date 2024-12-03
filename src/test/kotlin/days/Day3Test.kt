package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.hamcrest.core.IsNull.notNullValue
import org.junit.jupiter.api.Test

class Day3Test {

    private val dayThree = Day3()

    @Test
    fun testPartOne() {
        assertThat(dayThree.partOne(), `is`(161))
    }

    @Test
    fun testPartTwo() {
        val partTwo = dayThree.partTwo()
        assertThat(partTwo, `is`(48))
    }
}
