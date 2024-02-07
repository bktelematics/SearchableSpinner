    package com.bk.searchablespinner;
    import static com.bk.searchablespinner.Util.NormalizeText;
    import static com.bk.searchablespinner.Util.HideKeyboard;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.DiffUtil;
    import androidx.recyclerview.widget.AsyncListDiffer;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;
    import java.util.List;

    public class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject>
            extends RecyclerView.Adapter<SearchableAdapter.SearchableSpinnerViewHolder> {

        interface OnItemClickListener {
            void onItemClicked(com.bk.searchablespinner.SearchableObject item, int position);
        }

        private OnItemClickListener onItemClickListener;

        private final int itemRowResourceId;

        private final List<SearchableObject> _initialList;

        private final AsyncListDiffer<SearchableObject> differ;

        public SearchableAdapter(int itemRowResourceId, List<SearchableObject> initialList) {
            this.itemRowResourceId = itemRowResourceId;
            differ = new AsyncListDiffer<>(this, differCallback);
            _initialList = initialList;
            differ.submitList(initialList);
        }

        public List<SearchableObject> getItems() {
            return differ.getCurrentList();
        }

        @Override
        public int getItemCount() {
            return differ.getCurrentList().size();
        }

        private final DiffUtil.ItemCallback<SearchableObject> differCallback = new DiffUtil.ItemCallback<SearchableObject>() {
            @Override
            public boolean areItemsTheSame(@NonNull SearchableObject oldItem, @NonNull SearchableObject newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame( @NonNull SearchableObject oldItem, @NonNull SearchableObject newItem) {
                return oldItem.getFilterableText().equals(newItem.getFilterableText());
            }
        };

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public static class SearchableSpinnerViewHolder extends RecyclerView.ViewHolder {
            public SearchableSpinnerViewHolder(View itemView) {
                super(itemView);
            }
        }

        @NonNull
        @Override
        public SearchableSpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(this.itemRowResourceId, parent, false);
            return new SearchableSpinnerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchableSpinnerViewHolder holder, int position) {
            SearchableObject searchableObject = getItems().get(holder.getAdapterPosition());
            holder.itemView.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    HideKeyboard(view);
                    onItemClickListener.onItemClicked(searchableObject, holder.getAdapterPosition());
                }
            });
        }

        public void updateData(String txtFilter) {
            List<SearchableObject> filteredList = new ArrayList<>();
            for (SearchableObject searchableObject : _initialList) {
                String itemText = searchableObject.getFilterableText();
                if (NormalizeText(itemText).contains(NormalizeText(txtFilter))) {
                    filteredList.add(searchableObject);
                }
            }
            differ.submitList(filteredList);
        }
    }