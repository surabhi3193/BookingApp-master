package com.example.mind_android.bookingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mind_android.bookingapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.mind_android.bookingapp.Constant.NetWorkClass.BASE_URL_NEW;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] country = {"+91", "+234", "+233", "+1", "+44",};
    EditText  phoneNoET, passEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNoET = findViewById(R.id.phoneEt);
        passEt = findViewById(R.id.passEt);
        TextView create_account = findViewById(R.id.create_account);


        RelativeLayout login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextActivity();
            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          startActivity(new Intent(LoginActivity.this,SignupActivity.class));
          finish();
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


        String phone = phoneNoET.getText().toString();
        String password = passEt.getText().toString();
       if (phone.length()!=0 && password.length()!=0)
       {
           loginUser(phone,password);
       }
        else
        {
           if (phone.length()==0)
               phoneNoET.setError("Field Required");
           else if(password.length()==0)
                   passEt.setError("Field Required");
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private void loginUser(final  String phone,final  String password) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Please wait ...",
                "Loading Post", true);
        ringProgressDialog.setCancelable(false);

        params.put("business_phone", phone);
        params.put("business_pass", password);
        params.put("user_device_type", "1");
        params.put("user_device_token", "5482746982y");
        params.put("user_device_id", "3466135346");

        client.post(BASE_URL_NEW + "login", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                System.out.println(" ************* signup response ***");
                System.out.println(response);
                ringProgressDialog.dismiss();
                try {

                    if (response.getString("status").equals("0")) {
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ringProgressDialog.dismiss();
            }

        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this,EnterLoginActivity.class));
        finish();
    }
}
