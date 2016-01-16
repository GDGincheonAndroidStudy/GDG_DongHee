package me.dong.gdg_testsample;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Dong on 2016-01-16.
 */
public class MainActivityInstrumentationTest {

    private static final String KEYWORD = "ssd";

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void searchProduct() {
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        onView(withText("ssd")).perform(click());

        String expectedTest = KEYWORD;
        onView(withId(R.id.editText_search)).check(matches(withText(expectedTest)));
    }
}
