package com.leme.cookbook.fragment;


import android.os.Bundle;
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
import com.leme.cookbook.service.BakingIngredientsService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    private Baking baking;
    private IngredientItemAdapter mIngredientItemAdapter;

    @BindView(R.id.detail_fragment_recyclerview_ingredients)
    RecyclerView mRecyclerView;

    @BindView(R.id.detail_fragment_name)
    TextView mDetailBakingName;

    @BindView(R.id.detail_fragment_button_to_steps)
    Button mDetailButtonToSteps;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);
        setDetailsInFragment(baking);

        mDetailButtonToSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSteps();
            }
        });

        BakingIngredientsService.startActionUpdateBakingWidgets(getContext(), baking.getIngredients());

        return view;
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

    public void setBakingData(Baking baking) {
        this.baking = baking;
    }
}
