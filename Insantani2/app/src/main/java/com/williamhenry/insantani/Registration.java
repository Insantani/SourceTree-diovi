package com.williamhenry.insantani;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.Button;

/**
 * Created by agungwy on 10/29/2015.
 */
public class Registration extends Activity {
    private String url;
    private RequestQueue mQueue;
    private Context context;
    private LinearLayout linearLayout;
//    private ArrayList<Article> articles;
//    private ArrayList<Product> mItems;
    public static final String REQUEST_TAG = "Registration";
    private TextView email,password, verifyPassword,name,phoneNumber, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayout= (LinearLayout) findViewById(R.id.linearLayoutRegistration);
        Bundle extras = getIntent().getExtras();
        Bundle item = (Bundle) extras.get("User");
        email = (TextView) findViewById(R.id.register_email);
        password = (TextView) findViewById(R.id.register_password);
        name = (TextView)findViewById(R.id.register_name);
        verifyPassword = (TextView) findViewById(R.id.register_verifyPassword);
        phoneNumber = (TextView) findViewById(R.id.register_phoneNumber);
        address = (TextView) findViewById(R.id.register_address);
//        email.setText(item.getString("Email"));
//        password.setText(item.getString("Password"));


        context=this;
        Button register = (Button) findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("email", email.getText().toString());
                Log.d("password", password.getText().toString());
                Log.d("verify_password", verifyPassword.getText().toString());
                Log.d("phone_number", phoneNumber.getText().toString());
                Log.d("address", address.getText().toString());

                mQueue= CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                url="http://104.155.213.80/insantani/public/api/register";
                final StringRequest stringRequest= new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>(){
                            //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                            @Override
                            public void onResponse(String response){
                                Log.d("register",response.toString());
                                try {
                                    Log.d("response_register", response.toString());
                                    JSONObject jsonObject= new JSONObject(response.toString());
                                    if(jsonObject.has("message")) {
                                        String message = jsonObject.getString("message");
                                        Log.d("JSON_response_register", jsonObject.getString("message"));
//                                      String message=response.getString("message");
//
                                        if (message.equals("Registration Complete!")) {
                                            Snackbar snackbar = Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG);
                                            snackbar.setActionTextColor(Color.WHITE);

                                            View snackbarView = snackbar.getView();
                                            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                            textView.setTextColor(Color.WHITE);

                                            snackbar.show();
                                        }

                                    }else if(jsonObject.has("password") &&!jsonObject.has("name") &&
                                            !jsonObject.has("password_confirmation") && !jsonObject.has("address") &&
                                            !jsonObject.has("phone_number") &&!jsonObject.has("email")) {
                                        JSONArray password = jsonObject.getJSONArray("password");
                                        Log.d("JSON_response_register_password", password.get(0).toString());
//                                      String message=response.getString("message");
//

                                        Snackbar snackbar = Snackbar.make(linearLayout, password.get(0).toString(), Snackbar.LENGTH_LONG);
                                        snackbar.setActionTextColor(Color.WHITE);

                                        View snackbarView = snackbar.getView();
                                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                        textView.setTextColor(Color.WHITE);

                                        snackbar.show();
                                    }else{
                                        Snackbar snackbar = Snackbar.make(linearLayout, "Please enter the missing field", Snackbar.LENGTH_LONG);
                                        snackbar.setActionTextColor(Color.WHITE);

                                        View snackbarView = snackbar.getView();
                                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                        textView.setTextColor(Color.WHITE);

                                        snackbar.show();
                                    }

                                } catch(Exception e){
                                    Log.d("JSON_error_register",e.toString());
                                }
                            }
                        },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Log.d("error_response_register",error.toString());
                        Snackbar snackbar = Snackbar.make(linearLayout, "Email has been used", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);

                        View snackbarView= snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);

                        snackbar.show();
                    }
                }){


                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String,String>();
                        // the POST parameters:
                        params.put("name",name.getText().toString());
                        params.put("email", email.getText().toString());

                        params.put("password", password.getText().toString());
                        params.put("password_confirmation",verifyPassword.getText().toString());
                        params.put("address", address.getText().toString());
                        params.put("phone_number",phoneNumber.getText().toString());
                        return params;
                    };


                };


                stringRequest.setTag(REQUEST_TAG);
                mQueue.add(stringRequest);




//                Intent intent= new Intent(context,MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("Email", email.getText().toString());
//                bundle.putString("Password", password.getText().toString());
//                intent.putExtra("User", bundle);
//                context.startActivity(intent);
            }
        });



    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.) {
//            return true;
//        }

        switch (id) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }
}
