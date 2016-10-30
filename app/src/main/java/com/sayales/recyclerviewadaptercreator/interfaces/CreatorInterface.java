package com.sayales.recyclerviewadaptercreator.interfaces;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.sayales.recyclerviewadaptercreator.SwipeAction;

/**
 * Created by Pavel on 30.10.2016.
 */

public interface CreatorInterface<T> {

    /*
    * Add onSwipe action to view, if (action == null) add swipe to dismiss action
    * Footer and header are not swipable
     */
    CreatorInterface<T> onSwipeAction(RecyclerView view, @Nullable SwipeAction action);

    CreatorInterface<T> withFooterViewHolder(Class<? extends RecyclerView.ViewHolder> footerViewHolderClass, @LayoutRes int layoutId);

    CreatorInterface<T> withHeaderViewHolder(Class<? extends RecyclerView.ViewHolder> headerViewHolderClass, @LayoutRes int layoutId);

    Config<T> withViewHolder(Class<? extends RecyclerView.ViewHolder> elementViewHolderClass, @LayoutRes int layoutId);
}
