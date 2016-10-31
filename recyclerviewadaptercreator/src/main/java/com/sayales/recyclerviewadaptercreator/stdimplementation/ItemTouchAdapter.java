package com.sayales.recyclerviewadaptercreator.stdimplementation;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Pavel on 26.10.2016.
 */
abstract class ItemTouchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public  abstract void onItemDismiss(int position);
}
