package com.bk.searchablespinner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchableSpinner<T> extends Spinner {
    public static final int NO_ITEM_SELECTED = -1;
    private Context _context;
    private List _items;
    private SearchableListDialog _searchableListDialog;
    private String _strHintText;
    int spinnerColor;
    private boolean _isFromInit;

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SearchableSpinner_hintText) {
                _strHintText = a.getString(attr);
            }
            if (attr == R.styleable.SearchableSpinner_color) {
                //spinnerColor =  a.getColor(attr,getResources().getColor(R.color.green));
            }
        }
        a.recycle();
        init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this._context = context;
        init();
    }
    public void updateItems(List<T> items, RecyclerView.Adapter<?> adapter) {
        _items.clear();
        _items.addAll(items);
        _searchableListDialog = SearchableListDialog.newInstance(_items);
        _searchableListDialog.show(scanForActivity(_context).getSupportFragmentManager(), "");
        _searchableListDialog.setAdapter(adapter);
        _searchableListDialog.setItems(items);
    }

//    @Override
//    public boolean performClick() {
//        if(Common.isNetworkAvailable(_context)){
//            ((VehicleListViewModel) viewModel).getCustomers();
//        }
//        else
//            Snackbar.make(_context,getRootView(), "Δεν υπάρχει σύνδεση στο δίκτυο.",Snackbar.LENGTH_SHORT).show();
//        return true;
//    }
    private void init() {
        _items = new ArrayList();
        _searchableListDialog = SearchableListDialog.newInstance(_items);
        if (!TextUtils.isEmpty(_strHintText)) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(_context, android.R.layout.simple_spinner_dropdown_item, new String[]{_strHintText});
            _isFromInit = true;
            setAdapter(arrayAdapter);
        }
    }
    private AppCompatActivity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof AppCompatActivity)
            return (AppCompatActivity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        return null;
    }

    @Override
    public int getSelectedItemPosition() {
        if (!TextUtils.isEmpty(_strHintText)) {
            return NO_ITEM_SELECTED;
        } else {
            return super.getSelectedItemPosition();
        }
    }

    @Override
    public Object getSelectedItem() {
        if (!TextUtils.isEmpty(_strHintText)) {
            return null;
        } else {
            return super.getSelectedItem();
        }
    }

    public void setSelectedCustomerText(String title) {
        _strHintText=title;
        if (!TextUtils.isEmpty(_strHintText)) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(_context, android.R.layout.simple_spinner_dropdown_item, new String[]{_strHintText});
            _isFromInit = true;
            setAdapter(arrayAdapter);
        }
        _searchableListDialog.dismiss();
    }
}
