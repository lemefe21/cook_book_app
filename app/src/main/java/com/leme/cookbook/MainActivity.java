package com.leme.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leme.cookbook.adapter.BakingItemAdapter;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.util.ReadJsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BakingItemAdapter.BakingItemAdapterOnClickHandler {

    @BindView(R.id.recyclerview_baking_list)
    RecyclerView mRecyclerView;

    private BakingItemAdapter mBakingItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mBakingItemAdapter = new BakingItemAdapter(this, this);
        mBakingItemAdapter.setBakingData(ReadJsonUtil.loadJSONFromObject(this));
        mRecyclerView.setAdapter(mBakingItemAdapter);

    }

    @Override
    public void onClick(Baking baking) {
        Toast.makeText(this, "Baking id: " + baking.getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BakingDetailActivity.class);
        intent.putExtra("baking_selected", baking);
        startActivity(intent);
    }
}
