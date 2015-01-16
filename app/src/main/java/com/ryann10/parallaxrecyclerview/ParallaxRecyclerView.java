package com.ryann10.parallaxrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ParallaxRecyclerView extends RecyclerView {

    private int parallaxResourceId;
    private OnScrollListener delegateOnScrollListener;
    private OnHierarchyChangeListener delegateOnHierarchyChangeListener;

    final OnScrollListener PARALLAX_SCROLLER = new OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            updateParallaxOffset();

            if(delegateOnScrollListener != null)
                delegateOnScrollListener.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged (RecyclerView recyclerView, int newState){
            if(delegateOnScrollListener != null)
                delegateOnScrollListener.onScrollStateChanged(recyclerView, newState);
        }
    };

    final OnHierarchyChangeListener HIERARCHY_LISTENER = new OnHierarchyChangeListener() {
        @Override
        public void onChildViewAdded(View view, View view2) {
            updateParallaxOffset();

            if(delegateOnHierarchyChangeListener != null)
                delegateOnHierarchyChangeListener.onChildViewAdded(view, view2);
        }

        @Override
        public void onChildViewRemoved(View view, View view2) {
            if(delegateOnHierarchyChangeListener != null)
                delegateOnHierarchyChangeListener.onChildViewRemoved(view, view2);
        }
    };

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
        super.setOnScrollListener(PARALLAX_SCROLLER);
        super.setOnHierarchyChangeListener(HIERARCHY_LISTENER);
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        delegateOnScrollListener = listener;
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        this.delegateOnHierarchyChangeListener = listener;
    }

    public void setParallaxResourceId(int parallaxResourceId) {
        this.parallaxResourceId = parallaxResourceId;
    }

    public void updateParallaxOffset(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int last = layoutManager.getChildCount();
        int getLastItemBottom = 0;

        for(int position = 0; position < last; position++){
            View view = getChildAt(position);
            if(view != null) {
                ParallaxImageView parallaxImageView = (ParallaxImageView) view.findViewById(parallaxResourceId);
                if(position == last - 2) {
                    getLastItemBottom = view.getBottom();
                }

                if(position == last - 1) {
                    parallaxImageView.setParallaxOffset(getLastItemBottom);
                } else {
                    parallaxImageView.setParallaxOffset(view.getTop());
                }
            }
        }
    }
}
