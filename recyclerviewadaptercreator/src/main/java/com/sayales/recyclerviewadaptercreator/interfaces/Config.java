package com.sayales.recyclerviewadaptercreator.interfaces;

import com.sayales.recyclerviewadaptercreator.ViewHolderBinder;

/**
 * Created by Pavel on 30.10.2016.
 */

public interface Config<T>  {

    /*
    * Return configured creator where binder.bind(holder, position, configured) calls in onBindViewHolder() of returned adapter;
     */
    Configured<T> withViewHolderBinder(ViewHolderBinder<T> binder);
}
