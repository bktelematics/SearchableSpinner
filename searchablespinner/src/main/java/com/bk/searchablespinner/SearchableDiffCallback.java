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

        // Compare items based on their identity
        return oldItem.equals(newItem);
    }
//    @Override
//    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        Object oldItem = oldList.get(oldItemPosition);
//        Object newItem = newList.get(newItemPosition);
//
//        if (oldItem instanceof SearchableObject && newItem instanceof SearchableObject) {
//            // Assuming toSearchableString() method is available in SearchableObject
//            return ((SearchableObject) oldItem).toSearchableString().equals(((SearchableObject) newItem).toSearchableString());
//        } else {
//            // Handle the case where the items are not of type SearchableObject
//            // You might want to implement custom logic based on your use case
//            return oldItem.equals(newItem);
//        }
//    }
@Override
public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    Object oldItem = oldList.get(oldItemPosition);
    Object newItem = newList.get(newItemPosition);
    return oldItem.equals(newItem);
}
//    @Override
//    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        Object oldItem = oldList.get(oldItemPosition);
//        Object newItem = newList.get(newItemPosition);
//
//        if (oldItem instanceof SearchableObject && newItem instanceof SearchableObject) {
//            return oldItem.equals(newItem);
//        } else {
//            // Handle the case where the items are not of type SearchableObject
//            // You might want to implement custom logic based on your use case
//            return oldItem.equals(newItem);
//        }
//    }
}