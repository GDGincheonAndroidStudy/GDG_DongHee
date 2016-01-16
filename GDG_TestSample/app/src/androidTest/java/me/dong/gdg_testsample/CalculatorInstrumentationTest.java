package me.dong.gdg_testsample;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Created by Dong on 2016-01-16.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity>{

    Activity mActivity;

    public CalculatorInstrumentationTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //Injectint the Instrumentation instance is required
        //for your test to run with AdnroidUnitRunner
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}