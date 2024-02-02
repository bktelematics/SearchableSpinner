package com.bk.searchablespinner;


import static com.bk.searchablespinner.SearchableListDialog.HideKeyboard;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public abstract class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private OnItemClickListener onItemClickListener;

    private final DiffUtil.ItemCallback<SearchableObject> differCallback = new DiffUtil.ItemCallback<SearchableObject>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchableObject oldItem, @NonNull SearchableObject newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchableObject oldItem, @NonNull SearchableObject newItem) {
            return oldItem.toSearchableString().equals(newItem.toSearchableString());
        }

    };

    private final AsyncListDiffer<SearchableObject> differ;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public List<SearchableObject> getItems() {
        return differ.getCurrentList();
    }

    public SearchableAdapter(List<SearchableObject> originalList) {
        differ = new AsyncListDiffer<>(this, differCallback);
        differ.submitList(originalList);
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
        differ.submitList(filteredData);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }
}
//    @NonNull
//    @Override
//    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(0,parent, false);
//        return new VH()
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FwtDialogAdapter.MyViewHolder {
//        val inflater = LayoutInflater.from(context)
//        val view = inflater.inflate(R.layout.item_fwt_dialog, parent, false)
//        return FwtDialogAdapter.MyViewHolder(view)
//    }