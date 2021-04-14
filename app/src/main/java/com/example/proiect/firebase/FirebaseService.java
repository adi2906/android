package com.example.proiect.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proiect.util.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseService {
    public static final String COMPANY_TABLE_NAME = "companies";
    private DatabaseReference database;

    private static FirebaseService firebaseService;

    private FirebaseService() {
        database = FirebaseDatabase.getInstance().getReference(COMPANY_TABLE_NAME);
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void upsert(Company company) {
        if (company == null) {
            return;
        }
        if (company.getId() == null || company.getId().trim().isEmpty()) {
            String id = database.push().getKey();
            company.setId(id);
        }
        database.child(company.getId()).setValue(company);
    }

    public void delete(final Company company) {
        if (company == null || company.getId() == null || company.getId().trim().isEmpty()) {
            return;
        }
        database.child(company.getId()).removeValue();
    }

    public void attachDataChangeEventListener(final Callback<List<Company>> callback) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Company> companies = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Company company = data.getValue(Company.class);
                    if (company != null) {
                        companies.add(company);
                    }
                }
                callback.runResultOnUiThread(companies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseService", "Data is not available");
            }
        });
    }
}
