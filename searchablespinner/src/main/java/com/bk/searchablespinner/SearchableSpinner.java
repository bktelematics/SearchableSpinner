package com.bk.searchablespinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

@SuppressLint("AppCompatCustomView")
public class SearchableSpinner extends Spinner {
    private final Context _context;
    private SearchableAdapter _searchableAdapter;
    private int _selectedItemPosition;
    private final static String FRAGMENT_TAG = "FRAGMENT_SEARCHABLE_SPINNER";
    private int _layoutResource;


    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.searchableSpinner);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.searchableSpinner_layoutResource) {
                _layoutResource = a.getResourceId(attr, 0);
            }
        }
        a.recycle();
    }

    public void init(SearchableAdapter adapter, int selectedItemPosition) {
        _searchableAdapter = adapter;
        _selectedItemPosition = selectedItemPosition;

        SearchableObject searchableObject = (SearchableObject) _searchableAdapter.getItems().get(_selectedItemPosition);
        setSelectedItem(searchableObject);
    }

    public void setSelectedItem (SearchableObject searchableObject){
        ArrayAdapter<SearchableObject> arrayAdapter = new ArrayAdapter<>(_context, _layoutResource, new SearchableObject[]{ searchableObject });
        setAdapter(arrayAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean performClick() {
        showDialog();
        return true;
    }

    private void showDialog(){
        SearchableListDialog searchableListDialog = new SearchableListDialog(_searchableAdapter);

        _searchableAdapter.setOnItemClickListener((item, itemPosition) -> {
            setSelectedItem(item);
            searchableListDialog.dismiss();
        });

        if (!searchableListDialog.isAdded()) {
            searchableListDialog.show(Util.GetActivity(_context).getSupportFragmentManager(), FRAGMENT_TAG);
        }
    }


}
