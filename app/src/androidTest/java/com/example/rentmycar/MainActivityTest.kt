package com.example.rentmycar

import android.util.Log.d
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    //Checks if correct activity is displayed
    @Test
    fun checkActivityVisibility() {
        onView(withId(R.id.layout_mainActivity)).check(matches(isDisplayed()))
    }

    //Checks if username label has correct text
    @Test
    fun checkText() {
        onView(withId(R.id.register_username_label)).check(matches(withText(
            "Username"
        )))
    }
}