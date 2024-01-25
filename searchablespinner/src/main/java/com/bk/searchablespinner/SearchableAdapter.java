package com.bk.searchablespinner;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public abstract class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    //void updateData(ArrayList<T> newData);

    public abstract List<SearchableObject> getItems();

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SearchableObject searchableObject= getItems().get(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(v -> {
            searchableObject.onSearchableItemClicked();
        });
    }

    //    public void updateData(List<SearchableObject> newData) {
//        // Customize this based on your needs
//        //DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(items, newData));
//        items.clear();
//        items.addAll(newData);
//        this.setItems(items);
//       // diffResult.dispatchUpdatesTo(this);
//    }
}
