package com.leme.cookbook.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.leme.cookbook.BakingDetailActivity;
import com.leme.cookbook.R;
import com.leme.cookbook.adapter.IngredientItemAdapter;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.model.Step;
import com.leme.cookbook.service.BakingIngredientsService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    private static final String LIST_STATE = "LIST_STATE";

    private Baking baking;
    private List<Baking> bakings;
    private int bakingIndex;
    private IngredientItemAdapter mIngredientItemAdapter;

    @BindView(R.id.detail_fragment_recyclerview_ingredients)
    RecyclerView mRecyclerView;

    @BindView(R.id.detail_fragment_name)
    TextView mDetailBakingName;

    @BindView(R.id.detail_fragment_button_to_steps)
    Button mDetailButtonToSteps;

    @BindView(R.id.detail_btn_next)
    Button mDetailButtonToNext;

    @BindView(R.id.detail_btn_previous)
    Button mDetailButtonToPrevious;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            bakings = savedInstanceState.getParcelableArrayList(LIST_STATE);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);
        configDetailFlow();

        mDetailButtonToSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSteps();
            }
        });

        mDetailButtonToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bakingIndex++;
                configDetailFlow();
            }
        });

        mDetailButtonToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bakingIndex--;
                configDetailFlow();
            }
        });

        return view;
    }

    private void configDetailFlow() {
        baking = bakings.get(bakingIndex);
        setDetailsInFragment(baking);

        BakingIngredientsService.startActionUpdateBakingWidgets(getContext(), baking.getIngredients());

        if(bakingIndex == 0) {
            mDetailButtonToNext.setVisibility(View.VISIBLE);
            mDetailButtonToPrevious.setVisibility(View.INVISIBLE);
        } else if(bakingIndex == (bakings.size() - 1)) {
            mDetailButtonToNext.setVisibility(View.INVISIBLE);
            mDetailButtonToPrevious.setVisibility(View.VISIBLE);
        } else {
            mDetailButtonToNext.setVisibility(View.VISIBLE);
            mDetailButtonToPrevious.setVisibility(View.VISIBLE);
        }
    }

    private void onClickSteps() {
        BakingDetailActivity activity = (BakingDetailActivity) getActivity();
        activity.getStepsBakingClick(baking.getSteps());
    }

    private void setDetailsInFragment(Baking baking) {

        mDetailBakingName.setText(baking.getName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mIngredientItemAdapter = new IngredientItemAdapter(getContext());
        mIngredientItemAdapter.setIngredientData(baking.getIngredients());
        mRecyclerView.setAdapter(mIngredientItemAdapter);

    }

    public void setBakingData(List<Baking> bakings, int index) {
        this.bakings = bakings;
        this.bakingIndex = index;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE, new ArrayList<Parcelable>(bakings));
    }
}
