package com.bk.searchablespinner;

import android.content.Context;
import android.view.View;
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
}
