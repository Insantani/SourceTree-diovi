package com.williamhenry.insantani;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class IngredientsTabFragment extends android.support.v4.app.Fragment {

    private ListView resultListView;
    private SearchListViewAdapter searchListViewAdapter;
    private String url;
    private String query="apple";
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "Search";

    private CustomJSONObjectRequest jsonRequestAll;

    public IngredientsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ingredients_tab, container, false);

        final ArrayList<Product>newsList = new ArrayList<>();

        mQueue= CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        Log.d("query",query);
        if (query!=null) {

            url = "http://104.155.213.80/insantani/public/api/search/product/" + query;
        }else{
            url = "http://104.155.213.80/insantani/public/api/search/product/apple";
        }
            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET,
                    url, new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray result = response.getJSONArray("result");
                                Log.d("result_seach", result.toString());
//
//
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
                                                    Log.d("bitmap", bitmap.toString());
                                                    Product searchItem = new Product();
                                                    searchItem.setPrice(dataDetail.getInt("prod_price"));
                                                    searchItem.setStock(dataDetail.getInt("stock_num"));
                                                    searchItem.setName(dataDetail.getString("product_name"));
                                                    searchItem.setFarmerName(dataDetail.getString("farmer_username"));
                                                    searchItem.setUrl(dataDetail.getString("product_picture_url"));
                                                    searchItem.setId(dataDetail.getInt("id"));
                                                    searchItem.setDescription(dataDetail.getString("prod_desc"));
                                                    searchItem.setUom(dataDetail.getString("uom"));
                                                    searchItem.setThumbnail(bitmap);
                                                    newsList.add(searchItem);



                                searchListViewAdapter = new SearchListViewAdapter(getActivity(), newsList);
                                resultListView = (ListView) view.findViewById(R.id.itemList);
                                resultListView.setAdapter(searchListViewAdapter);

                                resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Bundle bundle= new Bundle();
                                        Intent intent= new Intent(getContext(),ProductActivity.class);
                                        bundle.putString("title",searchListViewAdapter.getItem(position).getName());
                                        bundle.putString("description",searchListViewAdapter.getItem(position).getDescription());
                                        bundle.putString("fname",searchListViewAdapter.getItem(position).getFarmerName());
                                        bundle.putString("url",searchListViewAdapter.getItem(position).getUrl());
                                        bundle.putInt("id", searchListViewAdapter.getItem(position).getId());
                                        bundle.putInt("price", searchListViewAdapter.getItem(position).getPrice());
                                        bundle.putInt("stock", searchListViewAdapter.getItem(position).getStock());
                                        bundle.putString("uom",searchListViewAdapter.getItem(position).getUom());
                                        intent.putExtra("nature", bundle);
                                        getContext().startActivity(intent);

//                                        Toast.makeText(getActivity(),
//                                                getString(R.string.clicked) + " " + searchListViewAdapter.getItem(position).getTitle().toLowerCase(),
//                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
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
                            } catch (Exception e) {
                                Log.d("JSON_err", e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error_response", error.toString());
                }
            });
            jsonRequest.setTag(REQUEST_TAG);
            jsonRequestAll=jsonRequest;
            mQueue.add(jsonRequest);









        // add items to the list
//        searchListViewAdapter.add(new SearchItem("News 1", "This is the content of news 1", 1));
//        searchListViewAdapter.add(new SearchItem("News 2", "This is the content of news 2", 2));
//        searchListViewAdapter.add(new SearchItem("News 3", "This is the content of news 3", 3));

        // show toast on item click


        return view;
    }

    public void search(String param) {

        this.query=param;
        Log.d("param",param);
        searchListViewAdapter.getFilter().filter(param);

    }


}
