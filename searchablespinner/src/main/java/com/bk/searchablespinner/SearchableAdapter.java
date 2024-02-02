    package com.bk.searchablespinner;

    import static com.bk.searchablespinner.SearchableListDialog.HideKeyboard;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.DiffUtil;
    import androidx.recyclerview.widget.AsyncListDiffer;
    import androidx.recyclerview.widget.RecyclerView;
    import java.util.List;

    public class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject> extends RecyclerView.Adapter<SearchableAdapter.MyViewHolder> {

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(com.bk.searchablespinner.SearchableObject item, int position);
        }

        private final DiffUtil.ItemCallback<SearchableObject> differCallback = new DiffUtil.ItemCallback<SearchableObject>() {
            @Override
            public boolean areItemsTheSame(@NonNull SearchableObject oldItem, @NonNull SearchableObject newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame( @NonNull SearchableObject oldItem, @NonNull SearchableObject newItem) {
                return oldItem.toSearchableString().equals(newItem.toSearchableString());
            }
        };

        private final AsyncListDiffer<SearchableObject> differ;

        private final int itemRowResourceId;

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public List<SearchableObject> getItems() {
            return differ.getCurrentList();
        }

        @Override
        public int getItemCount() {
            return differ.getCurrentList().size();
        }

        public SearchableAdapter(int itemRowResourceId, List<SearchableObject> items) {
            this.itemRowResourceId = itemRowResourceId;
            differ = new AsyncListDiffer<>(this, differCallback);
            differ.submitList(items);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(this.itemRowResourceId, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchableAdapter.MyViewHolder holder, int position) {
            SearchableObject searchableObject = getItems().get(holder.getAdapterPosition());
            holder.itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {HideKeyboard(view);
                    onItemClickListener.onItemClick(searchableObject, holder.getAdapterPosition());
                }
            });
        }

        public void updateData(List<SearchableObject> filteredData) {
            differ.submitList(filteredData);
        }
    }