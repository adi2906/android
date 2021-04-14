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
import com.example.proiect.database.model.Item;
import com.example.proiect.database.model.User;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context context;
    private int resource;;
    private List<Item> items;
    private LayoutInflater layoutInflater;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Item item = items.get(position);
        if (item !=null) {
            addItemName(view, item.getName());
            addItemWeight(view, String.valueOf(item.getWeight()));
            addItemPrice(view, String.valueOf(item.getPrice()));
        }
        return view;
    }

    private void addItemName(View view, String name) {
        TextView textView = view.findViewById(R.id.item_tv_name);
        textView.setText(name);
    }
    private void addItemWeight(View view, String weight) {
        TextView textView = view.findViewById(R.id.item_tv_weight);
        textView.setText(weight);
    }
    private void addItemPrice(View view, String price) {
        TextView textView = view.findViewById(R.id.item_tv_price);
        textView.setText(price);
    }





}
