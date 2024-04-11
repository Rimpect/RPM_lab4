package com.example.laba4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private final static String FILENAME = "sample.txt"; // имя файла
    private EditText mEditText;
    private SharedPreferences sharedPreferences;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Инициализация EditText
        mEditText = findViewById(R.id.editText);

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:
                saveFile(FILENAME);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Метод для открытия файла
    private void openFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }

                inputStream.close();
                mEditText.setText(builder.toString());
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    // Метод для сохранения файла
    private void saveFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(mEditText.getText().toString());
            osw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        // читаем установленное значение из CheckBoxPreference
        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
            openFile(FILENAME);
        }
        // читаем размер шрифта из EditTextPreference
        float fSize = Float.parseFloat(prefs.getString(
                getString(R.string.pref_size), "20"));

// применяем настройки в текстовом поле
        mEditText.setTextSize(fSize);
        // читаем стили текста из ListPreference
        String regular = prefs.getString(getString(R.string.pref_style), "");
        int typeface = Typeface.NORMAL;

        if (regular.contains("Полужирный"))
            typeface += Typeface.BOLD;

        if (regular.contains("Курсив"))
            typeface += Typeface.ITALIC;
        int backgroundColorId = sharedPreferences.getInt(getString(R.string.pref_background), R.color.default_background);
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(ContextCompat.getColor(this, backgroundColorId));
// меняем настройки в EditText
        mEditText.setTypeface(null, typeface);
    }
}