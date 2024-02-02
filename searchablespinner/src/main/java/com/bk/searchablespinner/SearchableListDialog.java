package com.bk.searchablespinner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class SearchableListDialog<T extends SearchableObject> extends DialogFragment implements SearchView.OnCloseListener,  SearchView.OnQueryTextListener {
    private RecyclerView rvItem;
    private SearchView svItem;
    private SearchableAdapter myadapter;
    private OnItemSelectedListener onItemSelectedListener;
    private OnSearchTextChanged _onSearchTextChanged;
    private List<SearchableObject> originalList;
    private List<SearchableObject> currentList;


    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(SearchableObject item, int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    public SearchableListDialog(SearchableAdapter adapter) {
        myadapter = adapter;
        originalList = new ArrayList<>(myadapter.getItems());
        currentList = new ArrayList<>(originalList);

        myadapter.setOnItemClickListener(new SearchableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchableObject item, int position) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(item, position);
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

    public  static void HideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        svItem = view.findViewById(R.id.svItem);
        rvItem = view.findViewById(R.id.rvItem);

        this.setSizeOfDialog(0.95,0.95,this.requireDialog());
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
        List<SearchableObject> filteredList = new ArrayList<>();
        for (SearchableObject item : originalList) {
            String itemText = item.toSearchableString();
            if (ConvertString(itemText.toString()).contains(ConvertString(text.toString()))) {
                filteredList.add(item);
            }
        }
        currentList = filteredList;
        myadapter.updateData(filteredList);
    }
    public  void setSizeOfDialog(double widthPercentage, double heightPercentage, Dialog dialog) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * widthPercentage);
        int height = (int) (displaymetrics.heightPixels * heightPercentage);
        dialog.getWindow().setLayout(width,height);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(this.getContext().getDrawable(R.drawable.round_background));
    }
    private  String RemoveAccents(String txt){
        String ret = "";
        ret = Normalizer.normalize(txt, Normalizer.Form.NFD).replace(toRegex("\\p{Mn}+"), "");
        return ret;
    }
    private  String toRegex(String name) {
        return name.replace(".", "\\.");
    }
    private  String ConvertString(String txt){
        String ret = "";
        if(txt!=null)
            ret=RemoveAccents(txt).toUpperCase().trim();
        return ret;
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
}