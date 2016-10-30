package com.sayales.recyclerviewadaptercreator.stdimplementation;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sayales.recyclerviewadaptercreator.SwipeAction;
import com.sayales.recyclerviewadaptercreator.ViewHolderBinder;
import com.sayales.recyclerviewadaptercreator.interfaces.CreatorInterface;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by Pavel on 25.10.2016.
 */
public class RecyclerViewAdapterCreator<T> implements CreatorInterface<T> {


    private List<T> data;
    private RecyclerView view;
    private Config config = new Config();

    private RecyclerViewAdapterCreator(List<T> data) {
        this.data = data;
    }

    public static <T> CreatorInterface<T> create(List<T> data) {
        return new RecyclerViewAdapterCreator<>(data);
    }


    @Override
    public CreatorInterface<T> onSwipeAction(RecyclerView view, @Nullable SwipeAction action) {
        config.swipable = true;
        config.action = action;
        this.view = view;
        return this;
    }

    @Override
    public CreatorInterface<T> withFooterViewHolder(Class<? extends RecyclerView.ViewHolder> clazz, @LayoutRes int layoutId) {
        Constructor<? extends RecyclerView.ViewHolder> ctor = null;
        try {
            ctor = clazz.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.config.footerHolderConstructor = ctor;
        this.config.footerHolderLayoutId = layoutId;
        this.config.withFooter = true;
        return this;
    }

    @Override
    public CreatorInterface<T> withHeaderViewHolder(Class<? extends RecyclerView.ViewHolder> clazz, @LayoutRes int layoutId) {
        Constructor<? extends RecyclerView.ViewHolder> ctor = null;
        try {
            ctor = clazz.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.config.headerHolderConstructor = ctor;
        this.config.headerHolderLayoutId = layoutId;
        this.config.withHeader = true;
        return this;
    }

    @Override
    public com.sayales.recyclerviewadaptercreator.interfaces.Config<T> withViewHolder(Class<? extends RecyclerView.ViewHolder> clazz, @LayoutRes int layoutId) {
        Constructor<? extends RecyclerView.ViewHolder> ctor = null;
        try {
            ctor = clazz.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.config.normalHolderConstructor = ctor;
        this.config.normalHolderLayoutId = layoutId;
        return config;
    }


    public class Configured implements com.sayales.recyclerviewadaptercreator.interfaces.Configured<T> {

        private RecyclerView.ViewHolder footer;
        private RecyclerView.ViewHolder header;

        ViewHolderBinder<T> binder;
        Config config;

        Configured(ViewHolderBinder<T> binder, Config config) {
            this.binder = binder;
            this.config = config;
        }

        public RecyclerView.Adapter<RecyclerView.ViewHolder> create() {
            ItemTouchAdapter result = getExceptedAdapter();
            if (config.swipable) {
                ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(result);
                ItemTouchHelper helper = new ItemTouchHelper(callback);
                helper.attachToRecyclerView(view);
            }
            return result;
        }

        public RecyclerView.ViewHolder getFooter() {
            return footer;
        }

        public RecyclerView.ViewHolder getHeader() {
            return header;
        }

        public List<T> getData() {
            return data;
        }

        private ItemTouchAdapter getExceptedAdapter() {
            if (config.withFooter && config.withHeader)
                return adapterWithHeaderAndFooter();
            if (config.withFooter)
                return adapterWithFooter();
            if (config.withHeader)
                return adapterWithHeader();
            else
                return simpleAdapter();
        }

        private ItemTouchAdapter simpleAdapter() {
            return new ItemTouchAdapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return config.getNormalViewHolder(parent);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    binder.bind(holder, position, Configured.this);
                }

                @Override
                public int getItemCount() {
                    return data.size();
                }

                @Override
                public void onItemDismiss(int position) {
                    data.remove(position);
                    notifyItemRemoved(position);
                }

                @Override
                public int getItemViewType(int position) {
                    return super.getItemViewType(position);
                }
            };
        }

        private ItemTouchAdapter adapterWithFooter() {
            return new ItemTouchAdapter() {

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == HolderTypes.FOOTER_TYPE) {
                        footer = config.getFooterViewHolder(parent);
                        return footer;
                    }
                    return config.getNormalViewHolder(parent);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    binder.bind(holder, position, Configured.this);
                }

                @Override
                public int getItemCount() {
                    if (data == null)
                        return 0;
                    return data.size() + 1;
                }

                @Override
                public int getItemViewType(int position) {
                    if (position == data.size()) {
                        return HolderTypes.FOOTER_TYPE;
                    }
                    return super.getItemViewType(position);
                }

                @Override
                public void onItemDismiss(int position) {
                    if (config.action != null) {
                        config.action.action(position, Configured.this, this);
                    } else {
                        data.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            };
        }

        private ItemTouchAdapter adapterWithHeader() {
            return new ItemTouchAdapter() {


                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == HolderTypes.HEADER_TYPE) {
                        header = config.getHeaderViewHolder(parent);
                        return header;
                    }
                    return config.getNormalViewHolder(parent);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    binder.bind(holder, position, Configured.this);
                }

                @Override
                public int getItemCount() {
                    if (data == null)
                        return 0;
                    return data.size() + 1;
                }

                @Override
                public int getItemViewType(int position) {
                    if (position == 0) {
                        return HolderTypes.HEADER_TYPE;
                    }
                    return super.getItemViewType(position);
                }

                @Override
                public void onItemDismiss(int position) {
                    if (config.action != null) {
                        config.action.action(position, Configured.this, this);
                    } else {
                        data.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            };
        }

        private ItemTouchAdapter adapterWithHeaderAndFooter() {
            return new ItemTouchAdapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == HolderTypes.HEADER_TYPE) {
                        header = config.getHeaderViewHolder(parent);
                        return header;
                    }
                    if (viewType == HolderTypes.FOOTER_TYPE) {
                        footer = config.getFooterViewHolder(parent);
                        return footer;
                    }
                    return config.getNormalViewHolder(parent);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    binder.bind(holder, position, Configured.this);
                }

                @Override
                public int getItemCount() {
                    if (data == null)
                        return 0;
                    return data.size() + 2;
                }

                @Override
                public int getItemViewType(int position) {
                    if (position == 0) {
                        return HolderTypes.HEADER_TYPE;
                    }
                    if (position == data.size() + 1) {
                        return HolderTypes.FOOTER_TYPE;
                    }
                    return super.getItemViewType(position);
                }

                @Override
                public void onItemDismiss(int position) {
                    if (config.action != null) {
                        config.action.action(position, Configured.this, this);
                    } else {
                        data.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            };
        }
    }

