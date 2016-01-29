package me.dong.gdg_testsample;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Dong on 2016-01-16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    private static final String KEYWORD = "ssd";

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testDisplay(){
        onView(withId(R.id.editText_search)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchProduct() {
        //Type text
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        //Check that the text was changed
        onView(withId(R.id.editText_search)).check(matches(withText(KEYWORD)));

        //press the button
        onView(withId(R.id.button_search)).perform(click());
    }

    //EditText String Clear Test
    @Test
    public void searchStringClear(){
        //Type text
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        //press the button
        onView(withId(R.id.imageButton_searchStrClear)).perform(click());

        //Check that the text was changed
        onView(withId(R.id.editText_search)).check(matches(withText("")));
    }
}
