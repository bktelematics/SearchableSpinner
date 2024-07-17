package com.bk.searchablespinner;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchableListDialog extends DialogFragment {
    private RecyclerView rvSLDialog;
    private SearchView svSLDialog;
    private final SearchableAdapter searchableAdapter;

    public SearchableListDialog(SearchableAdapter adapter) {
        searchableAdapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_searchable_list, container);
        return view;
    }
    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        svSLDialog = view.findViewById(R.id.svSLDialog);
        rvSLDialog = view.findViewById(R.id.rvSLDialog);
        rvSLDialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Util.HideKeyboard(v);
                return false;
            }
        });

        svSLDialog.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchableAdapter.updateData(s);
                return false;
            }
        });

        rvSLDialog.addItemDecoration(new DividerItemDecoration(new ContextThemeWrapper(rvSLDialog.getContext(), R.style.Theme_SearchableSpinner), DividerItemDecoration.VERTICAL));
        rvSLDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSLDialog.setAdapter(searchableAdapter);

        initDialog(this.requireDialog(), 0.95,0.95);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (svSLDialog != null) {
            svSLDialog.setQuery("", true);
            svSLDialog.clearFocus();
        }
    }

    private void initDialog(Dialog dialog, double widthPercentage, double heightPercentage) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * widthPercentage);
        int height = (int) (displaymetrics.heightPixels * heightPercentage);
        dialog.getWindow().setLayout(width, height);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(dialog.getContext(), R.drawable.round_background));
    }

}