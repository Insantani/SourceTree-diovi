package com.williamhenry.insantani;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import butterknife.ButterKnife;
//import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;
    private ScrollView scrollView;
    private String url;
    private RequestQueue mQueue;
    private SharedPreferences pref;
    private Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ButterKnife.inject(this);
        scrollView = (ScrollView) findViewById(R.id.scrollViewLogin);
        loginButton = (Button) findViewById(R.id.btn_login);
        passwordText = (EditText) findViewById(R.id.input_password);
        emailText = (EditText) findViewById(R.id.input_email);
        signupLink = (TextView) findViewById(R.id.link_signup);
        pref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                login();

                mQueue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                url = "http://104.155.213.80/insantani/public/oauth/token";
                final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>() {
                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                    @Override
                    public void onResponse(String response) {
                        Log.d("login", response.toString());
                        try {
                            Log.d("response_login", response.toString());
                            JSONObject jsonObject = new JSONObject(response.toString());
                            editor.putString("access_token", jsonObject.getString("access_token"));
                            editor.putString("token_type", jsonObject.getString("token_type"));
                            editor.putString("refresh_token", jsonObject.getString("refresh_token"));
                            Log.d("access_token", jsonObject.getString("access_token"));
                            Log.d("token_type", jsonObject.getString("token_type"));
                            Log.d("refresh_token", jsonObject.getString("refresh_token"));

                            Snackbar snackbar = Snackbar.make(scrollView, "Login Success", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(Color.WHITE);

                            View snackbarView = snackbar.getView();
                            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);

                            snackbar.show();


                        } catch (Exception e) {
                            Log.d("JSON_error_login", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error_response_login", error.toString());
                        Snackbar snackbar = Snackbar.make(scrollView, "Login Failed", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);

                        View snackbarView = snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);

                        snackbar.show();
                    }
                }) {


                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        // the POST parameters:
                        params.put("grant_type", "password");
                        params.put("email", emailText.getText().toString());

                        params.put("password", passwordText.getText().toString());
                        params.put("scope", "read");
                        params.put("client_id", "testclient");
                        params.put("client_secret", "testpass");
                        return params;
                    }

                    ;


                };
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                Bundle bundle = new Bundle();
                bundle.putString("Email", emailText.getText().toString());
                bundle.putString("Password", passwordText.getText().toString());
                intent.putExtra("User", bundle);
//                                                   context.startActivity(intent);
                startActivity(intent);
            }
        });


//    public void login() {
//        Log.d(TAG, "Login");
//
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

        loginButton.setEnabled(false);
    }

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        // TODO: Implement your own authentication logic here.
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity

        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

//    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//
//        _loginButton.setEnabled(true);
//    }

//    public boolean validate() {
//        boolean valid = true;
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
//        return valid;
//    }
}