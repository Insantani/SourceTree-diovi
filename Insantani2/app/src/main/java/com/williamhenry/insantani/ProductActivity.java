package com.williamhenry.insantani;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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

public class ProductActivity extends ActionBarActivity {
    private Context context;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private String url;
    private RequestQueue mQueue;
    int counter=1;
    private SharedPreferences pref;
    private Editor editor;
    private boolean checkToken;
    private boolean checkRefreshToken;
    private boolean tokenType;
    private boolean user_id;
    private RelativeLayout relativeLayout;

//    private ArrayList<Article> articles;
//    private ArrayList<Product> mItems;
    public static final String REQUEST_TAG = "RelatedItems";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);
        pref= getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        checkToken=pref.contains("access_token");
        checkRefreshToken=pref.contains("refresh_token");
        tokenType=pref.contains("token_type");
        user_id=pref.contains("user_id");
        relativeLayout= (RelativeLayout) findViewById(R.id.relativeLayoutProduct);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycle_view_relative_item);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Bundle extras= getIntent().getExtras();
        final Bundle item=(Bundle)extras.get("nature");
//        final Context context=getApplicationContext();
        final ArrayList<Product> relatedItems = new ArrayList<Product>();

        mQueue= CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        url="http://104.155.213.80/insantani/public/api/farmer/"+item.getString("fname")+"/products";
        Log.d("url_farmer",url);
        final CustomJSONObjectRequest jsonRequest= new CustomJSONObjectRequest(Request.Method.GET,
                url,new JSONObject(),
                new Response.Listener<JSONObject>(){
                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONArray result=response.getJSONArray("result");
//                        if(result.getString("next_page_url"))

//                            JSONArray data=result.getJSONArray("data");
//                            Log.d("response1", data.get(0).toString());
                            for (int i=0;i<result.length();i++){
                                final JSONObject dataDetail=(JSONObject)result.get(i);
                                url="http://104.155.213.80/insantani/public/"+dataDetail.getString("product_picture_url");
                                Log.d("url",url);
                                final ImageRequest imageRequest= new ImageRequest(url,
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                try{
                                                    Log.d("bitmap",bitmap.toString());
//                                                    Article article = new Article(dataDetail.getString("author"),
//                                                            dataDetail.getString("title"), dataDetail.getString("content"), bitmap);
////
//                                                    articles.add(article);
//                                                    mAdapter = new MyAdapter(articles, getContext());
//                                                    mRecyclerView.setAdapter(mAdapter);
                                                    Product product = new Product();
                                                    product.setFarmerName(dataDetail.getString("farmer_username"));
                                                    product.setDescription(dataDetail.getString("prod_desc"));
                                                    product.setName(dataDetail.getString("product_name"));
                                                    product.setPrice(dataDetail.getInt("prod_price"));
                                                    product.setStock(dataDetail.getInt("stock_num"));
                                                    product.setThumbnail(bitmap);
                                                    product.setId(dataDetail.getInt("id"));
                                                    product.setUom(dataDetail.getString("uom"));
                                                    product.setUrl(dataDetail.getString("product_picture_url"));
                                                    Log.d("id_data", Integer.toString(dataDetail.getInt("id")));
                                                    Log.d("item_id",Integer.toString(item.getInt("id")));
                                                    if (dataDetail.getInt("id")!= item.getInt("id"))
                                                        relatedItems.add(product);

                                                    mAdapter = new RelativeItemAdapter(relatedItems,getApplicationContext());
                                                    mRecyclerView.setAdapter(mAdapter);


//
                                                }catch(Exception e){
                                                    Log.d("error_picture",e.toString());

                                                }

                                            }
                                        },0,0,null,new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("error_response", error.toString());
                                    }
                                });
                                imageRequest.setTag(REQUEST_TAG);
                                mQueue.add(imageRequest);

                            }

                        } catch(Exception e){
                            Log.d("JSON_err",e.toString());
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("error_response",error.toString());
            }
        });
        jsonRequest.setTag(REQUEST_TAG);
        mQueue.add(jsonRequest);

        Log.d("item_name",item.getString("title"));
//        Log.d("thumbnail",item.getString("thumbnail"));

        final ImageView image= (ImageView)findViewById(R.id.img_thumbnail1);
//        int x=item.getInt("thumbnail");
//        Drawable draw=getResources().getDrawable(x);
//        byte [] encodeByte= Base64.decode(item.getString("thumbnail"), Base64.DEFAULT);
//        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);


        url="http://104.155.213.80/insantani/public/"+item.getString("url");
        Log.d("url_image_product", url);
        final ImageRequest imageRequest= new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        try{
                            Log.d("bitmap",bitmap.toString());
                            image.setImageBitmap(bitmap);
                        }catch(Exception e){
                            Log.d("error_picture_product",e.toString());

                        }

                    }
                },0,0,null,new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("error_response_image_product", error.toString());
            }
        });
        imageRequest.setTag(REQUEST_TAG);
        mQueue.add(imageRequest);


