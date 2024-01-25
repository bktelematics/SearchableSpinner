package com.bk.searchablespinner;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

abstract class FilterableAdapter<SearchableObject> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //void updateData(ArrayList<T> newData);

    List<SearchableObject> items;

    public void setItems(List<SearchableObject> items) {
        items = this.items;
    }

    public List<SearchableObject> getItems() {
        return items;
    }
}
