package com.bk.searchablespinner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchableListDialog<T extends SearchableObject> extends DialogFragment implements SearchView.OnCloseListener {
    private RecyclerView rvItem;
    private SearchView svItem;
    private SearchableAdapter myadapter;
    private OnItemSelectedListener onItemSelectedListener;
    public interface OnItemSelectedListener {
        void onItemSelected(SearchableObject item, int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }
    public SearchableListDialog(SearchableAdapter adapter){
        myadapter = adapter;
        myadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(SearchableObject item, int position) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(item, position);
                }
            }
    });
        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(SearchableObject item, int position) {
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchable_spinner, container);
        svItem = view.findViewById(R.id.svItem);
        rvItem = view.findViewById(R.id.rvItem);
        return view;
    }

    public void HideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

        rvItem.addItemDecoration(new DividerItemDecoration(new ContextThemeWrapper(rvItem.getContext(),R.style.Theme_SearchableSpinner), DividerItemDecoration.VERTICAL));
        rvItem.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItem.setAdapter(myadapter);

        svItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        dismiss();
    }
    private void filter(String text) {
        ArrayList<SearchableObject> filteredList = new ArrayList<>();
        for (Object item : myadapter.getItems()) {
            String itemText = ((SearchableObject)item).toSearchableString();
            if (itemText.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add((SearchableObject) item);
            }
        }
        //myadapter.updateData(filteredList);
    }
}