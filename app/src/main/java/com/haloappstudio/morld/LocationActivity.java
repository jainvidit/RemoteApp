package com.haloappstudio.morld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.haloappstudio.morld.adapters.LocationAdapter;
import com.haloappstudio.morld.models.Location;
import com.haloappstudio.morld.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationActivity extends AppCompatActivity {

    private Retrofit mRetrofit;
    private ArrayList<Location> mLocationList;
    private RecyclerView mRecyclerView;
    private LocationAdapter mLocationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.haloappstudio.morld.R.layout.activity_location);
        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable(Utils.USER);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_location);
        mLocationList = new ArrayList<>();
        mLocationAdapter = new LocationAdapter(mLocationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mLocationAdapter);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Utils.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = mRetrofit.create(ApiService.class);

        apiService.locations(user.getmId()).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                List<Location> locationList = response.body();
                for(Location location : locationList) {
                    mLocationList.add(location);
                }
                mLocationAdapter.notifyDataSetChanged();
                Log.d("TAG","Success");

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.d("TAG","Fail");
            }
        });
    }
}
