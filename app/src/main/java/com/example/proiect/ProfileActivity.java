package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    public static final String SHARED_PREF_FILE_NAME = "profileSharedPref";
    public static final String ADRESS = "adress";
    public static final String GENDER_RB_ID = "gender_rb_id";
    public static final String COUNTRY_ID = "country_id";
    public static final String RATING_ID = "rating_id";
    public static final String RATING_STARS = "stars";



    private Spinner spnCountry;
    private TextInputEditText tietAdress;
    private RadioGroup rgGender;
    private Button btnSave;
    private Button btnFinish;
    private RatingBar rbRating;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initCompoents();

    }

    private void initCompoents() {
        spnCountry = findViewById(R.id.profile_spinner);
        tietAdress = findViewById(R.id.profile_tiet_adress);
        rgGender = findViewById(R.id.rg_profile_gender);
        btnSave = findViewById(R.id.profile_btnSave);
        rbRating = findViewById(R.id.profile_rbRating);
        btnFinish = findViewById(R.id.profile_btnFinish);



        preferences = getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        populateCountryAdapter();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adress = tietAdress.getText()!=null ? tietAdress.getText().toString() : "";
                int genderRbId = rgGender.getCheckedRadioButtonId();
                int countryId = (int) spnCountry.getSelectedItemId();
                float ratingId = rbRating.getRating();

//                Log.d("country4", String.valueOf(spnCountry.getSelectedItemId()));
//                Log.d("Rating1", String.valueOf(rbRating.getNumStars()));
//                Log.i("Rating2", String.valueOf(rbRating.getRating()));



                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(ADRESS, adress);
                editor.putInt(GENDER_RB_ID,  genderRbId);
                editor.putInt(COUNTRY_ID, countryId);
                editor.putFloat(RATING_ID,ratingId);
                editor.apply();
                finish();

            }
        });
        getFromSharedPreferences();


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                intent.putExtra(RATING_STARS , rbRating.getRating());
                startActivity(intent);
            }
        });
    }

    private void getFromSharedPreferences() {
        String adress = preferences.getString(ADRESS, "");
        int genderRbId = preferences.getInt(GENDER_RB_ID, 0);
        int countryId = preferences.getInt(COUNTRY_ID, 0);
        float ratingId = preferences.getFloat(RATING_ID, 0);

        //populare elemente vizuale
        tietAdress.setText(adress);
        rgGender.check(genderRbId);
        spnCountry.setSelection(countryId);
        rbRating.setRating(ratingId);

    }

    private void populateCountryAdapter() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.countries, R.layout.custom_spinner_layout);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_layout);
        spnCountry.setAdapter(adapter);
    }
}