package com.example.avanscinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.avanscinema.API.ApiConnection;
import com.example.avanscinema.R;

public class UserListCreateActivity extends AppCompatActivity implements ApiConnection.formResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_create);

        // Init api connection
        ApiConnection api = new ApiConnection();

        // Set onclick listener for back button
        ImageButton backButton = (ImageButton) findViewById(R.id.form_list_toolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserListCreateActivity.super.finish();
            }
        });

        // Set onclick for save button
        Button saveButton = (Button) findViewById(R.id.form_list_submit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = (EditText) findViewById(R.id.form_list_name);
                EditText descriptionEditText = (EditText) findViewById(R.id.form_list_description);

                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if(!name.matches("") && !description.matches("")){
                    api.addMovieList(UserListCreateActivity.this, name, description);
                }else{
                    Toast.makeText(getApplicationContext(), (R.string.fill_before_submit) , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onListCreateResponse(String message) {
        Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();

        if(message.contains("success")){
            Intent intent = new Intent(UserListCreateActivity.this, UserListActivity.class);
            startActivity(intent);
        }
    }
}