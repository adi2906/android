package com.example.proiect.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect.R;


public class CompanyAdapter extends ArrayAdapter<Company> {
    private Context context;
    private int resource;
    private List<Company> companies;
    private LayoutInflater inflater;

    public CompanyAdapter(@NonNull Context context, int resource, @NonNull List<Company> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.companies = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Company company = companies.get(position);
        if (company != null) {
            addName(view, company.getName());
            addOwner(view, company.getOwner());
            addCategory(view, company.getCategory());
        }
        return view;
    }

    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.tv_row_name);
        populateTVs(textView, name);
    }

    private void addOwner(View view, String team) {
        TextView textView = view.findViewById(R.id.tv_row_team);
        populateTVs(textView, team);
    }

    private void addCategory(View view, String role) {
        TextView textView = view.findViewById(R.id.tv_row_role);
        populateTVs(textView, role);
    }

    private void populateTVs(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.default_value);
        }
    }
}
