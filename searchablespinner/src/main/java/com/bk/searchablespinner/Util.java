package com.bk.searchablespinner;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.Normalizer;

public class Util {
    public static String RemoveAccents(String txt){
        String ret = "";
        ret = Normalizer.normalize(txt, Normalizer.Form.NFD).replace(toRegex("\\p{Mn}+"), "");
        return ret;
    }
    public static  String toRegex(String name) {
        return name.replace(".", "\\.");
    }
    public static  String ConvertString(String txt){
        String ret = "";
        if(txt!=null)
            ret=RemoveAccents(txt).toUpperCase().trim();
        return ret;
    }

    public  static void HideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public  static void setSizeOfDialog(Context context, double widthPercentage, double heightPercentage, Dialog dialog) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * widthPercentage);
        int height = (int) (displaymetrics.heightPixels * heightPercentage);
        dialog.getWindow().setLayout(width,height);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.round_background));
    }
}
