package com.bk.searchablespinner;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public abstract class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    private OnItemClickListener onItemClickListener;
    List<SearchableObject> originalList ;
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

    public void updateData(List<Object> filteredData) {
        List<SearchableObject> newItems = new ArrayList<>();
        for (Object item : filteredData) {
                newItems.add((SearchableObject) item);
        }
        originalList = this.getItems();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SearchableDiffCallback(originalList, newItems));
        originalList = newItems; // Update the originalList
        diffResult.dispatchUpdatesTo(this);
    }
    }

