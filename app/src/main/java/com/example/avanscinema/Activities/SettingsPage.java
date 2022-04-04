package com.example.avanscinema.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.avanscinema.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class SettingsPage extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup rgLangauge, rgTheme;
    private RadioButton enButton, nlButton, darkButton, lightButton;
    private String mLangauge;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_settings);
        rgLangauge = findViewById(R.id.radio_group_language_switch_settings);
        rgTheme = findViewById(R.id.radio_group_thema_switch_settings);
        enButton = findViewById(R.id.englishModeRadioBtn);
        nlButton = findViewById(R.id.dutchModeRadioBtn);
        darkButton = findViewById(R.id.darkModeRadioBtn);
        lightButton = findViewById(R.id.lightModeRadioBtn);
        drawerLayout = findViewById(R.id.settings_drawer);

        //adding listeners to the radiogroups
        rgLangauge.setOnCheckedChangeListener(this::onCheckedChanged);
        rgTheme.setOnCheckedChangeListener(this::onCheckedChanged);


        setupMenu();

    }

    private void setupMenu() {
        Toolbar toolbar = findViewById(R.id.settings_Toolbar);
        setSupportActionBar(toolbar);
        actionBar = new ActionBarDrawerToggle(SettingsPage.this, drawerLayout, R.string.Open, R.string.Close);
        actionBar.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view_settings);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.Movie_list:
                        Intent main = new Intent(SettingsPage.this, MainActivity.class);
                        startActivity(main);
                        break;
                    case R.id.Favourites:
                        //Show list of Favourite movies
                        break;
                    case R.id.settings:
                        //YOU ARE HERE
                        break;
                    case R.id.usermovielist:
                        Intent userListsIntent = new Intent(SettingsPage.this, UserListActivity.class);
                        startActivity(userListsIntent);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBar.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;

                    case R.id.lightModeRadioBtn:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        Intent settings = new Intent(getApplicationContext(), SettingsPage.class);
        startActivity(settings);
    }
}
