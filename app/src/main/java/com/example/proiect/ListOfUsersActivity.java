package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.proiect.asyncTask.network.Callback;
import com.example.proiect.database.model.Item;
import com.example.proiect.database.model.User;
import com.example.proiect.database.service.ItemService;
import com.example.proiect.database.service.UserService;
import com.example.proiect.util.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListOfUsersActivity extends AppCompatActivity {


    public List<User> usersbackup = new ArrayList<>();
    public List<User> users = new ArrayList<>();
    private ListView lvUserAccounts;
    private TextView tvUsersRegistered;
    private Button btnBack;
    private Switch switchListOfUsers;

    private UserService userService;
    public static final String LIST_KEY = "listKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);


        if (getIntent().getExtras() != null) {
            users = (List<User>) getIntent().getSerializableExtra(LIST_KEY);

        }

        initComponents();




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchListOfUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchListOfUsers.isChecked()){
                    addUserAdapter();
                    notifyAdapter();
                }
                else {
                    //switchListOfUsers.setChecked(true);
                    removeList();
                    tvUsersRegistered.setText(R.string.emptystring);
                }
                //Log.i("switch1", String.valueOf(switchListOfUsers.isChecked()));
            }
        });

        userService = new UserService(getApplicationContext());

    }



    private void initComponents() {
        lvUserAccounts = findViewById(R.id.lv_list_of_users_accounts);
        tvUsersRegistered = findViewById(R.id.tv_list_of_user_accounts_total);
        btnBack = findViewById(R.id.activity_list_of_users_btn_back);
        switchListOfUsers = findViewById(R.id.activity_list_of_users_switch);
        addUserAdapter();
        switchListOfUsers.setChecked(true);
        tvUsersRegistered.setText(getString(R.string.theres_a_total) + String.valueOf(users.size()) + getString(R.string.users_regietered));
        lvUserAccounts.setOnItemLongClickListener(deleteEventListener());


    }

    //delete
    private AdapterView.OnItemLongClickListener deleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                userService.delete(deleteToDbCallback(position), users.get(position));
                return true;
            }
        };
    }

    //insert
    private Callback<User> insertIntoDbCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if (result != null) {
                    users.add(result);
                    notifyAdapter();
                }
            }
        };
    }

    private void removeList() {


        UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.lv_row_view, usersbackup, getLayoutInflater());
        lvUserAccounts.setAdapter(adapter);
        notifyAdapter();
    }



    private void notifyAdapter() {
        UserAdapter adapter = (UserAdapter) lvUserAccounts.getAdapter();
        adapter.notifyDataSetChanged();
        tvUsersRegistered.setText(getString(R.string.theres_a_total) + String.valueOf(users.size()) + getString(R.string.users_regietered));
    }

    private void addUserAdapter() {
        UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.lv_row_view, users, getLayoutInflater());
        lvUserAccounts.setAdapter(adapter);
    }

    private Callback<List<User>> getAllFromDbCallback() {
        return new Callback<List<User>>() {
            @Override
            public void runResultOnUiThread(List<User> result) {
                if (result != null) {
                    users.clear();
                    users.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }



    private Callback<Integer> deleteToDbCallback(final int position) {  //BD
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    users.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

}