package com.bk.searchablespinner;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchableListDialog<T> extends DialogFragment implements SearchView.OnCloseListener {

    private static final String ITEMS = "items";

    private RecyclerView rvItem;
    private SearchView svItem;
    RecyclerView.Adapter myadapter;
    private List<T> myItems;
    public SearchableListDialog() {

    }

    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new SearchableListDialog();
        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);
        multiSelectExpandableFragment.setArguments(args);
        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.searchable_spinner, null);
        svItem = view.findViewById(R.id.svItem);
        rvItem = view.findViewById(R.id.rvItem);
        super.onCreate(savedInstanceState);
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
        rvItem.addItemDecoration(new DividerItemDecoration(new ContextThemeWrapper(rvItem.getContext(),R.style.Theme_SearchableSpinner), DividerItemDecoration.VERTICAL));
        rvItem.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItem.setAdapter(myadapter);

//        svItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter(newText);
//                return true;
//            }
//        });
    }
    public void HideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        myadapter = adapter;
    }
    public void setItems(List<T> items){
        myItems = items;
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
        ArrayList<T> filteredList = new ArrayList<>();
        for (T item : myItems) {
            String itemText = item.toString();
            if (itemText.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        // Notify the adapter with the filtered list
//        if (myAdapter instanceof Filterable) {
//            ((Filterable) myAdapter).getFilter().filter(text);
    }
    // }
//    private void filter(String text) {
//        ArrayList<Customer> filteredList = new ArrayList<>();
//        if (null != _onSearchTextChanged) {
//            _onSearchTextChanged.onSearchTextChanged(text);
//        }
//        if (customers != null) {
//            for (Customer customer : customers) {
//                if (ConvertString(customer.getTitle()).contains(ConvertString(text)) || ConvertString(customer.getTitleInLatin()).contains(ConvertString(text))) {
//                    filteredList.add(customer);
//                }
//            }
//        }
//    }
    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T item);
    }

    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }
}