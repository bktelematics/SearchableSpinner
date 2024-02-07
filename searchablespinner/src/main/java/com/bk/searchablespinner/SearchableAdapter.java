    package com.bk.searchablespinner;
    import static com.bk.searchablespinner.Util.ConvertString;
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

    public class SearchableAdapter<SearchableObject extends com.bk.searchablespinner.SearchableObject> extends RecyclerView.Adapter<SearchableAdapter.MyViewHolder> {

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
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

        private final AsyncListDiffer<SearchableObject> differ;
        private List<SearchableObject> _initialList;

        private final int itemRowResourceId;

        private OnItemClickListener onItemClickListener;
        private OnTextSearchedListener onTextSearchedListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }
        public void setOnTextSearchedListener(OnTextSearchedListener listener) {
            this.onTextSearchedListener = listener;
        }

        public List<SearchableObject> getItems() {
            return differ.getCurrentList();
        }
        public List<SearchableObject> getInitialList() {
            return _initialList;
        }

        @Override
        public int getItemCount() {
            return differ.getCurrentList().size();
        }

        public SearchableAdapter(int itemRowResourceId, List<SearchableObject> initialList) {
            this.itemRowResourceId = itemRowResourceId;
            differ = new AsyncListDiffer<>(this, differCallback);
            _initialList = initialList;
            differ.submitList(initialList);
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
                if (onItemClickListener != null) {
                    HideKeyboard(view);
                    onItemClickListener.onItemClicked(searchableObject, holder.getAdapterPosition());
                }
            });
        }

        public void updateData(String text) {
            List<SearchableObject> filteredList = new ArrayList<>();
            for (SearchableObject searchableObject : _initialList) {
                String itemText = searchableObject.getFilterableText();
                if (ConvertString(itemText).contains(ConvertString(text))) {
                    filteredList.add(searchableObject);
                }
            }
            differ.submitList(filteredList);
        }
    }