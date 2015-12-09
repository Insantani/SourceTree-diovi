package com.williamhenry.insantani;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean user_id;
    private boolean checkToken;
    private boolean checkRefreshToken;
    private boolean tokenType;
    private TextView address;
    private TextView zipcode;
    private Button buy;
    private TextView totalPrice;
    private TextView total;
    private String url;
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "CheckoutActivity";
    ArrayList<JSONObject> carts;
    private StringEntity stringEntity;
    private LinearLayout linearLayout;
    private CartFragment cartFragment;
    private TextView totalPriceInput;
    private TextView totalInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref=getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        user_id=pref.contains("user_id");
        checkToken=pref.contains("access_token");
        checkRefreshToken=pref.contains("refresh_token");
        tokenType=pref.contains("token_type");
        linearLayout=(LinearLayout) findViewById(R.id.linearLayoutCheckout);
        cartFragment= new CartFragment();

        address= (TextView) findViewById(R.id.location);
        zipcode= (TextView) findViewById(R.id.postcode);
        buy=(Button) findViewById(R.id.buyButton);
        total=(TextView) findViewById(R.id.total);
        totalPrice= (TextView) findViewById(R.id.totalprice);
        totalPriceInput=(TextView)findViewById(R.id.totalPriceInput);
        totalInput= (TextView) findViewById(R.id.totalInput);

//        Bundle extras= getIntent().getExtras();
//        final Bundle item=(Bundle)extras.get("nature");
        Bundle extras=getIntent().getExtras();
        final Bundle item=(Bundle)extras.get("information");

        totalPriceInput.setText("Rp "+item.getString("totalPrice"));
        totalInput.setText(item.getString("totalItem"));
