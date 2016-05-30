package com.haloappstudio.morld;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.haloappstudio.morld.adapters.DeviceAdapter;
import com.haloappstudio.morld.models.Device;
import com.haloappstudio.morld.models.Location;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceActivity extends AppCompatActivity {

    private Retrofit mRetrofit;
    private ArrayList<Device> mDeviceList;
    private RecyclerView mRecyclerView;
    private DeviceAdapter mDeviceAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ApiService mApiService;
    private Callback<List<Device>> mCallback;
    private final String TAG = "DeviceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        setContentView(com.haloappstudio.morld.R.layout.activity_device);
        Bundle bundle = getIntent().getExtras();
        final Location location = bundle.getParcelable(Utils.LOCATION);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_device);
        mDeviceList = new ArrayList<>();
        mDeviceAdapter = new DeviceAdapter(mDeviceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mDeviceAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshdevice);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        mDeviceList.clear();
                        mApiService.devices(location.getmId()).enqueue(mCallback);
                    }
                }
        );

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Utils.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
        mCallback = new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                List<Device> deviceList = response.body();
                for(Device Device : deviceList) {
                    mDeviceList.add(Device);
                }
                mDeviceAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG,"Success");

            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG,"Fail");
            }
        };

        // Call api to get location list
        mApiService.devices(location.getmId()).enqueue(mCallback);
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
