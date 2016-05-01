package com.example.crux;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.crux.database.CartDao;
import com.example.crux.ui.HomeActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author gauravarora
 * @since 01/05/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class VerifyCartTest {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Test
    public void verifyCart() {
        //clear cart
        new CartDao().deleteAll();

        onView(withText("Tools")).perform(click());

        //wait till list is downloaded
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(3 * 1000);
        registerIdlingResources(idlingResource);

        //select item of recyclerview
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        String productTitle = getText(withId(R.id.title));
        onView(withId(R.id.button_cart)).perform(click());

        pressBack();
        pressBack();

        Log.d("Product Title", productTitle);

        onView(withId(R.id.title)).check(matches(withText(productTitle)));
    }

    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view;
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

}
