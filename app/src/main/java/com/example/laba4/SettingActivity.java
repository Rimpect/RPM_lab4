package com.example.laba4;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity  extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // загружаем предпочтения из ресурсов
        addPreferencesFromResource(R.xml.preference);
    }
}
