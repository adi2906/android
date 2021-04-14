package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proiect.database.model.User;

public class MenuActivity extends AppCompatActivity {



    private Button btnSettings;
    private User user;
    private static final String USER_KEY = "userKey";
    private Button btnAddItems;
    private Button btnAuctionCompanies;

    //menu_btn_items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Intent intent = getIntent();
        user = (User) getIntent().getSerializableExtra(USER_KEY);
        initComponents();

    }

    private void initComponents() {
        btnAuctionCompanies = findViewById(R.id.menu_btn_auction_companies);
        btnSettings = findViewById(R.id.menu_settings);
        btnAddItems = findViewById(R.id.menu_btn_add_items);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        btnAuctionCompanies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuctionCompaniesActivity.class);
                startActivity(intent);
            }
        });

    }
}