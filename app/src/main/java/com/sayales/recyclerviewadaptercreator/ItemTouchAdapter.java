package com.sayales.recyclerviewadaptercreator;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Pavel on 26.10.2016.
 */
public abstract class ItemTouchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public  abstract void onItemDismiss(int position);
}
