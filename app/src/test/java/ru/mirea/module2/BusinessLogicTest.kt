package ru.mirea.module2

import org.junit.Assert.assertEquals
import org.junit.Test

class BusinessLogicTest {
    @Test fun belowGoalResetsStreak() = assertEquals(0, nextStreak(1499, 5))
    @Test fun exactGoalExtendsStreak() = assertEquals(6, nextStreak(1500, 5))
    @Test fun aboveGoalExtendsStreak() = assertEquals(2, nextStreak(1750, 1))
    @Test fun zeroWaterResetsStreak() = assertEquals(0, nextStreak(0, 2))
    @Test fun initialCartTotalIs306() = assertEquals(306, Cart(initialProducts()).totalSum)
    @Test fun emptyCartTotalIsZero() = assertEquals(0, Cart(emptyList()).totalSum)
    @Test fun removingLastProductRecalculatesTotal() = assertEquals(250, Cart(initialProducts().dropLast(1)).totalSum)
}
