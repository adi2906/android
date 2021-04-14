package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect.asyncTask.network.Callback;
import com.example.proiect.database.model.Item;
import com.example.proiect.database.service.ItemService;
import com.example.proiect.util.ItemAdapter;
import com.example.proiect.util.UserAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private Button btnAdd;
    private TextInputEditText tietName;
    private TextInputEditText tietWeight;
    private SeekBar sbPrice;
    private TextView tvPrice;
    private ListView lvItems;
    private ProgressBar progressBar;
    public List<Item> items = new ArrayList<>();
    private ItemService itemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initComponents();
        addLvItemsAdapter();

        itemService = new ItemService(getApplicationContext());
        itemService.getAll(getAllFromDbCallback());

//        Log.i("ITEMS:::", String.valueOf(items.size()));
//        Log.i("ITEMSV2:::", String.valueOf(lvItems.getCount()));

        sbPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPrice.setText(getString(R.string.CurrentPrice) + sbPrice.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void updateProgressBar() {
        progressBar.setProgress(lvItems.getCount());
    }

    private void addLvItemsAdapter() {
        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.lv_row_view_items, items, getLayoutInflater());
        lvItems.setAdapter(adapter);
    }

    private boolean validate() {
        if (tietName.getText() == null || tietName.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_item_error_1,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietWeight.getText() == null || tietWeight.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_item_error_2,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initComponents() {
        btnAdd = findViewById(R.id.add_item_button_add);
        tietName = findViewById(R.id.add_item_tiet_name);
        tietWeight = findViewById(R.id.add_item_tiet_weight);
        sbPrice = findViewById(R.id.add_item_seekbar_price);
        tvPrice = findViewById(R.id.add_item_tv_priceNumber);
        lvItems = findViewById(R.id.lv_list_of_items);
        progressBar = findViewById(R.id.add_item_progressBar);
        lvItems.setOnItemLongClickListener(deleteEventListener());
        lvItems.setOnItemClickListener(updateEventListener());
//        Log.i("ITEMSIZE", String.valueOf(items.size()));

        addItemAdapter();

        progressBar.setMax(50);
        updateProgressBar();
        sbPrice.setProgress(10);
        sbPrice.setMax(200);

        btnAdd.setOnClickListener(addItemEventListner());
    }

    private void addItemAdapter() {
        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.lv_row_view_items, items, getLayoutInflater());
        lvItems.setAdapter(adapter);
    }

    private View.OnClickListener addItemEventListner() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())  {
                    String name = tietName.getText().toString();
                    int weight = Integer.parseInt(tietWeight.getText().toString().trim());
                    int price = sbPrice.getProgress();
                    Item item = new Item(name, price, weight);

                    notifyAdapter();
                    itemService.insert(insertIntoDbCallback(), item);
                    progressBar.setProgress(lvItems.getCount());

//                    Log.i("name", item.getName());
//                    Log.i("weight", String.valueOf(item.getWeight()));
//                    Log.i("price", String.valueOf(item.getPrice()));
//                    Log.i("ITEMSV3:::", String.valueOf(lvItems.getCount()));

                    btnAdd.setText(R.string.add);
                    int whiteColor = Color.WHITE;
                    btnAdd.setTextColor(whiteColor);

                }
            }
        };
    }

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    items.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvItems.getAdapter();
        adapter.notifyDataSetChanged();
        updateProgressBar();
    }



    private Callback<List<Item>> getAllFromDbCallback() {
        return new Callback<List<Item>>() {
            @Override
            public void runResultOnUiThread(List<Item> result) {
                if (result != null) {
                    items.clear();
                    items.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }


    //update
    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item item= items.get(position);
                String name = item.getName();
                tietName.setText(name);
                String w = String.valueOf(item.getWeight());
                tietWeight.setText(w);
                int p = item.getPrice();
                sbPrice.setProgress(p);
                items.remove(position);
                notifyAdapter();
                int redColorValue = Color.RED;
                btnAdd.setTextColor(redColorValue);
                btnAdd.setText(R.string.modify);
                addItemEventListner();

            }
        };
    }



    //insert
    private Callback<Item> insertIntoDbCallback() {
        return new Callback<Item>() {
            @Override
            public void runResultOnUiThread(Item result) {
                if (result != null) {
                    items.add(result);
                    notifyAdapter();
                }
            }
        };
    }


    //delete
    private OnItemLongClickListener deleteEventListener() {
        return new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemService.delete(deleteToDbCallback(position), items.get(position));
                return true;
            }
        };
    }

}