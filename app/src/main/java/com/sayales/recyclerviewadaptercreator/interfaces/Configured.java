package com.sayales.recyclerviewadaptercreator.interfaces;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

/**
 * Created by Pavel on 30.10.2016.
 */

public interface Configured<T> {

    /*
    * Creates configured adapter
    * */
    RecyclerView.Adapter<RecyclerView.ViewHolder> create();

    /*
    * Returns your recycler view`s footer, if recycler view without footer then returns null
    * */
    RecyclerView.ViewHolder getFooter();

    /*
    * Returns your recycler view`s header, if recycler view without header then returns null
    * */
    RecyclerView.ViewHolder getHeader();

    /*
    * Returns adapter`s  dataset
    * */
    List<T> getData();
}
