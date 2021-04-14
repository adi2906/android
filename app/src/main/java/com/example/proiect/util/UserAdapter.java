package com.example.proiect.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect.R;
import com.example.proiect.database.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private int resource;;
    private List<User> users;
    private LayoutInflater layoutInflater;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.users = objects;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        User user = users.get(position);
        if (user !=null) {
            addUserFirstName(view, user.getFirstName());
            addUserLastName(view, user.getLastName());
        }
        return view;
    }

    private void addUserFirstName(View view, String firstName) {
        TextView textView = view.findViewById(R.id.tv_row_first_name);
        textView.setText(firstName);
    }
    private void addUserLastName(View view, String lastName) {
        TextView textView = view.findViewById(R.id.tv_row_last_name);
        textView.setText(lastName);
    }
}
