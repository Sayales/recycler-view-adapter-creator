package com.sayales.recyclerviewadaptercreator.stdimplementation;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Pavel on 28.09.2016.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private final ItemTouchAdapter adapter;

    public MyItemTouchHelperCallback(ItemTouchAdapter adapter) {
        super(ItemTouchHelper.UP, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }



    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == RecyclerViewAdapterCreator.HolderTypes.FOOTER_TYPE ||
                viewHolder.getItemViewType() == RecyclerViewAdapterCreator.HolderTypes.HEADER_TYPE)
            return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}

