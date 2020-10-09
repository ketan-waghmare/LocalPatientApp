package com.example.localpatientsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import com.example.localpatientsapp.R;
import com.example.localpatientsapp.Utils.DTU;
import com.example.localpatientsapp.Utils.Constant;
import com.example.localpatientsapp.Interfaces.RvClickListener;
import com.example.localpatientsapp.Adapters.PatientListAdapter;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseHelper;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseConstants;


public class PatientListActivity extends AppCompatActivity implements RvClickListener {

    //region variable
    private ImageView ivAddPatient;
    private RecyclerView rvPatients;
    private JSONArray patientArray;
    private DataBaseHelper dataBaseHelper;
    private PatientListAdapter adapter;
    private Context context = PatientListActivity.this;

    private Button btnFilter;
    private EditText edtFirstName;
    private EditText edtCreatedDate;
    private EditText edtDateOfBirth;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        initDB();
        setupUI();
        setupEvents();
        getPatientListFromDB();
    }

    private void initDB() {
        dataBaseHelper = new DataBaseHelper(this);
    }

    /**
     * set up UI elements here
     */
    private void setupUI() {
        btnFilter = findViewById(R.id.btn_filter);
        rvPatients = findViewById(R.id.rv_patients);
        ivAddPatient = findViewById(R.id.iv_add_patients);
        edtCreatedDate = findViewById(R.id.edt_created_date);
        edtDateOfBirth = findViewById(R.id.edt_date_of_birth);
        edtFirstName = findViewById(R.id.edt_first_name_filter);
    }

    /**
     * set up add patient click event
     */
    private void setupEvents() {
        ivAddPatient.setOnClickListener(view -> {
            openAddPatientScreen();
        });

        btnFilter.setOnClickListener(view -> {
            filterPatients();
        });

        edtDateOfBirth.setOnClickListener(view -> {
            showDatePicker(edtDateOfBirth);
        });

        edtCreatedDate.setOnClickListener(view -> {
            showDatePicker(edtCreatedDate);
        });

    }

    /**
     * get patient list from the database
     * set the patient list to recyclerview
     * if list is empty show no patient found message to user
     */
    private void getPatientListFromDB() {
        patientArray = dataBaseHelper.getPatientListFromDB();

        if (patientArray != null && patientArray.length() > 0) {
            rvPatients.setVisibility(View.VISIBLE);
            setPatientListAdapter(patientArray);
        } else {
            rvPatients.setVisibility(View.GONE);
            Toast.makeText(context, Constant.NO_PATIENT_FOUNT, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set list of the patient received from database
     * set the list click listener
     *
     * @param dataList
     */
    private void setPatientListAdapter(JSONArray dataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        rvPatients.setLayoutManager(gridLayoutManager);
        adapter = new PatientListAdapter(context, dataList);
        rvPatients.setAdapter(adapter);
        adapter.setRvClickListener(this);
    }

    /**
     * open add patient screen to add new patients
     */
    private void openAddPatientScreen() {
        context.startActivity(new Intent(this, AddPatientActivity.class));
    }

    /**
     * handle the list item click event of the patient list
     *
     * @param position
     * @param value
     * @param key
     */
    @Override
    public void rv_click(int position, int value, String key) {
        if (key.equals(Constant.DETAIL))
            showPatientDetail(position);
    }

    /**
     * move to the patient details screen
     * @param position
     */
    private void showPatientDetail(int position) {
        try {
            Intent intent = new Intent(this, PatientDetailActivity.class);
            intent.putExtra(Constant.ID, patientArray.getJSONObject(position).getString(DataBaseConstants.Constants_TBL_PATIENTS.ID));
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * filter the list of patients as per user input
     * set the filtered list to the adapter
     */
    private void filterPatients() {
        try {
            patientArray = dataBaseHelper.getFilteredList(edtFirstName.getText().toString(),edtCreatedDate.getText().toString(),edtDateOfBirth.getText().toString());
            setPatientListAdapter(patientArray);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * show date picker dialog to user for selecting the date
     */
    private void showDatePicker(EditText edtTextDate) {
        DTU.showDatePickerDialog(context, DTU.FLAG_OLD_AND_NEW, edtTextDate);
    }
}