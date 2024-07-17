package com.bk.searchablespinner;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.text.Normalizer;

public class Util {

    private final static String LOG_TAG = "SEARCHABLE_SPINNER";

    private static  String toRegex(String name) {
        return name.replace(".", "\\.");
    }

    public static String RemoveAccents(String txt){
        String ret = "";

        try {
            ret = Normalizer.normalize(txt, Normalizer.Form.NFD).replace(toRegex("\\p{Mn}+"), "");
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return ret;
    }

    public static String NormalizeText(String txt){
        String ret = "";
        if(txt!=null)
            ret=RemoveAccents(txt).toUpperCase().trim();
        return ret;
    }

    public static void HideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static AppCompatActivity GetActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof AppCompatActivity)
            return (AppCompatActivity) cont;
        else if (cont instanceof ContextWrapper)
            return GetActivity(((ContextWrapper) cont).getBaseContext());
        return null;
    }

}
