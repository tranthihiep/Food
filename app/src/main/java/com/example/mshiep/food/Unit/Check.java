package com.example.mshiep.food.Unit;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.HTextView;

public class Check {
    public static void showToast(Context context, String messger){
        Toast.makeText(context, messger, Toast.LENGTH_SHORT).show();
    }
    public static void typeFaceED(Context context, EditText string){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NABILA.TTF");
        string.setTypeface(typeface);
    }
    public static void typeFaceTV(Context context, TextView string){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NABILA.TTF");
        string.setTypeface(typeface);
    }
    public static void typeFaceHTV(Context context, HTextView string){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/NABILA.TTF");
        string.setTypeface(typeface);
    }
}
