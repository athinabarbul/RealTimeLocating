package com.tutorial.athina.pethood;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "Register Activity";
    public static int idUser = 1 ;
    public static int idLogin = 1;

    private EditText nameUser, phoneUser, ageUser, emailUser, passUser;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        nameUser = (EditText) findViewById(R.id.name);
        phoneUser = (EditText) findViewById(R.id.phone);
        ageUser = (EditText) findViewById(R.id.age);

        emailUser = (EditText) findViewById(R.id.email);
        passUser = (EditText) findViewById(R.id.password);

        registerButton = (Button) findViewById(R.id.btnSignUp);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        DbHelperUsers dbHelperUsers = new DbHelperUsers(this);
        SQLiteDatabase dbUsers = dbHelperUsers.getWritableDatabase();
        ContentValues valuesUsers = new ContentValues();

        DbHelperLogin dbHelperLogin = new DbHelperLogin(this);
        SQLiteDatabase dbLogin = dbHelperLogin.getWritableDatabase();
        ContentValues valuesLogin = new ContentValues();

        valuesUsers.clear();
        valuesUsers.put(UsersContract.Users.ID, idUser++);
        valuesUsers.put(UsersContract.Users.NAME, nameUser.getText().toString());
        valuesUsers.put(UsersContract.Users.PHONE, phoneUser.getText().toString());
        valuesUsers.put(UsersContract.Users.AGE, ageUser.getText().toString());

        Uri uriUser = getContentResolver().insert(UsersContract.CONTENT_URI, valuesUsers);

        if (uriUser != null) {
            Log.d(TAG, String.format("%s %s %s", nameUser.getText().toString(), phoneUser.getText().toString(),ageUser.getText().toString()));
        }

        valuesLogin.clear();
        valuesLogin.put(LoginContract.Login.ID, idLogin++);
        valuesLogin.put(LoginContract.Login.EMAIL, emailUser.getText().toString());
        valuesLogin.put(LoginContract.Login.PASSWORD, passUser.getText().toString());

        Uri uriPassword = getContentResolver().insert(LoginContract.CONTENT_URI, valuesLogin);

        if (uriPassword != null) {
            Log.d(TAG, String.format("%s %s ", emailUser.getText().toString(), passUser.getText().toString()));
        }

        startActivity(new Intent(this,RegisterDogActivity.class));

    }
}
