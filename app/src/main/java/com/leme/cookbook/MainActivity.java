package com.leme.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leme.cookbook.adapter.BakingItemAdapter;
import com.leme.cookbook.api.ApiInterface;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.util.ReadJsonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.leme.cookbook.api.ApiClientSingleton.API_CLIENT_INSTANCE;

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

        ApiInterface api = API_CLIENT_INSTANCE.getClient().create(ApiInterface.class);
        Call<List<Baking>> callRequest = api.getBakingData();
        callRequest.enqueue(new Callback<List<Baking>>() {
            @Override
            public void onResponse(Call<List<Baking>> call, Response<List<Baking>> response) {
                List<Baking> responses = response.body();
                mBakingItemAdapter.setBakingData(responses);
            }

            @Override
            public void onFailure(Call<List<Baking>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Request error...", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mBakingItemAdapter);
    }

    @Override
    public void onClick(Baking baking) {
        Intent intent = new Intent(this, BakingDetailActivity.class);
        intent.putExtra(getString(R.string.baking_selected), baking);
        startActivity(intent);
    }
}
