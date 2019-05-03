package com.leme.cookbook;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leme.cookbook.adapter.BakingItemAdapter;
import com.leme.cookbook.api.ApiInterface;
import com.leme.cookbook.model.Baking;
import com.leme.cookbook.util.ReadJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.leme.cookbook.api.ApiClientSingleton.API_CLIENT_INSTANCE;

public class MainActivity extends AppCompatActivity implements BakingItemAdapter.BakingItemAdapterOnClickHandler {

    public static final String BAKING_INDEX = "baking_index";
    public static final String BAKING_LIST = "baking_list";
    public static final String BAKING_SELECTED = "baking_selected";

    private BakingItemAdapter mBakingItemAdapter;
    private List<Baking> bakings;

    @BindView(R.id.recyclerview_baking_list)
    RecyclerView mRecyclerView;

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
                bakings = response.body();
                mBakingItemAdapter.setBakingData(bakings);
            }

            @Override
            public void onFailure(Call<List<Baking>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Request error...", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mBakingItemAdapter);
    }

    @Override
    public void onClick(Baking baking, int index) {
        Intent intent = new Intent(this, BakingDetailActivity.class);
        intent.putExtra(BAKING_SELECTED, baking);
        intent.putParcelableArrayListExtra(BAKING_LIST, new ArrayList<Parcelable>(bakings));
        intent.putExtra(BAKING_INDEX, index);
        startActivity(intent);
    }
}
