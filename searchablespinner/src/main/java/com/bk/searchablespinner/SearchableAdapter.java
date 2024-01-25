package com.bk.searchablespinner;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public abstract class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public abstract List<SearchableObject> getItems();

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SearchableObject searchableObject = getItems().get(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(searchableObject, holder.getAdapterPosition());
                }
            }
        });
    }
    }
    //    public void updateData(List<SearchableObject> newData) {
//        // Customize this based on your needs
//        //DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(items, newData));
//        items.clear();
//        items.addAll(newData);
//        this.setItems(items);
//       // diffResult.dispatchUpdatesTo(this);
//    }

