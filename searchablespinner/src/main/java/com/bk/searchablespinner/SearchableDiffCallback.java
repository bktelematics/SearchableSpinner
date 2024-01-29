package com.bk.searchablespinner;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class SearchableDiffCallback extends DiffUtil.Callback {
    private List<?> oldList;
    private List<?> newList;

    public SearchableDiffCallback(List<?> oldList, List<?> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Object oldItem = oldList.get(oldItemPosition);
        Object newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }

@Override
public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    Object oldItem = oldList.get(oldItemPosition);
    Object newItem = newList.get(newItemPosition);
    return oldItem.equals(newItem);
}

}