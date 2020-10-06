package com.example.localpatientsapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.content.Context;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.content.ContentValues;

import com.example.localpatientsapp.R;
import com.example.localpatientsapp.Utils.DTU;
import com.example.localpatientsapp.Utils.Utils;
import androidx.appcompat.app.AppCompatActivity;
import com.example.localpatientsapp.Utils.Constant;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseHelper;
import com.example.localpatientsapp.SQLiteDatabase.DataBaseConstants;

public class AddPatientActivity extends AppCompatActivity {

    // region variable
    private EditText edtDOB;
    private Spinner spnStatus;
    private RadioButton rbMale;
    private RadioGroup rgGender;
    private RadioButton rbGender;
    private EditText edtLastName;
    private RadioButton rbFemale;
    private EditText edtFirstName;
    private EditText edtMobileNumber;

    private Button btnAddPatient;
    private int selectedGenderId;
    private Spinner spnBloodGroup;
    private DataBaseHelper dataBaseHelper;
    private Context context = AddPatientActivity.this;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        initDB();
        setupUI();
        setupEvents();
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(context);
    }

    /**
     * set up all the UI elements of the screen
     */
    private void setupUI() {
        rbMale = findViewById(R.id.rd_male);
        rgGender = findViewById(R.id.rd_gender);
        rbFemale = findViewById(R.id.rd_female);
        spnStatus = findViewById(R.id.spn_status);
        edtDOB = findViewById(R.id.edt_date_of_birth);
        edtLastName = findViewById(R.id.edt_last_name);
        edtFirstName = findViewById(R.id.edt_first_name);
        spnBloodGroup = findViewById(R.id.spn_blood_group);
        btnAddPatient = findViewById(R.id.btn_add_patient);
        edtMobileNumber = findViewById(R.id.edt_mobile_number);
    }

    /**
     * set all click events of the screen
     */
    private void setupEvents() {
        btnAddPatient.setOnClickListener(view -> {
            if(validate())
                addNewPatient();
        });

        edtDOB.setOnClickListener(view -> {
            DTU.showDatePickerDialog(context, DTU.FLAG_OLD_AND_NEW, edtDOB);
        });

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            selectedGenderId = rgGender.getCheckedRadioButtonId();
            rbGender = findViewById(selectedGenderId);
        });
    }

    /**
     * validation for user input
     * @return
     */
    private boolean validate() {
        if(edtMobileNumber.getText().toString().length() <= 0){
            edtMobileNumber.setFocusable(true);
            edtMobileNumber.setError(Constant.ERR_MSG_MOBILE);
            return false;
        }else if(edtFirstName.getText().toString().length() <= 0){
            edtFirstName.setFocusable(true);
            edtFirstName.setError(Constant.ERR_MSG_FIRST_NAME);
            return false;
        }else if(edtLastName.getText().toString().length() <= 0){
            edtLastName.setFocusable(true);
            edtLastName.setError(Constant.ERR_MSG_LAST_NAME);
            return false;
        }else if(edtDOB.getText().toString().length() <= 0){
            edtDOB.setFocusable(true);
            edtDOB.setError(Constant.ERR_MSG_DATE_OF_BIRTH);
            return false;
        }else if(spnStatus.getSelectedItemPosition() == 0){
            Toast.makeText(context, Constant.ERR_MSG_STATUS, Toast.LENGTH_SHORT).show();
            return false;
        }else if(spnBloodGroup.getSelectedItemPosition() == 0){
            Toast.makeText(context, Constant.ERR_MSG_BLOOD_GROUP, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * add new patient to the database
     */
    private void addNewPatient() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.IS_DELETED, "N");
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.GENDER, getSelectedGender());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER, edtMobileNumber.getText().toString());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH, edtDOB.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME, edtLastName.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME, edtFirstName.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.STATUS, spnStatus.getSelectedItem().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP, spnBloodGroup.getSelectedItem().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.CREATE_DATE, Utils.getCurrentDateTime(Utils.DD_MM_YYYY));

            dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_PATIENTS, contentValues);

            goToPatientListing();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * after adding patient move to patient list screen
     */
    private void goToPatientListing() {
        context.startActivity(new Intent(this,PatientListActivity.class));
    }

    /**
     * get user selected gender
     * @return
     */
    private String getSelectedGender(){
        int radioButtonID = rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = rgGender.findViewById(radioButtonID);
        return radioButton.getText().toString();
    }

}