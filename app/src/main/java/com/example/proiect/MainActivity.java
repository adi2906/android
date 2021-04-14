package com.example.proiect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.example.proiect.network.HttpManager;
import com.example.proiect.database.model.User;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.example.proiect.asyncTask.network.AsyncTaskRunner;
import com.example.proiect.asyncTask.network.Callback;

public class MainActivity extends AppCompatActivity {

    public static final String USER_ACCOUNTS_URL = "https://jsonkeeper.com/b/1BB2";
    private TextInputEditText username;
    private EditText password;
    private Button btnRegister;
    private Button btnLogin;
    private Button btnList;


    public List<User> users = new ArrayList<>();

    private static final int ADD_USER_REQUEST_CODE = 210;
    private static final int EXTRA_TEXT = 200;
    private static final String USER_KEY = "userKey";
    private static final String LIST_KEY = "listKey";
    static Date date = new Date();
    static int min = date.getMinutes();
    static int sec = date.getSeconds();
    public static final int minute1= min;

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        getUserAccountsFromHttp();

    }
    private void initComponents() {
        //Register
        username = findViewById(R.id.main_tiet_username);
        password = findViewById(R.id.main_password);
        btnRegister = findViewById(R.id.main_btn_register);
        btnLogin = findViewById(R.id.main_btn_login);
        btnList = findViewById(R.id.main_btn_list);

        //REGISTER BUTTON
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST_CODE);
            }
        });
        //LOGIN BUTTON
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = findViewById(R.id.main_tiet_username);
                password = findViewById(R.id.main_password);
                String usernameValue = String.valueOf(username.getText());
                String passwordValue = String.valueOf(password.getText());

                Boolean foundUser = false;
                for (int i =0; i< users.size(); i++) {
                    User user = users.get(i);
                    if (user.getUsername().trim().equals(usernameValue.trim())) {


                        if (String.valueOf(user.getPassword()).trim().equals(passwordValue.trim())){
                            foundUser=true;
                            Toast.makeText(getApplicationContext(), getString(R.string.user) + user.getUsername() + getString(R.string.successfully), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.putExtra(USER_KEY, user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }

                }
                if (!foundUser) {
                    Toast.makeText(getApplicationContext(), R.string.invalid_user_pass, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //LIST BUTTON
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListOfUsersActivity.class);
                intent.putExtra(LIST_KEY, (Serializable) users);
                startActivity(intent);
            }
        });
        populateListWihSomePeople();
    }

    private void populateListWihSomePeople() {
        users.add(new User("Adrian", "Soare", "adi123", 123, new Date() ));
        users.add(new User("Simona", "Stan", "simona123", 123, new Date() ));
        users.add(new User("Mircea", "Gabriel", "mircea123", 123, new Date() ));
        users.add(new User("Vlad", "Radu", "vlad123", 123, new Date() ));
    }

    //JSON STUFF:
    private void getUserAccountsFromHttp() {
        Callable<String> asyncOperation = new HttpManager(USER_ACCOUNTS_URL);
        Callback<String> mainThreadOperation = receiveUserAccountsFromHttp();
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);

    }

    private User[] JsonParserMethod(String json)
    {
        Gson gson=new Gson();

        User[] user=gson.fromJson(json, User[].class);
        return user;
    }

    private Callback<String> receiveUserAccountsFromHttp() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                User[] users1 = JsonParserMethod(result);
                for (User user: users1) {
                    users.add(user);
                }

            }
        };
    }

    //PENTRU
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!= null && requestCode == ADD_USER_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = (User) data.getSerializableExtra(RegisterActivity.ADD_USER_REQUEST_CODE);
            if (user != null) {
                users.add(user);
                username.setText(user.getUsername());
                password.setText(String.valueOf(user.getPassword()));

            }
        }
    }

}