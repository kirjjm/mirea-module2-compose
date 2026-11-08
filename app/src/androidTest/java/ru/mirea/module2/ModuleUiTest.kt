package ru.mirea.module2

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.File

class ModuleUiTest {
    @get:Rule val rule = createAndroidComposeRule<ComponentActivity>()

    private fun openTask(number: Int) {
        rule.setContent { ModuleTheme { ModuleApp(number) } }
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val dir = File(instrumentation.targetContext.filesDir, "screenshots").apply { mkdirs() }
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        requireNotNull(bitmap) { "Не удалось получить снимок экрана" }
        File(dir, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
    }

    @Test fun greetingWithNameIsDisplayed() {
        openTask(1)
        rule.onNodeWithText("Привет, Кирилл!").assertIsDisplayed()
        screenshot("01_named")
    }

    @Test fun greetingWithoutNameIsDisplayed() {
        rule.setContent { ModuleTheme { Hello(null) } }
        rule.onNodeWithText("Имя не задано").assertIsDisplayed()
        screenshot("01_null_function")
    }

    @Test fun textStylesAndEllipsisArePresent() {
        openTask(3)
        rule.onAllNodesWithText(rule.activity.getString(R.string.compose_definition)).assertCountEquals(3)
        screenshot("03_text_styles")
    }

    @Test fun styledButtonIsClickable() {
        openTask(4)
        rule.onNodeWithText("Нажми на меня").assertIsDisplayed().performClick()
        screenshot("04_button")
    }

    @Test fun initialsAreDisplayed() {
        openTask(5)
        rule.onNodeWithText("КФ").assertIsDisplayed()
        screenshot("05_initials")
    }

    @Test fun bothVectorImagesAreDisplayed() {
        openTask(6)
        rule.onNodeWithContentDescription("Оранжевый круг").assertIsDisplayed()
        rule.onNodeWithContentDescription("Фиолетовый круг").assertIsDisplayed()
        screenshot("06_vectors")
    }

    @Test fun contactLayoutsAreDisplayed() {
        openTask(7)
        rule.onAllNodesWithText("Имя: Евгений").assertCountEquals(3)
        rule.onNodeWithContentDescription("Избранный контакт").performScrollTo().assertIsDisplayed()
        screenshot("07_contacts")
    }

    // Задание 9 пункт 1: наличие кнопки и накопление количества воды.
    @Test fun addWaterButtonAdds250EachTime() {
        openTask(8)
        rule.onNodeWithTag("water_amount").assertTextEquals("0 мл")
        rule.onNodeWithText("+250 мл").assertIsDisplayed()
        repeat(3) { rule.onNodeWithTag("add_water").performClick() }
        rule.onNodeWithTag("water_amount").assertTextEquals("750 мл")
        screenshot("08_water_750")
    }

    // Задание 9 пункт 2: завершение дня и сброс воды.
    @Test fun finishDayResetsWater() {
        openTask(8)
        rule.onNodeWithText("Завершить день").assertIsDisplayed()
        rule.onNodeWithTag("add_water").performClick()
        rule.onNodeWithTag("finish_day").performClick()
        rule.onNodeWithTag("water_amount").assertTextEquals("0 мл")
        rule.onNodeWithTag("day_streak").assertTextEquals("Дней подряд с нормой 1500 мл: 0")
    }

    // Задание 9 пункт 3: серия увеличивается при >= 1500 и видна пользователю.
    @Test fun twoSuccessfulDaysProduceStreakTwo() {
        openTask(8)
        repeat(2) {
            repeat(6) { rule.onNodeWithTag("add_water").performClick() }
            rule.onNodeWithTag("finish_day").performClick()
        }
        rule.onNodeWithTag("water_amount").assertTextEquals("0 мл")
        rule.onNodeWithTag("day_streak").assertTextEquals("Дней подряд с нормой 1500 мл: 2")
        screenshot("08_streak_two")
    }

    @Test fun insufficientDayResetsExistingStreak() {
        openTask(8)
        repeat(6) { rule.onNodeWithTag("add_water").performClick() }
        rule.onNodeWithTag("finish_day").performClick()
        rule.onNodeWithTag("day_streak").assertTextEquals("Дней подряд с нормой 1500 мл: 1")
        repeat(5) { rule.onNodeWithTag("add_water").performClick() }
        rule.onNodeWithTag("finish_day").performClick()
        rule.onNodeWithTag("day_streak").assertTextEquals("Дней подряд с нормой 1500 мл: 0")
        screenshot("08_streak_reset")
    }

    @Test fun waterAndStreakSurviveStateRestoration() {
        val restoration = StateRestorationTester(rule)
        restoration.setContent { ModuleTheme { WaterTracker() } }
        repeat(6) { rule.onNodeWithTag("add_water").performClick() }
        rule.onNodeWithTag("finish_day").performClick()
        rule.onNodeWithTag("add_water").performClick()
        restoration.emulateSavedInstanceStateRestore()
        rule.onNodeWithTag("water_amount").assertTextEquals("250 мл")
        rule.onNodeWithTag("day_streak").assertTextEquals("Дней подряд с нормой 1500 мл: 1")
    }

}
