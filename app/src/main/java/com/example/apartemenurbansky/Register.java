package com.example.apartemenurbansky;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText name, edit_username,   edit_password, c_password;
    Button buttonregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name= findViewById(R.id.name);
        edit_username= findViewById(R.id.edit_username);
        edit_password= findViewById(R.id.edit_password);
        c_password= findViewById(R.id.c_password);
        buttonregister= findViewById(R.id.buttonregister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = name.getText().toString();
                String txtEdit_username = edit_username.getText().toString();
                String txtEdit_password = edit_password.getText().toString();
                String txtC_password = c_password.getText().toString();
                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtEdit_username) ||
                        TextUtils.isEmpty(txtEdit_password) || TextUtils.isEmpty(txtC_password)) {
                    Toast.makeText(Register.this, "Isi data dengan lengkap", Toast.LENGTH_SHORT).show();
                } else {
                    registerNewAccount(txtName, txtEdit_username, txtEdit_password, txtC_password);
                }
            }
        }
        );
    }
    private void registerNewAccount(final String name, final String edit_username, final String edit_password, final String c_password){
        final ProgressDialog progressDialog = new ProgressDialog( Register.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering New Account");
        progressDialog.show();
        String uRl = "http://192.168.1.103/android_register_login/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")){
                    progressDialog.dismiss();
                    Toast.makeText( Register.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent( Register.this,Login.class));
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText( Register.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                progressDialog.dismiss();
                Toast.makeText( Register.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("name", name);
                param.put("username", edit_username);
                param.put("password", edit_password);
                param.put("c_pass", c_password);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Register.this).addToRequestQueue(request);
    }
}
