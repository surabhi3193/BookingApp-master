package com.example.mind_android.bookingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mind_android.bookingapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] country = {"+91", "+234", "+233", "+1", "+44",};
    EditText emailEt, fullnameEt, phoneNoET, passEt, cPasswordEt;

    public static boolean isMatch(String s, String patt) {
        Pattern pat = Pattern.compile(patt);
        Matcher m = pat.matcher(s);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        RelativeLayout next_signup = findViewById(R.id.next_signup);

        emailEt = findViewById(R.id.emailEt);
        fullnameEt = findViewById(R.id.nameET);
        phoneNoET = findViewById(R.id.phoneEt);
        passEt = findViewById(R.id.passEt);
        cPasswordEt = findViewById(R.id.cPassEt);


        next_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextActivity();
            }
        });


        Spinner spin = findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_items, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    private void nextActivity() {

        String name = fullnameEt.getText().toString();
        String phone = phoneNoET.getText().toString();
        String password = passEt.getText().toString();
        String cpassword = cPasswordEt.getText().toString();
        String em = emailEt.getText().toString();
        String empatt = "^[a-zA-Z0-9_.]+@[a-zA-Z]+\\.[a-zA-Z]+$";
        boolean b4 = isMatch(em, empatt);
        if (!b4) {
            emailEt.setError("Invalid Email ID");
            return;
        }
        if (!password.equals(cpassword)) {
            cPasswordEt.setError("Password Mismatch");

        }

        else
        {
            startActivity(new Intent(SignupActivity.this, BussinessSignUpActivity.class)
                    .putExtra("name",name)
                    .putExtra("phone",phone)
                    .putExtra("email",em)
                    .putExtra("password",password));
            finish();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), country[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this,EnterLoginActivity.class));
        finish();
    }
}
