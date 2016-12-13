package io.github.minhwang.activitymonitorexample;

import android.app.Instrumentation;
import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testActivityMonitor() {
        this.activityTestRule.getActivity();

        // Setup activity monitor
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(44455555, null);
        Instrumentation.ActivityMonitor activityMonitor = new Instrumentation.ActivityMonitor(SecondActivity.class.getName(), activityResult, true);
        InstrumentationRegistry.getInstrumentation().addMonitor(activityMonitor);

        Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click());

        SystemClock.sleep(1000);

        String expectedText = String.valueOf(activityMonitor.getResult().getResultCode());
        Espresso.onView(ViewMatchers.withId(R.id.resultCodeTextView))
                                    .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)));
    }
}
