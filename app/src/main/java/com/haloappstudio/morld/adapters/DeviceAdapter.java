package com.haloappstudio.morld.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.haloappstudio.morld.ApiService;
import com.haloappstudio.morld.R;
import com.haloappstudio.morld.Utils;
import com.haloappstudio.morld.models.Device;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by suheb on 27/5/16.
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private Switch mSwitch;

        public TextView getmTextView() {
            return mTextView;
        }

        public void setmTextView(TextView mTextView) {
            this.mTextView = mTextView;
        }

        public Switch getmSwitch() {
            return mSwitch;
        }

        public void setmSwitch(Switch mSwitch) {
            this.mSwitch = mSwitch;
        }

        public ViewHolder(View view) {
            super(view);
        }

    }

    private ArrayList<Device> mDataset;
    private final String TAG = "DeviceAdapter";

    public DeviceAdapter(ArrayList<Device> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        TextView textView = (TextView) view.findViewById(R.id.device_name);
        viewHolder.setmTextView(textView);
        Switch aSwitch = (Switch) view.findViewById(R.id.device_switch);
        viewHolder.setmSwitch(aSwitch);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Device device = mDataset.get(position);
        holder.getmTextView().setText(device.getmName());
        holder.getmSwitch().setChecked(device.getmStatus());
        holder.getmSwitch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Adapter","CLICKED");
                Integer status = 0;
                if(holder.getmSwitch().isChecked()) {
                    status = 1;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Utils.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                apiService.set_status(device.getmId(),status).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(TAG,response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "FAIL");
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}