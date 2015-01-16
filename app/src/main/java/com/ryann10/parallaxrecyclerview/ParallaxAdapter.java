package com.ryann10.parallaxrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ParallaxAdapter extends RecyclerView.Adapter<ParallaxAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgParallax;
        public ViewHolder(View v) {
            super(v);
            imgParallax = (ImageView) v.findViewById(R.id.img_parallax);
        }
    }

    public ParallaxAdapter() {
    }

    @Override
    public ParallaxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}