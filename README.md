# recycler-view-adapter-creator
A little lib for creating simple adapters for android RecyclerView

Demonstration of usage: https://github.com/Sayales/creator-demo

# Installation
Add recycleradaptercreator.aar as .aar module to your project

# About

Minimal configuration for creating adapter: 
```java
    List<String> data = new ArrayList<>();
        RecyclerView.Adapter adapter = RecyclerViewAdapterCreator.withData(data)
                .withViewHolder(MyViewHolder.class, R.layout.holder_layout)
                .withViewHolderBinder(new ViewHolderBinder<String>() {
                    @Override
                    public void bind(RecyclerView.ViewHolder holder, int position, Configured<String> data) {
                        MyViewHolder trueHolder = (MyViewHolder) holder;
                        trueHolder.getText().setText(data.getData().get(i));
                    }
                })
                .create();
```
You can add more config methods between *RecyclerViewAdapterCreator.withData(data)* and *.withViewHolder(MyViewHolder.class, R.layout.holder_layout)*. All avalible methods can be found here: https://github.com/Sayales/recycler-view-adapter-creator/tree/master/recyclerviewadaptercreator/src/main/java/com/sayales/recyclerviewadaptercreator/interfaces
