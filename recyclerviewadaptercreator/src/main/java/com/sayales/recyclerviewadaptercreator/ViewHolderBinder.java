package com.sayales.recyclerviewadaptercreator;

import android.support.v7.widget.RecyclerView;

import com.sayales.recyclerviewadaptercreator.interfaces.Configured;

/**
 * Created by Pavel on 26.10.2016.
 */
public interface ViewHolderBinder<T> {
    void bind(RecyclerView.ViewHolder holder, int position, Configured<T> data);
}
