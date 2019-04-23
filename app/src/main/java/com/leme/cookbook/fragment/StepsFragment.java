package com.leme.cookbook.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leme.cookbook.R;
import com.leme.cookbook.model.Step;

import java.util.List;

public class StepsFragment extends Fragment {


    public StepsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    public void setStepsData(List<Step> steps) {

    }
}
