package com.example.rentmycar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.rentmycar.activity.MainMenuActivity
import org.junit.Rule
import org.junit.Test

class MainMenuActivity {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainMenuActivity::class.java)

    //Checks if correct activity is displayed
    @Test
    fun checkActivityVisibility() {
        onView(withId(R.id.main_layout)).check(matches(isDisplayed()))
    }

    //Checks if menu button navigates correctly to the designated layout
    @Test
    fun checkNavigation() {
        onView(withId(R.id.main_menu_my_trip_btn)).perform(click())

        onView(withId(R.id.trips_layout)).check(matches(isDisplayed()))
    }
}