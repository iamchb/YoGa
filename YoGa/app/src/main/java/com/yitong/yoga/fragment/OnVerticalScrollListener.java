package com.yitong.yoga.fragment;

import android.support.v7.widget.RecyclerView;

/**
 * Created by chb on 2017/2/14
 */

public abstract class OnVerticalScrollListener
        extends RecyclerView.OnScrollListener {


    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        } else if (dy < 0) {
            onScrolledUp();
        } else if (dy > 0) {
            onScrolledDown();
        }
    }

    public  int  onScrolledUp() {

        return  3;
    }

    public int  onScrolledDown() {

        return  2;
    }

    public int  onScrolledToTop() {

        return -1;
    }

    public int onScrolledToBottom() {
        return  1;
    }
}