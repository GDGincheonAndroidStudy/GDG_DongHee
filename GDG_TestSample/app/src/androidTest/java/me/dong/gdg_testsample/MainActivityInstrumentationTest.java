package me.dong.gdg_testsample;


import android.support.test.espresso.DataInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Dong on 2016-01-16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    private static final String KEYWORD = "ssd";

    private static final String LIST_ITEM = "[3.5가이드증정] 109,920원(단골2%+카드3%) 마이크론정품 대원CTS총판점 Micron Crucial MX200 - 250GB SSD";

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void display() {
        onView(withId(R.id.editText_search)).check(matches(isDisplayed()));
    }

    @Test
    public void searchProduct() {
        //Type text
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        //Check that the text was changed
        onView(withId(R.id.editText_search)).check(matches(withText(KEYWORD)));

        //press the button
        onView(withId(R.id.button_search)).perform(click());
    }

    //EditText String Clear Test
    @Test
    public void searchStringClear() {
        //Type text
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        //press the button
        onView(withId(R.id.imageButton_searchStrClear)).perform(click());

        //Check that the text was changed
        onView(withId(R.id.editText_search)).check(matches(withText("")));
    }

    @Test
    public void listItem() {
        //Type text
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        //Check that the text was changed
        onView(withId(R.id.editText_search)).check(matches(withText(KEYWORD)));

        //press the button
        onView(withId(R.id.button_search)).perform(click());

        //list
//        onData(anything())
//                .inAdapterView(withId(R.id.recyclerView_product))
//                .atPosition(0)
//                .check(matches(hasDescendant(
//                        allOf(withId(R.id.textView_product_title), withText(containsString(LIST_ITEM))))));
    }

    //Todo: list scroll test
    @Test
    public void listScrolls() {
        //Type text
        onView(withId(R.id.editText_search)).perform(typeText(KEYWORD), closeSoftKeyboard());

        //Check that the text was changed
        onView(withId(R.id.editText_search)).check(matches(withText(KEYWORD)));

        //press the button
        onView(withId(R.id.button_search)).perform(click());

        //onData(allOf(is(instanceOf(String.class)), is(LIST_ITEM))).perform(click());

        //onRow(LIST_ITEM).check(matches(isCompletelyDisplayed()));
    }

    private static DataInteraction onRow(String str) {
        return onData(hasEntry(equalTo(LIST_ITEM), is(str)));
    }

}
