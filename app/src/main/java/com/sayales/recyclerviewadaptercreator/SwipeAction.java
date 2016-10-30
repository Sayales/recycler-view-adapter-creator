package com.sayales.recyclerviewadaptercreator;

import android.support.v7.widget.RecyclerView;

import com.sayales.recyclerviewadaptercreator.interfaces.Configured;

/**
 * Created by Pavel on 30.10.2016.
 */
public interface SwipeAction {
    /*
    * Action witch would be called on item swipe
    * */
    void action(int position, Configured data, RecyclerView.Adapter adapter);
}
