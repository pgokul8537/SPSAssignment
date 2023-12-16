package com.example.spsassignment

import com.example.spsassignment.utils.FormatUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class FormatRatingTest(
    val rating: Double,
    val expectedValue: String
) {
    @Test
    fun getFormattedRatingTest() {
        val result = FormatUtil.getFormattedRating(rating)
        Assert.assertEquals(expectedValue, result)
    }

    companion object {
        @JvmStatic
        @Parameters(name = "{index} : average rating - {0} expectedValue {1}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(2.01, "2"),
                arrayOf(0.1, "0.1"),
                arrayOf(0.0, "0"),
                arrayOf(0, "0")
            )
        }
    }
}