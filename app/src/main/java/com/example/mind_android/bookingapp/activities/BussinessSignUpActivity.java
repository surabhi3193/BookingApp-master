package com.example.mind_android.bookingapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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

public class BussinessSignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] bussiness = { "Services","Retail" , };
    EditText buss_nameET,cityEt,countryEt;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_sign_up);

         spin =  findViewById(R.id.spinner2);
         buss_nameET =  findViewById(R.id.buss_nameET);
         cityEt =  findViewById(R.id.cityEt);
         countryEt =  findViewById(R.id.countryEt);
      TextView login_link =  findViewById(R.id.login_link);

        RelativeLayout signup_btn= findViewById(R.id.signup_btn);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_items,bussiness);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);



        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BussinessSignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                nextActivity();
            }
        });
    }

    private void nextActivity() {
        
        String buss_name = buss_nameET.getText().toString();
        String city = cityEt.getText().toString();
        String country = countryEt.getText().toString();
        String buss_type = spin.getSelectedItem().toString();


        if (buss_type.equals("Services"))
            buss_type="1";
        else if (buss_type.equals("Retails"))
            buss_type="2";
        
        
        Bundle bundle = getIntent().getExtras();
        
        if (bundle!=null)
        {
            String name = bundle.getString("name");
            String phone =bundle.getString("phone");
            String email =bundle.getString("email");
            String password = bundle.getString("password");
            
            registerUser(buss_name,city,country,buss_type,name,phone,password,email);
        }
    }

    private void registerUser(String buss_name, String city, String country, String buss_type, String name,
                              String phone, String password,String email) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        final ProgressDialog ringProgressDialog;
        ringProgressDialog = ProgressDialog.show(BussinessSignUpActivity.this, "Please wait ...",
                "Loading Post", true);
        ringProgressDialog.setCancelable(false);
        params.put("business_name", buss_name);
        params.put("full_name", name);
        params.put("business_location", city);
        params.put("business_country",  country);
        params.put("business_pass", password);
        params.put("business_phone", phone);
        params.put("business_type", buss_type);
        params.put("user_device_type", "1");
        params.put("user_device_token", "5482746982y");
        params.put("user_device_id", "3466135346");
        params.put("business_email", email);
        client.post(BASE_URL_NEW + "signup", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                System.out.println(" ************* signup response ***");
                System.out.println(response);
                ringProgressDialog.dismiss();
                try {

                    if (response.getString("status").equals("0")) {
                        Toast.makeText(BussinessSignUpActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();

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
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),bussiness[position] ,Toast.LENGTH_LONG).show();
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
      startActivity(new Intent(BussinessSignUpActivity.this,SignupActivity.class));
      finish();
    }
}