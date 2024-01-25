package com.bk.searchablespinner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

public class SearchableSpinner<T> extends Spinner  implements SearchableListDialog.OnItemSelectedListener {
    public static final int NO_ITEM_SELECTED = -1;
    private Context _context;
    private SearchableListDialog _searchableListDialog;
    private String _strHintText;

//    public SearchableSpinner(Context context) {
//        super(context);
//    }

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
        }
        a.recycle();
    }

    public SearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
