package com.williamhenry.insantani;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ShopTabFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private String url;
    public static final String REQUEST_TAG = "HomeFragment";
    private RequestQueue mQueue;
    private ArrayList<Product> mItems;

    public ShopTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_tab, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView




        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mItems = new ArrayList<Product>();
        mQueue= CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        url="http://104.155.213.80/insantani/public/api/products?page=0&limit=3";
        final CustomJSONObjectRequest jsonRequestProduct= new CustomJSONObjectRequest(Request.Method.GET,
                url,new JSONObject(),
                new Response.Listener<JSONObject>(){
                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONObject resultProduct=response.getJSONObject("result");
//                        if(result.getString("next_page_url"))

                            JSONArray dataProduct=resultProduct.getJSONArray("data");
                            Log.d("response_products", dataProduct.get(0).toString());
                            for (int i=0;i<dataProduct.length();i++){
                                final JSONObject dataDetailProduct=(JSONObject)dataProduct.get(i);
                                url="http://104.155.213.80/insantani/public/"+dataDetailProduct.getString("product_picture_url");
//                                Log.d("url_product",url);
                                final ImageRequest imageRequest= new ImageRequest(url,
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                try{
                                                    Log.d("bitmap", bitmap.toString());
                                                    Product product = new Product();
                                                    product.setFarmerName(dataDetailProduct.getString("farmer_username"));
                                                    product.setDescription(dataDetailProduct.getString("prod_desc"));
                                                    product.setName(dataDetailProduct.getString("product_name"));
                                                    product.setPrice(dataDetailProduct.getInt("prod_price"));
                                                    product.setStock(dataDetailProduct.getInt("stock_num"));
                                                    product.setId(dataDetailProduct.getInt("id"));
                                                    product.setUom(dataDetailProduct.getString("uom"));
                                                    product.setUrl(dataDetailProduct.getString("product_picture_url"));
                                                    product.setThumbnail(bitmap);
//
                                                    mItems.add(product);

                                                    mAdapter = new GridAdapter(mItems,getContext());
                                                    mRecyclerView.setAdapter(mAdapter);




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
        jsonRequestProduct.setTag(REQUEST_TAG);
        mQueue.add(jsonRequestProduct);



        // Inflate the layout for this fragment
        return rootView;
    }


}