//        image.setImageBitmap(bitmap);

        TextView text1 = (TextView)findViewById(R.id.productname);
        text1.setText(item.getString("title"));

        TextView text2 = (TextView)findViewById(R.id.description);
        text2.setText(item.getString("description"));

        TextView text3 = (TextView)findViewById(R.id.farmername);
        text3.setText("By: " + item.getString("fname"));

        TextView text4 = (TextView)findViewById(R.id.price);
        text4.setText("Rp " + Integer.toString(item.getInt("price"))+ " / "+item.getString("uom"));

        TextView text5 = (TextView)findViewById(R.id.stock);
        text5.setText("Stock: " + Integer.toString(item.getInt("stock")));

        Button shopping = (Button) findViewById(R.id.shoppingcart_button);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkToken && !checkRefreshToken && !tokenType && !user_id) {
                    Snackbar snackbar = Snackbar.make(relativeLayout, "Login First", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);

                    View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);

                    snackbar.show();
                } else {
                    if (item.getInt("stock") > 0) {
                        LayoutInflater inflater = getLayoutInflater();

                        AlertDialog alertDialog = new AlertDialog.Builder(ProductActivity.this).create();
                        alertDialog.setView(inflater.inflate(R.layout.confirmation_add_to_shopping_cart_dialog, null));
                        alertDialog.setTitle("Quantity");

                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog1, int which) {
                                        final DialogInterface dialog = dialog1;
//                                    Intent intent = new Intent(context, CartFragment.class);
//                                    Bundle bundle = new Bundle();
                                        EditText text = (EditText) ((Dialog) dialog).findViewById(R.id.confirmation_quantity);
                                        text.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Integer.toString(item.getInt("stock")))});
                                        final int quantity = Integer.parseInt(text.getText().toString());


//                                    bundle.putString("ProductName", item.getString("title"));
//                                    bundle.putString("FarmerName", item.getString("fname"));
//                                    bundle.putFloat("price", item.getInt("price"));
//                                    bundle.putInt("image", item.getInt("thumbnail"));
//                                    bundle.putInt("qty", quantity);
//                                    intent.putExtra("Cart", bundle);


                                        //add to cart starts here
                                        url = "http://104.155.213.80/insantani/public/api/cart/add";
                                        final StringRequest stringRequestAddCart = new StringRequest(Request.Method.POST,
                                                url, new Response.Listener<String>() {
                                            //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                                            @Override
                                            public void onResponse(String response) {
                                                Log.d("add_cart", response.toString());
                                                try {
                                                    Log.d("response_add_cart", response.toString());
                                                    JSONObject jsonObject = new JSONObject(response.toString());
//                                                editor.putString("user_id",jsonObject.getString("user_id"));
//                            editor.putString("token_type",jsonObject.getString("token_type"));
//                            editor.putString("refresh_token", jsonObject.getString("refresh_token"));
//                                                editor.commit();
                                                    Log.d("message_add_cart", jsonObject.getString("message"));
//                            Log.d("token_type",jsonObject.getString("token_type"));
//                            Log.d("refresh_token",jsonObject.getString("refresh_token"));

//                            Snackbar snackbar = Snackbar.make(relativeLayout, "Login Success", Snackbar.LENGTH_LONG);
//                            snackbar.setActionTextColor(Color.WHITE);
//
//                            View snackbarView= snackbar.getView();
//                            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                            textView.setTextColor(Color.WHITE);
//
//                            snackbar.show();

//                                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                                startActivity(intent);

                                                    dialog.dismiss();
                                                } catch (Exception e) {
                                                    Log.d("JSON_error_add_cart", e.toString());
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Log.d("error_response_add_cart", error.toString());

                                                Snackbar snackbar = Snackbar.make(relativeLayout, "You have the same item in cart", Snackbar.LENGTH_LONG);
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
                                                Log.d("Auth_add_cart", auth);
                                                headers.put("Authorization", auth);
//                        params.put("email", email.getText().toString());
//
//                        params.put("password", password.getText().toString());
//                        params.put("scope","read");
//                        params.put("client_id", "testclient");
//                        params.put("client_secret","testpass");
                                                return headers;
                                            }

                                            ;

                                            protected Map getParams() {
                                                Map params = new HashMap();
                                                // the POST parameters:

                                                String user_id = pref.getString("user_id", null);
                                                params.put("product_id", Integer.toString(item.getInt("id")));
                                                params.put("user_id", user_id);

                                                params.put("product_quantity", Integer.toString(quantity));
//                                            params.put("scope","read");
//                                            params.put("client_id", "testclient");
//                                            params.put("client_secret","testpass");
                                                return params;
                                            }

                                            ;


                                        };


                                        stringRequestAddCart.setTag(REQUEST_TAG);
                                        mQueue.add(stringRequestAddCart);


                                    }
                                });
                        alertDialog.show();
                        Button plus = (Button) alertDialog.findViewById(R.id.increse_button);
                        Button min = (Button) alertDialog.findViewById(R.id.decrese_button);
                        EditText qty = (EditText) alertDialog.findViewById(R.id.confirmation_quantity);
                        qty.setFilters(new InputFilter[]{new InputFilterMinMax("1", Integer.toString(item.getInt("stock")))});
                        final TextView text = (TextView) alertDialog.findViewById(R.id.confirmation_quantity);
                        counter = Integer.parseInt(text.getText().toString());

                        qty.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {

                                // you can call or do what you want with your EditText here
                                if (!text.getText().toString().equals("")) {

                                    counter = Integer.parseInt(text.getText().toString());
                                }


                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }
                        });
                        plus.setOnClickListener(new View.OnClickListener() {
                                                    public void onClick(View view) {
                                                        if(counter<item.getInt("stock")) {
                                                            counter += 1;
                                                            text.setText("" + counter);
                                                        }else{
                                                            text.setText(Integer.toString(item.getInt("stock")));
                                                        }

                                                    }
                                                }

                        );
                        min.setOnClickListener(new View.OnClickListener()

                                               {

                                                   public void onClick(View view) {
                                                       if (counter <= 1) {
                                                           text.setText("1");
                                                       }
                                                       if (counter > 1) {
                                                           counter -= 1;
                                                           text.setText("" + counter);
                                                       }
                                                   }
                                               }

                        );
                    } else {
                        Snackbar snackbar = Snackbar.make(relativeLayout, "Item out of stocks", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);

                        View snackbarView = snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);

                        snackbar.show();

                    }

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        TextView title = (TextView) findViewById(R.id.productname);
        setTitle(title.getText());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
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
