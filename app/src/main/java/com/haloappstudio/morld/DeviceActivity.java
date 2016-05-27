package com.haloappstudio.morld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    private final String TAG = "DeviceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        setContentView(com.haloappstudio.morld.R.layout.activity_device);
        Bundle bundle = getIntent().getExtras();
        Location location = bundle.getParcelable(Utils.LOCATION);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_device);
        mDeviceList = new ArrayList<>();
        mDeviceAdapter = new DeviceAdapter(mDeviceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mDeviceAdapter);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Utils.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = mRetrofit.create(ApiService.class);

        apiService.devices(location.getmId()).enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                List<Device> DeviceList = response.body();
                for(Device Device : DeviceList) {
                    mDeviceList.add(Device);
                }
                mDeviceAdapter.notifyDataSetChanged();
                Log.d(TAG,"Success");

            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Log.d(TAG,"Fail");
            }
        });
    }
}