    public class Config implements com.sayales.recyclerviewadaptercreator.interfaces.Config<T> {

        boolean swipable = false;

        boolean withFooter = false;

        boolean withHeader = false;

        Constructor<? extends RecyclerView.ViewHolder> normalHolderConstructor;

        int normalHolderLayoutId;

        Constructor<? extends RecyclerView.ViewHolder> footerHolderConstructor;

        int footerHolderLayoutId;

        Constructor<? extends RecyclerView.ViewHolder> headerHolderConstructor;

        int headerHolderLayoutId;

        SwipeAction action;

        @Override
        public com.sayales.recyclerviewadaptercreator.interfaces.Configured<T> withViewHolderBinder(ViewHolderBinder<T> binder) {
            return new Configured(binder, this);
        }

        RecyclerView.ViewHolder getNormalViewHolder(ViewGroup parent) {
            try {
                return normalHolderConstructor.newInstance(LayoutInflater.from(parent.getContext()).inflate(normalHolderLayoutId, parent, false));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent) {
            try {
                return footerHolderConstructor.newInstance(LayoutInflater.from(parent.getContext()).inflate(footerHolderLayoutId, parent, false));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent) {
            try {
                return headerHolderConstructor.newInstance(LayoutInflater.from(parent.getContext()).inflate(headerHolderLayoutId, parent, false));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class HolderTypes {
        public final static int FOOTER_TYPE = 1;
        public final static int HEADER_TYPE = 2;
    }
}
