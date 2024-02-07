package com.bk.searchablespinner;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class SearchableSpinner<T> extends Spinner  implements OnItemClickListener{
    private Context _context;
    private SearchableListDialog _searchableListDialog;
    private String _strHintText;
    private int _layoutResource;

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SearchableSpinner_hintText) {
                _strHintText = a.getString(attr);
            } else if (attr == R.styleable.SearchableSpinner_layoutResource) {
                _layoutResource = a.getResourceId(attr, 0);
            }
        }
        a.recycle();
    }
    private void setInitialObjectToSpinner(SearchableObject myObject){
        ArrayAdapter<SearchableObject> arrayAdapter = new ArrayAdapter<>(_context, _layoutResource, new SearchableObject[]{myObject});
        setAdapter(arrayAdapter);
    }
    private void setInitialTextToSpinner(String text){
        ArrayAdapter arrayAdapter = new ArrayAdapter(_context,_layoutResource, new String[]{text});
        setAdapter(arrayAdapter);
    }
    public void setAdapter(SearchableAdapter adapter, int position) {
        _searchableListDialog = new SearchableListDialog(adapter);
        _searchableListDialog.setOnItemClickListener(this);
        if(position>=0){
            Object selectedItem = adapter.getItems().get(position);
            SearchableObject obj = (SearchableObject) selectedItem;
            setInitialObjectToSpinner(obj);
        }
        else{
            if(_strHintText!=null){
                setInitialTextToSpinner(_strHintText);
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
    public void onItemClicked(SearchableObject item, int position) {
        setInitialObjectToSpinner(item);
        _searchableListDialog.dismiss();
    }
}
