package com.raywenderlich.android.emojicalculator

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raywenderlich.android.emojicalculator.ScreenRobot.Companion.withRobot
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    // SHEET: https://developer.android.com/training/testing/espresso/cheat-sheet

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun onLaunchCheckAmountInputIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        // Add a matcher for a view with the ID inputAmount
        onView(withId(R.id.inputAmount))
            // Verify that the matched view is displayed on screen
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchOkayButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        // The main difference here is that you use withText() instead of withId(). You can pass both
        // references to String resources like you did above or String literals to this function
        onView(withText(R.string.okay))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.buttonOkay))
            .perform(click())

        // There’s an extra layer of nesting in this matcher. You can tell Espresso that it needs
        // to match both the conditions on the view using allOf().
        onView(allOf(withId(R.id.textTip), withText("")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsFilledTipIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.inputAmount))
            .perform(typeText("11"))

        onView(withId(R.id.buttonOkay))
            .perform(click())

        onView(withId(R.id.textTip))
            .check(matches(withText("1,98")))

        onView(withId(R.id.textTotal))
            .check(matches(withText("12,98")))
    }

    @Test
    fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedAmountIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.switchRound))
            .perform(click())
        onView(withId(R.id.inputAmount))
            .perform(typeText("11"))
        onView(withId(R.id.buttonOkay))
            .perform(click())

        onView(withId(R.id.textTip))
            .check(matches(withText("2,00")))
        onView(withId(R.id.textTotal))
            .check(matches(withText("13,00")))
    }

    /**
     *  WITH ROBOT
     */

    @Test
    fun whenOkayButtonIsPressedAndAmountIsFilledTipIsSetWithRobot() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("1,98")
            .verifyTotalIsCorrect("12,98")
    }

    @Test
    fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedAmountIsCorrectWithRobot() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("2,00")
            .verifyTotalIsCorrect("13,00")
    }

    /**
     *  Screen Robots pull out some of these tasks into helper functions. This makes your tests more
     *  expressive, readable and easier to update your if something in the UI changes. For example,
     *  if an ID changes, instead of updating it in a bunch of separate tests, you can update it in
     *  the one robot.
     */
    class CalculatorScreenRobot : ScreenRobot<CalculatorScreenRobot>() {

        fun verifyTipIsCorrect(tip: String): CalculatorScreenRobot =
            checkViewHasText(R.id.textTip, tip)

        fun verifyTotalIsCorrect(total: String): CalculatorScreenRobot =
                checkViewHasText(R.id.textTotal, total)

        fun inputCheckAmountAndSelectOkayButton(input: String): CalculatorScreenRobot =
            enterTextIntoView(R.id.inputAmount, input)
                    .clickOkOnView(R.id.buttonOkay)
    }
}