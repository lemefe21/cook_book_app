package com.leme.cookbook;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.leme.cookbook.fragment.DetailFragment;
import com.leme.cookbook.fragment.StepsFragment;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.model.Step;
import com.leme.cookbook.provider.BakingWidgetProvider;

import java.util.ArrayList;
import java.util.List;

public class BakingDetailActivity extends AppCompatActivity {

    private Baking baking;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_detail);

        Intent intent = getIntent();
        Baking bakingExtra = getIntent().getParcelableExtra(BakingWidgetProvider.FILTER_BAKING_ITEM);

        if(savedInstanceState == null) {
            if(getIntent().hasExtra("baking_selected")) {
                baking = intent.getExtras().getParcelable("baking_selected");
            } else if(getIntent() != null && bakingExtra != null) {
                baking = bakingExtra;
                setIntent(null);
            }

            detailFragment = new DetailFragment();
            detailFragment.setBakingData(baking);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction tx = fragmentManager.beginTransaction();
            tx.replace(R.id.detail_fragment, detailFragment);

            if(isTabletScreen()) {
                tx.replace(R.id.steps_fragment, new StepsFragment());
            }

            tx.commit();
        } else {
            Toast.makeText(this, "savedInstanceState", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isTabletScreen() {
        return getResources().getBoolean(R.bool.tabletScreen);
    }

    public void getStepsBakingClick(List<Step> steps) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(!isTabletScreen()) {
            FragmentTransaction tx = fragmentManager.beginTransaction();
            StepsFragment stepFragment = new StepsFragment();
            Bundle params = new Bundle();
            params.putParcelableArrayList(getString(R.string.step_list_key), new ArrayList<Parcelable>(steps));
            stepFragment.setArguments(params);

            tx.replace(R.id.detail_fragment, stepFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            StepsFragment stepsFragment =
                    (StepsFragment) fragmentManager.findFragmentById(R.id.steps_fragment);

            stepsFragment.setStepsData(steps);
        }

    }

}
