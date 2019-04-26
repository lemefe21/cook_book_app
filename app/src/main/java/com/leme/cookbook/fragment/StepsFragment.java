package com.leme.cookbook.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.leme.cookbook.R;
import com.leme.cookbook.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    private List<Step> steps;
    private int stepSequence = 0;

    @BindView(R.id.step_position_sequence)
    TextView mStepPositionSequence;

    @BindView(R.id.step_short_description)
    TextView mStepShortDescription;

    @BindView(R.id.step_description)
    TextView mStepDescription;

    @BindView(R.id.step_video_url)
    TextView mStepVideoUrl;

    @BindView(R.id.step_btn_previous)
    Button mStepBtnPrevious;

    @BindView(R.id.step_btn_next)
    Button mStepBtnNext;

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, view);

        Bundle params = getArguments();
        if(params != null) {
            steps = params.getParcelableArrayList(getString(R.string.step_list_key));
        }

        configStepFlow();

        mStepBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepSequence--;
                configStepFlow();
            }
        });

        mStepBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepSequence++;
                configStepFlow();
            }
        });

        return view;
    }

    public void setStepsData(List<Step> steps) {
        this.steps = steps;
    }

    private void configStepFlow() {
        String stepSequenceLabel = String.format(getResources().getString(R.string.baking_step_label), stepSequence);
        mStepPositionSequence.setText(stepSequenceLabel);

        Step step = steps.get(stepSequence);

        mStepShortDescription.setText(step.getShortDescription());
        mStepDescription.setText(step.getDescription());
        mStepVideoUrl.setText(step.getVideoURL());

        if(stepSequence == 0) {
            mStepBtnNext.setVisibility(View.VISIBLE);
            mStepBtnPrevious.setVisibility(View.INVISIBLE);
        } else if(stepSequence == (steps.size() - 1)) {
            mStepBtnNext.setVisibility(View.INVISIBLE);
            mStepBtnPrevious.setVisibility(View.VISIBLE);
        } else {
            mStepBtnNext.setVisibility(View.VISIBLE);
            mStepBtnPrevious.setVisibility(View.VISIBLE);
        }
    }
}
