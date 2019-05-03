package com.leme.cookbook.api;

import com.leme.cookbook.model.Baking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("baking.json")
    Call<List<Baking>> getBakingData();

}
