package days

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.hamcrest.core.IsNull.notNullValue
import org.junit.jupiter.api.Test

class Day8Test {

    private val dayEight = Day8()

    @Test
    fun testPartOne() {
        assertThat(dayEight.partOne(), `is`(14))
    }

    @Test
    fun testPartTwo() {
        val alternativeInput =
            """
                T....#....
                ...T......
                .T....#...
                .........#
                ..#.......
                ..........
                ...#......
                ..........
                ....#.....
                ..........
            """.trimIndent()

        val partTwo = dayEight.partTwo()
        assertThat(partTwo, `is`(34))
    }
}
