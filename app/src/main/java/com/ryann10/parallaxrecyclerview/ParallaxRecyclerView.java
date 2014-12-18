package com.ryann10.parallaxrecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Ryan on 2014-12-17.
 */
public class ParallaxRecyclerView extends RecyclerView {
    final OnScrollListener PARALLAX_SCROLLER = new OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //updateParallaxOffset();
        }
    };
    private int parallaxResourceId;

    public ParallaxRecyclerView(Context context) {
        super(context);
        init();
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnScrollListener(PARALLAX_SCROLLER);
    }

    public void setParallaxResourceId(int parallaxResourceId) {
        this.parallaxResourceId = parallaxResourceId;
    }

    public void updateParallaxOffset(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int last = layoutManager.findLastVisibleItemPosition();
        for(int position = 0; position <= last; position++){
            View view = layoutManager.getChildAt(position);
            if(view != null) {
                ParallaxImageView parallaxImageView = (ParallaxImageView) view.findViewById(parallaxResourceId);
                parallaxImageView.setParallaxOffset(view.getTop());
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        updateParallaxOffset();
        super.onDraw(canvas);
    }
}
