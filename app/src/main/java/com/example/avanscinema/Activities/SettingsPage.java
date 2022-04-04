package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.avanscinema.R;

import java.util.Locale;

public class SettingsPage extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup rgLangauge, rgTheme;
    private RadioButton enButton, nlButton, darkButton, lightButton;
    private String mLangauge;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_settings);
        rgLangauge = findViewById(R.id.radio_group_language_switch_settings);
        rgTheme = findViewById(R.id.radio_group_thema_switch_settings);
        enButton = findViewById(R.id.englishModeRadioBtn);
        nlButton = findViewById(R.id.dutchModeRadioBtn);
        darkButton = findViewById(R.id.darkModeRadioBtn);
        lightButton = findViewById(R.id.lightModeRadioBtn);

        //adding listeners to the radiogroups
        rgLangauge.setOnCheckedChangeListener(this::onCheckedChanged);
        rgTheme.setOnCheckedChangeListener(this::onCheckedChanged);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == rgLangauge) {
            switch (i) {
                case R.id.dutchModeRadioBtn:
                    this.mLangauge = "nl_NL";
                    break;

                case R.id.englishModeRadioBtn:
                    this.mLangauge = "en";
                    break;
            }
            switchLanguage();
        }
            if (radioGroup == rgTheme) {
                switch (i){
                    case R.id.darkModeRadioBtn:
                        // TODO: 4-4-2022 plaats hier de switch functionaliteit naar dark mode
                        break;

                    case R.id.lightModeRadioBtn:
                        // TODO: 4-4-2022 plaats hier de switch functionaliteit naar light mode
                        break;
                }
        }
    }

    public void switchLanguage() {
        Locale locale = new Locale(this.mLangauge);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        SettingsPage.this.getResources().updateConfiguration(config, SettingsPage.this.getResources().getDisplayMetrics());
        Intent intent = new Intent(SettingsPage.this, MainActivity.class);
        startActivity(intent);
    }
}