//        Log.d("cart",item.getStringArray("cart").toString());


        carts = new ArrayList<JSONObject>();

        mQueue= CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        url="http://104.155.213.80/insantani/public/api/cart?user_id="+pref.getString("user_id",null);
        final StringRequest stringRequestCart= new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>(){
            //                    private ArrayList<Article> articles1=new ArrayList<Article>();
            @Override
            public void onResponse(String response) {
                Log.d("checkout_item", response.toString());
                try {
                    JSONObject jsonObjectData = new JSONObject(response.toString());
                    JSONArray jsonArrayData = jsonObjectData.getJSONArray("data");
                    for (int i = 0; i < jsonArrayData.length(); i++) {
                        carts.add((JSONObject) jsonArrayData.get(i));
                    }

//                    totalInput.setText(Integer.toString(carts.size()));


//                    if (!item.getString("totalItem").equals("0") && !address.getText().toString().equals("") && !zipcode.getText().toString().equals("")){


                        buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!item.getString("totalItem").equals("0") && !address.getText().toString().equals("") && !zipcode.getText().toString().equals("")){
                                ArrayList<JSONObject> array = new ArrayList<JSONObject>();


                                for (int i = 0; i < carts.size(); i++) {
                                    try {
                                        JSONObject sendObject = new JSONObject();
                                        sendObject.put("product_id", carts.get(i).getString("product_id"));
                                        sendObject.put("productQty", carts.get(i).getInt("product_quantity"));
                                        sendObject.put("user_id", pref.getString("user_id", null));
                                        sendObject.put("address", address.getText().toString());
                                        sendObject.put("zipcode", zipcode.getText().toString());
                                        array.add(sendObject);
                                    } catch (Exception e) {
                                        Log.d("translation_error", e.toString());
                                    }
                                    //            temp.add(sendObject);

                                }
                                JSONArray jsonArrayItems = new JSONArray(array);
                                JSONObject params = new JSONObject();
                                // the POST parameters:
                                Log.d("params", jsonArrayItems.toString());
//                Log.d("data", jsonArrayItems.toString());
                                try {
                                    params.put("data", jsonArrayItems);
                                } catch (Exception e) {
                                    Log.d("error_json_request", e.toString());
                                }


//    mQueue= CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                                url = "http://104.155.213.80/insantani/public/api/checkout";
                                final JsonObjectRequest jsonRequestCheckout = new JsonObjectRequest(Request.Method.POST,
                                        url, params, new Response.Listener<JSONObject>() {
                                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("checkout", response.toString());
                                        try {
                                            Log.d("response_checkout", response.toString());
//                JSONObject jsonObjectMessage= new JSONObject(response.toString());
                                            String message = response.getString("message");
//                                Log.d("JSON_response_checkout", jsonObject.getString("message"));
//                                      String message=response.getString("message");
//
                                            if (message.equals("success")) {
//                                            Snackbar snackbar = Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG);
//                                            snackbar.setActionTextColor(Color.WHITE);
//
//                                            View snackbarView = snackbar.getView();
//                                            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                                            textView.setTextColor(Color.WHITE);
//
//                                            snackbar.show();
                                                LayoutInflater inflater = getLayoutInflater();

                                                AlertDialog alertDialog = new AlertDialog.Builder(CheckoutActivity.this).create();
                                                alertDialog.setView(inflater.inflate(R.layout.cart_warn, null));
                                                alertDialog.setTitle("Warning");
                                                alertDialog.setMessage("You have successfully purchase items");

                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                dialog.dismiss();
                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                alertDialog.show();
//                                            getSupportFragmentManager().beginTransaction().detach(cartFragment).attach(cartFragment).commit();
                                            }else if(message.equals("out of stocks")){
                                                JSONArray itemOut= response.getJSONArray("data");
                                                int sizeItemOut= itemOut.length();
                                                int leftOver= Integer.valueOf(item.getString("totalItem"))-sizeItemOut;
                                                LayoutInflater inflater = getLayoutInflater();

                                                AlertDialog alertDialog = new AlertDialog.Builder(CheckoutActivity.this).create();
                                                alertDialog.setView(inflater.inflate(R.layout.cart_warn_limited, null));
                                                alertDialog.setTitle("Warning");

//                                                TextView warn= (TextView) alertDialog.findViewById(R.id.warn);
//                                                warn.setText("Can only process "+Integer.toString(leftOver)+" items due to limited stock");
                                                alertDialog.setMessage("Can only process "+Integer.toString(leftOver)+" items due to limited stock");

                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                dialog.dismiss();
                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                                alertDialog.show();

                                            }


                                        } catch (Exception e) {
                                            Log.d("JSON_error_checkout", e.toString());
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.d("error_response_checkout", error.toString());
                                        Snackbar snackbar = Snackbar.make(linearLayout, "Something wrong", Snackbar.LENGTH_LONG);
                                        snackbar.setActionTextColor(Color.WHITE);

                                        View snackbarView = snackbar.getView();
                                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                        textView.setTextColor(Color.WHITE);

                                        snackbar.show();
                                    }
                                }) {

                                    @Override
                                    public Map<String, String> getHeaders() {
                                        Map<String, String> headers = new HashMap<String, String>();
                                        // the POST parameters:
                                        String auth = "Bearer " + pref.getString("access_token", null);
                                        Log.d("Auth_cart", auth);
                                        headers.put("Authorization", auth);

                                        return headers;
                                    }

                                    ;

                                };


                                jsonRequestCheckout.setTag(REQUEST_TAG);
                                mQueue.add(jsonRequestCheckout);


                            }else{
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Please input the missing fields", Snackbar.LENGTH_LONG);
                                    snackbar.setActionTextColor(Color.WHITE);

                                    View snackbarView = snackbar.getView();
                                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.WHITE);

                                    snackbar.show();
                                }
                        }


                        });
//                }else{
//                        Snackbar snackbar = Snackbar.make(linearLayout, "Something wrong", Snackbar.LENGTH_LONG);
//                        snackbar.setActionTextColor(Color.WHITE);
//
//                        View snackbarView= snackbar.getView();
//                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        textView.setTextColor(Color.WHITE);
//
//                        snackbar.show();
//                    }


                } catch (Exception e) {
                    Log.d("checkout_item_error", e.toString());
                }
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

                Log.d("error_response_checkout_item",error.toString());

                Snackbar snackbar = Snackbar.make(linearLayout, "Something wrong", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);

                View snackbarView= snackbar.getView();
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                snackbar.show();
            }
        }){

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<String,String>();
                // the POST parameters:
                String auth="Bearer "+pref.getString("access_token",null);
                Log.d("Auth_cart",auth);
                headers.put("Authorization",auth);

                return headers;
            };




        };


        stringRequestCart.setTag(REQUEST_TAG);
        mQueue.add(stringRequestCart);


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
