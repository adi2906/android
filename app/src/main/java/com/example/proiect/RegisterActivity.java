package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiect.util.DateConverter;
import com.example.proiect.database.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    public static final String ADD_USER_REQUEST_CODE = "user";

    private final DateConverter dateConverter = new DateConverter();

    private TextInputEditText tietFirstName;
    private TextInputEditText tietLastName;
    private TextInputEditText tietUsername;
    private EditText tietPassword;
    private DatePicker dpBirthday;
    private Button btnSave;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        intent = getIntent();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validate()){
                   User user = buildUserFromWidgets();

                   intent.putExtra(ADD_USER_REQUEST_CODE, user);
                   setResult(RESULT_OK, intent);
                   finish();
                }

            }
        });
    }


    private void initComponents() {
        tietFirstName = findViewById(R.id.register_tiet_FirstName);
        tietLastName = findViewById(R.id.register_tiet_LastName);
        tietUsername = findViewById(R.id.register_tiet_Username);
        tietPassword = findViewById(R.id.register_tbPassword);
        dpBirthday = findViewById(R.id.register_birthday);
        btnSave = findViewById(R.id.register_btnSave);
    }

    private boolean validate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (year-dpBirthday.getYear() <= 18) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_register_error1,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietFirstName.getText() == null || tietFirstName.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_register_error2,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietLastName.getText() == null || tietLastName.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_register_error3,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietUsername.getText() == null || tietUsername.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_register_error4,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietPassword.getText() == null || tietPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.invalid_register_error5,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;


    }

    private User buildUserFromWidgets() {
        String firstName = tietFirstName.getText().toString();
        String lastName = tietLastName.getText().toString();
        String username = tietUsername.getText().toString();
        int password = Integer.parseInt( tietPassword.getText().toString().trim());
        int day = dpBirthday.getDayOfMonth();
        int month =dpBirthday.getMonth();
        int year = dpBirthday.getYear() - 1900;
        Date birthday = new Date();



        Date d = new Date(year, month, day);
        String strDate = dateConverter.toString(d);
       // Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), R.string.added_user, Toast.LENGTH_SHORT).show();

        User user = new User(firstName, lastName, username, password, d);
        //Toast.makeText(getApplicationContext(), user.getBirthday().toString(), Toast.LENGTH_SHORT).show();

        return user;

    }



}