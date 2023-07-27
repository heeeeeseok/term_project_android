package com.project.term_project.activity.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceService {

    public void saveJWTToSharedPreferences(Context context, String jwt) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt", jwt);
        editor.apply();
    }

    public String getJWTFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("jwt", "");
    }

    public void updateJWTInSharedPreferences(Context context, String newJWT) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("jwt");
        editor.putString("jwt", newJWT);
        editor.apply();
    }
}
