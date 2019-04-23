package com.leme.cookbook;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leme.cookbook.fragment.DetailFragment;
import com.leme.cookbook.fragment.StepsFragment;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.model.Step;

import java.util.List;

public class BakingDetailActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Baking baking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_detail);

        if(findViewById(R.id.main_fragment_linear_layout) != null) {
            mTwoPane = true;

            if(savedInstanceState == null) {

                DetailFragment detailFragment = new DetailFragment();

                Intent intent = getIntent();
                if(getIntent().hasExtra("baking_selected")) {
                    baking = intent.getExtras().getParcelable("baking_selected");
                    detailFragment.setBakingData(baking);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction tx = fragmentManager.beginTransaction();
                tx.replace(R.id.detail_fragment, detailFragment);
                tx.replace(R.id.steps_fragment, new StepsFragment());
                tx.commit();

            }

        } else {
            mTwoPane = false;
        }
    }

    public void getStepsBakingClick(List<Step> steps) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepsFragment stepsFragment =
                (StepsFragment) fragmentManager.findFragmentById(R.id.steps_fragment);

        stepsFragment.setStepsData(steps);
    }

}
