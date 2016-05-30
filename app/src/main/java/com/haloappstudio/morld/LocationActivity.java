package com.haloappstudio.morld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.haloappstudio.morld.adapters.LocationAdapter;
import com.haloappstudio.morld.models.Location;

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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ApiService mApiService;
    private Callback<List<Location>> mCallback;
    private final String TAG = "LocationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.haloappstudio.morld.R.layout.activity_location);
        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences(Utils.MY_PREFS, Context.MODE_PRIVATE);
        final int user_id = prefs.getInt(Utils.USER_ID, -1);
        Log.d(TAG, user_id + "");
        mRecyclerView = (RecyclerView) findViewById(R.id.list_location);
        mLocationList = new ArrayList<>();
        mLocationAdapter = new LocationAdapter(mLocationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mLocationAdapter);
        mLocationAdapter.setListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location location) {
                Log.d("TAG", location.toString());
                Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
                intent.putExtra(Utils.LOCATION, location);
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlocation);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        mLocationList.clear();
                        mApiService.locations(user_id).enqueue(mCallback);
                    }
                }
        );

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Utils.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);

        mCallback = new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                List<Location> locationList = response.body();
                for(Location location : locationList) {
                    mLocationList.add(location);
                }
                mLocationAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG,"Success");

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG,"Fail");
            }
        };

        // Call api to get location list
        mApiService.locations(user_id).enqueue(mCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                Utils.logout(getApplicationContext());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
