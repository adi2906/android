package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proiect.firebase.Callback;
import com.example.proiect.firebase.FirebaseService;
import com.example.proiect.util.Company;
import com.example.proiect.util.CompanyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AuctionCompaniesActivity extends AppCompatActivity {

    private TextInputEditText tietName;
    private TextInputEditText tietOwner;
    private Spinner spnCategory;
    private Button btnClear;
    private FloatingActionButton fabDelete;
    private FloatingActionButton fabSave;
    private ListView lvCompanies;

    private List<Company> companies = new ArrayList<>();

    private int selectedCompanyIndex = -1;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_companies);

        initComponents();
        firebaseService = FirebaseService.getInstance();
        firebaseService.attachDataChangeEventListener(dataChangeCallback());
    }

    private Callback<List<Company>> dataChangeCallback() {
        return new Callback<List<Company>>() {
            @Override
            public void runResultOnUiThread(List<Company> result) {
                if (result != null) {
                    companies.clear();
                    companies.addAll(result);
                    notifyLvAdapter();
                    clear();
                }
            }
        };
    }


    private void initComponents() {
        tietName = findViewById(R.id.auction_tiet_name);
        tietOwner = findViewById(R.id.auction_tiet_owner);
        spnCategory = findViewById(R.id.auction_spn_categories);
        btnClear = findViewById(R.id.auction_btn_clear);
        fabDelete = findViewById(R.id.auction_fab_delete);
        fabSave = findViewById(R.id.auction_fab_save);
        lvCompanies = findViewById(R.id.auction_lv_companies);
        setSpnAdapter();
        setLvAdapter();
        btnClear.setOnClickListener(clearFieldsEventListener());
        fabDelete.setOnClickListener(deleteEventListener());
        fabSave.setOnClickListener(saveEventListener());
        lvCompanies.setOnItemClickListener(companySelectEventListener());
    }

    private AdapterView.OnItemClickListener companySelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCompanyIndex = position;
                tietName.setText(companies.get(selectedCompanyIndex).getName());
                tietOwner.setText(companies.get(selectedCompanyIndex).getOwner());
                selectCategory(companies.get(selectedCompanyIndex).getCategory());
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Company company = createCompanyFromView();
                    firebaseService.upsert(company);
                }
            }
        };
    }

    private View.OnClickListener deleteEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCompanyIndex != -1) {
                    firebaseService.delete(companies.get(selectedCompanyIndex));
                }
            }
        };
    }


    private boolean validate() {
        if (tietName.getText() == null || tietName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.auction_company_error_name,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tietOwner.getText() == null || tietOwner.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.auction_company_error_owner,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Company createCompanyFromView() {

        String id = selectedCompanyIndex >= 0 ? companies.get(selectedCompanyIndex).getId() : null;
        String name = tietName.getText().toString();
        String team = tietOwner.getText().toString();
        String role = spnCategory.getSelectedItem().toString();
        return new Company(id, name, team, role);
    }


    private void setSpnAdapter() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.custom_spinner_layout);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_layout);
        spnCategory.setAdapter(adapter);
    }

    private void setLvAdapter() {
        CompanyAdapter adapter = new CompanyAdapter(getApplicationContext(), R.layout.lv_row_view_companies,
                companies, getLayoutInflater());
        lvCompanies.setAdapter(adapter);
    }

    private void notifyLvAdapter() {
        CompanyAdapter companyAdapter = (CompanyAdapter) lvCompanies.getAdapter();
        companyAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener clearFieldsEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        };
    }

    private void selectCategory(String category) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnCategory.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(category)) {
                spnCategory.setSelection(i);
                break;
            }
        }
    }

    private void clear() {
        tietName.setText(null);
        tietOwner.setText(null);
        spnCategory.setSelection(0);
        selectedCompanyIndex = -1;
    }



}