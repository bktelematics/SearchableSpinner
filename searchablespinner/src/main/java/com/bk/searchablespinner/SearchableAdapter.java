package com.bk.searchablespinner;


import static com.bk.searchablespinner.SearchableListDialog.HideKeyboard;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public abstract class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private OnItemClickListener onItemClickListener;
    private List<SearchableObject> originalList;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public abstract List<SearchableObject> getItems();

    public SearchableAdapter(List<SearchableObject> originalList) {
        this.originalList = originalList;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        SearchableObject searchableObject = getItems().get(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    HideKeyboard(view);
                    onItemClickListener.onItemClick(searchableObject, holder.getAdapterPosition());
                }
            }
        });
    }

    public void updateData(List<SearchableObject> filteredData) {
        List<SearchableObject> newItems = new ArrayList<>(filteredData);  // Create a new list
        setData(newItems);
    }

    public void setData(List<SearchableObject> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SearchableDiffCallback(originalList, newData));
        originalList.clear();
        originalList.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }
}
