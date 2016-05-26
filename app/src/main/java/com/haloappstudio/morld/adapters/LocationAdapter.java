package com.haloappstudio.morld.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haloappstudio.morld.R;
import com.haloappstudio.morld.models.Location;

import java.util.ArrayList;


/**
 * Created by suheb on 27/5/16.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private ArrayList<Location> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public TextView getmTextView() {
            return mTextView;
        }

        public void setmTextView(TextView mTextView) {
            this.mTextView = mTextView;
        }

        public ViewHolder(View view) {
            super(view);
        }
    }

    public LocationAdapter(ArrayList<Location> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setmTextView((TextView) view.findViewById(R.id.location_name));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Location location = mDataset.get(position);
        holder.getmTextView().setText(location.getmName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}