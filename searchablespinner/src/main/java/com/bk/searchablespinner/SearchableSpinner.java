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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.List;

public class SearchableSpinner<T> extends Spinner  implements SearchableListDialog.OnItemSelectedListener, SearchableListDialog.OnSearchTextChanged {
    private Context _context;
    public static final int NO_ITEM_SELECTED = -1;
    private SearchableListDialog _searchableListDialog;
    private String _strHintText;
    SearchableObject localItem;

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
    public void setOnSearchTextChangedListener(SearchableListDialog.OnSearchTextChanged onSearchTextChanged) {
        _searchableListDialog.setOnSearchTextChangedListener(onSearchTextChanged);
    }
    public void setAdapter(SearchableAdapter adapter) {
        _searchableListDialog = new SearchableListDialog(adapter);
        _searchableListDialog.setOnItemSelectedListener(this);
        _searchableListDialog.setOnSearchTextChangedListener(this);
        if(_strHintText!=null){
            ArrayAdapter arrayAdapter = new ArrayAdapter(_context, android.R.layout.simple_list_item_1, new String[]{_strHintText});
            setAdapter(arrayAdapter);
        }
        else{
            Object selectedItem = adapter.getItems().get(0);
            SearchableObject obj = (SearchableObject) selectedItem;
            ArrayAdapter arrayAdapter = new ArrayAdapter(_context, android.R.layout.simple_list_item_1, new String[]{obj.toSetOnSpinner()});
            setAdapter(arrayAdapter);
        }
//        setAdapter(arrayAdapter); //new Object[]{adapter.getItems().get(0)}
// arrayAdapter.addAll(adapter.getItems());
//        setPrompt("test");
//        searchableSpinner.setSelection(0);

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
    public boolean performClick() {
        if (!_searchableListDialog.isAdded()) {
            _searchableListDialog.show(scanForActivity(_context).getSupportFragmentManager(), "SearchableListDialog");
        }
        return super.performClick();
    }

    @Override
    public void onItemSelected(SearchableObject item, int position) {
        this.localItem =item;
        _strHintText = item.toSetOnSpinner(); //εδω να βαζει ο χρηστης τι θελει να γινει display
        ArrayAdapter arrayAdapter = new ArrayAdapter(_context, android.R.layout.simple_list_item_1, new String[]{_strHintText});
        setAdapter(arrayAdapter);
        _searchableListDialog.dismiss();
        //setSelection(position);
//        int test = getSelectedItemPosition();
//        Object var4 = getSelectedItem();
    }
     public Object findSpinnerItem(){
        if(localItem!=null)
            return localItem;
        else
            return null;
     }
    @Override
    public void onSearchTextChanged(String strText) {

    }
}
