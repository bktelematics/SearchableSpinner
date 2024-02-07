package com.bk.searchablespinner;

import static com.bk.searchablespinner.Util.HideKeyboard;
import static com.bk.searchablespinner.Util.setSizeOfDialog;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchableListDialog<T extends SearchableObject> extends DialogFragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private SearchView searchView;
    private SearchableAdapter myadapter;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SearchableListDialog(SearchableAdapter adapter) {
        myadapter = adapter;
        myadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(SearchableObject item, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(item, position);
                    if (searchView != null) {
                        searchView.setQuery("", true);
                        searchView.clearFocus();
                    }
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.searchable_spinner, container);
        searchView = view.findViewById(R.id.svItem);
        recyclerView = view.findViewById(R.id.rvItem);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.svItem);
        recyclerView = view.findViewById(R.id.rvItem);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HideKeyboard(v);
                return false;
            }
        });
        searchView.setOnQueryTextListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(new ContextThemeWrapper(recyclerView.getContext(), R.style.Theme_SearchableSpinner), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myadapter);
    }

    private void filter(String text) {
        myadapter.updateData(text);
    }

    @Override
    public void onResume() {
      setSizeOfDialog(requireContext(),0.95,0.95,this.requireDialog());
        super.onResume();
    }
        @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return true;
    }
}