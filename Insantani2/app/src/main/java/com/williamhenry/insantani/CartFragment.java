package com.williamhenry.insantani;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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


public class CartFragment extends android.support.v4.app.Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CartAdapter mAdapter;

    private RelativeLayout relativeLayout;
    private String url;
    private RequestQueue mQueue;
    private ArrayList<Cart> cart;
    public static final String REQUEST_TAG = "ShoppingCart";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean checkToken;
    private boolean checkRefreshToken;
    private boolean tokenType;
    private boolean userId;
    private int totalPrice;
    private int totalItem;
    private TextView totalprice;
    private TextView totalitem;
    private CartFragment cartFragment;



    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        pref=getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor=pref.edit();

        checkToken= pref.contains("access_token");
        tokenType=pref.contains("token_type");
        checkRefreshToken=pref.contains("refresh_token");

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        relativeLayout=(RelativeLayout) rootView.findViewById(R.id.relativeLayoutCart);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        totalprice= (TextView) rootView.findViewById(R.id.totalprice);
        totalitem= (TextView) rootView.findViewById(R.id.totalitem);
        cart = new ArrayList<Cart>();

        final CartFragment thisObj = this;

        mQueue= CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        url="http://104.155.213.80/insantani/public/api/cart?user_id="+pref.getString("user_id",null);
        final StringRequest stringRequestCart= new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>(){
            //                    private ArrayList<Article> articles1=new ArrayList<Article>();
            @Override
            public void onResponse(String response){
                Log.d("cart", response.toString());
                try {
                    Log.d("response_cart", response.toString());
                    JSONObject jsonObject1= new JSONObject(response.toString());
                    JSONArray jsonArray= jsonObject1.getJSONArray("data");

                    for (int i=0;i<jsonArray.length();i++){
                        final JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                        Log.d("product_id", Integer.toString(jsonObject.getInt("product_id")));
//                        Log.d("user_id", jsonObject.getString("user_id"));
                        Log.d("product_quantity", jsonObject.getString("product_quantity"));


                        url="http://104.155.213.80/insantani/public/api/products/"+Integer.toString(jsonObject.getInt("product_id"));
                        final CustomJSONObjectRequest jsonRequestProductDetail= new CustomJSONObjectRequest(Request.Method.GET,
                                url,new JSONObject(),
                                new Response.Listener<JSONObject>(){
                                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                                    @Override
                                    public void onResponse(JSONObject response){
                                        try {
                                            JSONArray resultProductDetail=response.getJSONArray("result");
//                        if(result.getString("next_page_url"))

//                                            JSONArray dataProduct=resultProduct.getJSONArray("data");
                                            Log.d("response_products_detail", resultProductDetail.get(0).toString());
                                            for (int i=0;i<resultProductDetail.length();i++){
                                                final JSONObject dataDetailProduct=(JSONObject)resultProductDetail.get(i);
                                                url="http://104.155.213.80/insantani/public/"+dataDetailProduct.getString("product_picture_url");
//                                Log.d("url_product",url);
                                                final ImageRequest imageRequest= new ImageRequest(url,
                                                        new Response.Listener<Bitmap>() {
                                                            @Override
                                                            public void onResponse(Bitmap bitmap) {
                                                                try{
                                                                    Log.d("bitmap",bitmap.toString());
                                                                    Cart cartItem = new Cart(dataDetailProduct.getString("product_name"),jsonObject.getInt("product_quantity"),
                                                                            dataDetailProduct.getString("farmer_username"), (float)dataDetailProduct.getInt("prod_price"),bitmap,
                                                                            dataDetailProduct.getInt("id"),dataDetailProduct.getString("uom"), dataDetailProduct.getInt("stock_num"));


                                                                    totalPrice+=(jsonObject.getInt("product_quantity") * dataDetailProduct.getInt("prod_price"));

//                                                                    product.setFarmerName(dataDetailProduct.getString("farmer_username"));
//                                                                    product.setDescription(dataDetailProduct.getString("prod_desc"));
//                                                                    product.setName(dataDetailProduct.getString("product_name"));
//                                                                    product.setPrice(dataDetailProduct.getInt("prod_price"));
//                                                                    product.setStock(dataDetailProduct.getInt("stock_num"));
//                                                                    product.setId(dataDetailProduct.getInt("id"));
//                                                                    product.setThumbnail(bitmap);
//
                                                                    cart.add(cartItem);

                                                                    mAdapter = new CartAdapter(cart,getContext(),mQueue,getActivity().getSupportFragmentManager(),thisObj);
                                                                    mRecyclerView.setAdapter(mAdapter);

                                                                    totalprice.setText(Integer.toString(totalPrice));
                                                                    totalitem.setText(Integer.toString(cart.size()));
                                                                    totalItem=cart.size();



                                                                        Button next = (Button) rootView.findViewById(R.id.next);
                                                                        next.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                if(totalItem>0) {
                                                                                    Intent intent = new Intent(getContext(), CheckoutActivity.class);
                                                                                    Bundle bundle = new Bundle();
//                                                                            ListBridge listBridge= new ListBridge(cart);
//
                                                                                    bundle.putString("totalPrice", Integer.toString(totalPrice));
                                                                                    bundle.putString("totalItem", Integer.toString(cart.size()));
                                                                                    intent.putExtra("information", bundle);
                                                                                    Log.d("totalPrice", Integer.toString(totalPrice));
                                                                                    Log.d("totalItem", Integer.toString(totalItem));
//////                                                                            Log.d("cart",cart.);
//                                                                            intent.putExtra(bundle);

                                                                                    getContext().startActivity(intent);
                                                                                }
                                                                            }
                                                                        });




//                                                                    mAdapter = new GridAdapter(mItems,getContext());
//                                                                    mRecyclerView1.setAdapter(mAdapter);




                                                                }catch(Exception e){
                                                                    Log.d("error_picture_cart",e.toString());

                                                                }

                                                            }
                                                        },0,0,null,new Response.ErrorListener() {
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("error_response_cart", error.toString());
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
                        jsonRequestProductDetail.setTag(REQUEST_TAG);
                        mQueue.add(jsonRequestProductDetail);



//                        Cart cartData=new Cart (jsonObject.getString(""),jsonObject.get);

                    }
//                                                editor.putString("user_id",jsonObject.getString("user_id"));
//                            editor.putString("token_type",jsonObject.getString("token_type"));
//                            editor.putString("refresh_token", jsonObject.getString("refresh_token"));
//                                                editor.commit();




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

//                    dialog.dismiss();
                } catch(Exception e){
                    Log.d("JSON_error_cart",e.toString());
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

                Log.d("error_response_cart",error.toString());

                Snackbar snackbar = Snackbar.make(relativeLayout, "Something wrong", Snackbar.LENGTH_LONG);
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
//                        params.put("email", email.getText().toString());
//
//                        params.put("password", password.getText().toString());
//                        params.put("scope","read");
//                        params.put("client_id", "testclient");
//                        params.put("client_secret","testpass");
                return headers;
            };

//            protected Map getParams(){
//                Map params = new HashMap();
//                // the POST parameters:
//
//                String user_id= pref.getString("user_id",null);
////                params.put("product_id",Integer.toString(item.getInt("id")));
//                params.put("user_id", user_id);
//
////                params.put("product_quantity",Integer.toString( quantity));
////                                            params.put("scope","read");
////                                            params.put("client_id", "testclient");
////                                            params.put("client_secret","testpass");
//                return params;
//            };


        };


        stringRequestCart.setTag(REQUEST_TAG);
        mQueue.add(stringRequestCart);












        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume(){
        super.onResume();



    }

    public void refresh() {
        Log.d("apasih", "koklama");
        totalitem.setText(Integer.toString(mAdapter.getItemCount()));
        int newTotal = 0;

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            Cart c = mAdapter.getItem(i);
            newTotal += c.getQty() * c.getPrice();
            System.out.println("HAHAHA" + c.getProductName());
        }
        totalprice.setText(Integer.toString(newTotal));
        totalPrice=newTotal;
        totalItem=mAdapter.getItemCount();
    }
}