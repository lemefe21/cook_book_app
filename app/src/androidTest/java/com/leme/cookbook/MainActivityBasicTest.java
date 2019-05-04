package com.leme.cookbook;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leme.cookbook.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.INVISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void clickListViewItem_OpensBakingDetailActivity() {
        onView(withId(R.id.recyclerview_baking_list)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_fragment_name)).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.detail_btn_previous)).check(matches(withEffectiveVisibility(INVISIBLE)));
    }

    @Test
    public void navigationFlowAt_BakingDetailActivity() {
        onView(withId(R.id.recyclerview_baking_list)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_fragment_name)).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.detail_btn_next)).perform(click());
        onView(withId(R.id.detail_fragment_name)).check(matches(withText("Brownies")));
        onView(withId(R.id.detail_btn_previous)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.detail_btn_next)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.detail_btn_next)).perform(click());
        onView(withId(R.id.detail_fragment_name)).check(matches(withText("Yellow Cake")));
        onView(withId(R.id.detail_btn_previous)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.detail_btn_next)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.detail_btn_next)).perform(click());
        onView(withId(R.id.detail_fragment_name)).check(matches(withText("Cheesecake")));
        onView(withId(R.id.detail_btn_previous)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.detail_btn_next)).check(matches(withEffectiveVisibility(INVISIBLE)));
    }

    @Test
    public void navigationFlowAt_BakingDetailToStepFragment() {
        onView(withId(R.id.recyclerview_baking_list)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_fragment_name)).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.detail_fragment_button_to_steps)).perform(click());
        onView(withId(R.id.step_btn_previous)).check(matches(withEffectiveVisibility(INVISIBLE)));
        onView(withId(R.id.step_btn_next)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.step_short_description)).check(matches(withText("Recipe Introduction")));
        onView(withId(R.id.step_description)).check(matches(withText("Recipe Introduction")));
        onView(withId(R.id.step_btn_next)).perform(click());
        onView(withId(R.id.step_btn_previous)).check(matches(withEffectiveVisibility(VISIBLE)));
        onView(withId(R.id.step_short_description)).check(matches(withText("Starting prep")));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}
