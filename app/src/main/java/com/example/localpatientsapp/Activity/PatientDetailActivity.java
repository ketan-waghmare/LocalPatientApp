package com.example.localpatientsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.localpatientsapp.R;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseConstants;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseHelper;
import com.example.localpatientsapp.Utils.Constant;

import org.json.JSONArray;

import java.util.ArrayList;

public class PatientDetailActivity extends AppCompatActivity {

    //region variables
    private EditText edtDOB;
    private Spinner spnStatus;
    private RadioButton rbMale;
    private RadioGroup rgGender;
    private EditText edtLastName;
    private RadioButton rbFemale;
    private EditText edtFirstName;
    private Spinner spnBloodGroup;
    private EditText edtMobileNumber;

    private String patientID;
    private JSONArray patientArray;
    private ArrayList<String> statusList;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<String> bloodGroupList;
    private Context context = PatientDetailActivity.this;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        initDB();
        setupUI();
        setSpinnerAdapter();
        getReceivedIntentData();
    }

    /**
     * set up all the UI elements of the screen here
     */
    private void setupUI() {
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rgGender = findViewById(R.id.rg_gender);
        edtDOB = findViewById(R.id.edit_date_of_birth);
        spnStatus = findViewById(R.id.spn_status_edit);
        edtLastName = findViewById(R.id.edit_last_name);
        edtFirstName = findViewById(R.id.edit_first_name);
        spnBloodGroup = findViewById(R.id.spn_blood_group_edit);
        edtMobileNumber = findViewById(R.id.edit_mobile_number);
    }

    /**
     * set spinner adapters
     * set list of status
     * set list of blood groups
     */
    private void setSpinnerAdapter() {
        statusList = new ArrayList<>();
        statusList.add(Constant.SELECT_STATUS);
        statusList.add(Constant.ACTIVE);
        statusList.add(Constant.INACTIVE);

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(context, R.layout.simple_item_selected, statusList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnStatus.setAdapter(dataAdapter);

        bloodGroupList = new ArrayList<>();
        bloodGroupList.add(Constant.SELECT_BLOOD_GROUP);
        bloodGroupList.add(Constant.O_POSITIVE);
        bloodGroupList.add(Constant.O_NEGATIVE);
        bloodGroupList.add(Constant.A_POSITIVE);
        bloodGroupList.add(Constant.A_NEGATIVE);
        bloodGroupList.add(Constant.B_POSITIVE);
        bloodGroupList.add(Constant.B_NEGATIVE);
        bloodGroupList.add(Constant.AB_POSITIVE);
        bloodGroupList.add(Constant.AB_NEGATIVE);

        ArrayAdapter<String> bloodGroupAdapter;
        bloodGroupAdapter = new ArrayAdapter<String>(context, R.layout.simple_item_selected, bloodGroupList);
        bloodGroupAdapter.setDropDownViewResource(R.layout.simple_item);
        spnBloodGroup.setAdapter(bloodGroupAdapter);
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(this);
    }

    /**
     * get patient id received from the previous fragment
     * set the patient data of the received id
     */
    private void getReceivedIntentData() {
        patientID = getIntent().getStringExtra(DataBaseConstants.Constants_TBL_PATIENTS.ID);

        setPatientData();
    }

    /**
     * set patient data of the received id
     */
    private void setPatientData() {
        try {
            patientArray = dataBaseHelper.getPatientByID(patientID);
            edtDOB.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH));
            edtLastName.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME));
            edtFirstName.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME));
            edtMobileNumber.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER));
            spnStatus.setSelection(statusList.indexOf(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.STATUS)));
            spnBloodGroup.setSelection(bloodGroupList.indexOf(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP)));
            if (patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.GENDER).equals("male")) {
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
            } else {
                rbFemale.setChecked(true);
                rbMale.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}