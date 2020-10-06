package com.example.localpatientsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.localpatientsapp.Adapters.PatientListAdapter;
import com.example.localpatientsapp.Interfaces.RvClickListener;
import com.example.localpatientsapp.R;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseConstants;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseHelper;
import com.example.localpatientsapp.Utils.Constant;

import org.json.JSONArray;

public class PatientListActivity extends AppCompatActivity implements RvClickListener {

    //region variable
    private ImageView ivAddPatient;
    private RecyclerView rvPatients;
    private JSONArray patientArray;
    private DataBaseHelper dataBaseHelper;
    private PatientListAdapter adapter;
    private Context context = PatientListActivity.this;
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
        rvPatients = findViewById(R.id.rv_patients);
        ivAddPatient = findViewById(R.id.iv_add_patients);
    }

    /**
     * set up add patient click event
     */
    private void setupEvents() {
        ivAddPatient.setOnClickListener(view -> {
            openAddPatientScreen();
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

    private void showPatientDetail(int position) {
        try {
            Intent intent = new Intent(this, PatientDetailActivity.class);
            intent.putExtra(Constant.ID, patientArray.getJSONObject(position).getString(DataBaseConstants.Constants_TBL_PATIENTS.ID));
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}