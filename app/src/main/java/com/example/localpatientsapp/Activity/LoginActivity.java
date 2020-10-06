package com.example.localpatientsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.localpatientsapp.R;
import com.example.localpatientsapp.Utils.Constant;
import com.example.localpatientsapp.Utils.Utils;

/**
 * created by ketan 06-10-2020
 */

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnCreatePin;
    private EditText edtEnterPin;
    private EditText edtCreatePin;
    private LinearLayout llEnterPin;
    private LinearLayout llCreatePin;
    private SharedPreferences preferenceUser;
    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI();
        preferences();
        setupEvents();
        setLayoutVisibility();
    }

    /**
     * visibility of the views as per first or existing user
     */
    private void setLayoutVisibility() {
        if(!preferenceUser.getString(Constant.PIN,"").equals("")){
            llEnterPin.setVisibility(View.VISIBLE);
        }else{
            llCreatePin.setVisibility(View.VISIBLE);
        }
    }

    /**
     * set all the UI elements of the screen
     */
    private void setupUI() {
        btnLogin = findViewById(R.id.btn_login);
        llEnterPin = findViewById(R.id.ll_enter_pin);
        llCreatePin = findViewById(R.id.ll_create_pin);
        edtCreatePin = findViewById(R.id.edt_enter_pin);
        btnCreatePin = findViewById(R.id.btn_create_pin);
        edtEnterPin = findViewById(R.id.edt_enter_login_pin);
    }

    /**
     * initialization of shared preference
     */
    private void preferences() {
        preferenceUser = getSharedPreferences(Utils.PREFERENCE_USER, MODE_PRIVATE);
    }

    /**
     * set up all the click events of the screen
     */
    private void setupEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateLogin())
                    doLogin();
            }
        });

        btnCreatePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    storeUserPin(edtCreatePin.getText().toString().trim());
                    finish();
                }
            }
        });
    }

    /**
     * validation for the entered pin for existing user
     */
    private boolean validateLogin() {
        if (edtEnterPin.getText().toString().length() <= 0) {
            edtEnterPin.setError(Constant.ERR_MSG_PIN);
            edtEnterPin.setFocusable(true);
            return false;
        }
        return true;
    }

    /**
     * do login if the entered pin matched with the stored pin
     */
    private void doLogin() {
        if(edtEnterPin.getText().toString().trim().equals(preferenceUser.getString(Constant.PIN,""))){
            context.startActivity(new Intent(context, PatientListActivity.class));
            finish();
        }
    }

    /**
     * validate entered pin while creating pin
     * @return
     */
    private boolean validate() {
        if (edtCreatePin.getText().toString().length() <= 0) {
            edtCreatePin.setError(Constant.ERR_MSG_PIN);
            edtCreatePin.setFocusable(true);
            return false;
        }
        return true;
    }

    /**
     * store the pin first time when creating the user
     */
    private void storeUserPin(String pin) {
        SharedPreferences.Editor editor = preferenceUser.edit();
        editor.putString(Constant.PIN, pin);
        editor.commit();

        //Move to MainScreen
        context.startActivity(new Intent(context, PatientListActivity.class));
    }


}