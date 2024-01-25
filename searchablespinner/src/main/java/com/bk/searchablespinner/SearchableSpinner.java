package com.bk.searchablespinner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

public class SearchableSpinner<T> extends Spinner  implements SearchableListDialog.OnItemSelectedListener {
    private Context _context;
    private SearchableListDialog _searchableListDialog;

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
    }

    public void setAdapter(SearchableAdapter adapter) {
        _searchableListDialog = new SearchableListDialog(adapter);
        _searchableListDialog.setOnItemSelectedListener(this);
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
            return super.getSelectedItemPosition();
    }
    @Override
    public Object getSelectedItem() {
            return super.getSelectedItem();
    }

    @Override
    public boolean performClick() {
        if (!_searchableListDialog.isAdded()) {
            _searchableListDialog.show(scanForActivity(_context).getSupportFragmentManager(), "SearchableListDialog");
        }
        return super.performClick();
    }

    @Override
    public void onItemSelected(SearchableObject item, int position) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(_context, android.R.layout.simple_spinner_dropdown_item, new String[]{item.toSearchableString()});
        setAdapter(arrayAdapter);
        _searchableListDialog.dismiss();
    }
}