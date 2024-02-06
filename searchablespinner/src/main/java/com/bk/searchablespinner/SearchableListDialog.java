package com.bk.searchablespinner;

import static com.bk.searchablespinner.Util.HideKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchableListDialog<T extends SearchableObject> extends DialogFragment implements SearchView.OnQueryTextListener {
    private RecyclerView rvItem;
    private SearchView svItem;
    private SearchableAdapter myadapter;
    private OnItemClickListener onItemClickListener;
    private OnSearchTextChanged _onSearchTextChanged;


    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SearchableListDialog(SearchableAdapter adapter) {
        myadapter = adapter;
        setStyle(R.style.DialogStyle, R.style.DialogStyle);


        myadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(SearchableObject item, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(item, position);
                    if (svItem != null) {
                        svItem.setQuery("", true);
                        svItem.clearFocus();
                    }
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchable_spinner, container);
        svItem = view.findViewById(R.id.svItem);
        rvItem = view.findViewById(R.id.rvItem);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        svItem = view.findViewById(R.id.svItem);
        rvItem = view.findViewById(R.id.rvItem);
        rvItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HideKeyboard(v);
                return false;
            }
        });

        svItem.setOnQueryTextListener(this);
        rvItem.addItemDecoration(new DividerItemDecoration(new ContextThemeWrapper(rvItem.getContext(), R.style.Theme_SearchableSpinner), DividerItemDecoration.VERTICAL));
        rvItem.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItem.setAdapter(myadapter);
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

    private void filter(String text) {
        myadapter.updateData(text);
    }

    @Override
    public void onResume() {
        if (svItem != null) {
            svItem.setQuery("", true);
            svItem.clearFocus();
        }
        super.onResume();
    }

}