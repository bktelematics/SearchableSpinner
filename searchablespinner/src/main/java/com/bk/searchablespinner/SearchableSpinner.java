package com.bk.searchablespinner;


import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class SearchableSpinner<T> extends Spinner  implements SearchableListDialog.OnItemSelectedListener, SearchableListDialog.OnSearchTextChanged {
    private Context _context;
    private SearchableListDialog _searchableListDialog;
    private String _strHintText;
    SearchableObject localItem;
    private int customDropdownLayoutResource = R.layout.custm_spinner_dropdown_item;

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
    public void setCustomDropdownLayoutResource(int layoutResource) {
        customDropdownLayoutResource = layoutResource;
    }
    public void setAdapter(SearchableAdapter adapter, int position) {
        _searchableListDialog = new SearchableListDialog(adapter);
        _searchableListDialog.setOnItemSelectedListener(this);
        _searchableListDialog.setOnSearchTextChangedListener(this);
        if(position>=0){
            Object selectedItem = adapter.getItems().get(position);
            SearchableObject obj = (SearchableObject) selectedItem;
            this.localItem = obj;
            ArrayAdapter arrayAdapter = new ArrayAdapter(_context, customDropdownLayoutResource, new String[]{obj.toSetOnSpinner()});
            setAdapter(arrayAdapter);
        }
        else{
            if(_strHintText!=null){
                ArrayAdapter arrayAdapter = new ArrayAdapter(_context,customDropdownLayoutResource, new String[]{_strHintText});//custm_spinner_dropdown_item
                setAdapter(arrayAdapter);
            }
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
    public boolean performClick() {
        if (!_searchableListDialog.isAdded()) {
            _searchableListDialog.show(scanForActivity(_context).getSupportFragmentManager(), "SearchableListDialog");
        }
        return true;
    }

    @Override
    public void onItemSelected(SearchableObject item, int position) {
        this.localItem =item;
        _strHintText = item.toSetOnSpinner(); //εδω να βαζει ο χρηστης τι θελει να γινει display
        ArrayAdapter arrayAdapter = new ArrayAdapter(_context,customDropdownLayoutResource, new String[]{_strHintText});//simple_list_item_1
        setAdapter(arrayAdapter);
        _searchableListDialog.dismiss();
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
