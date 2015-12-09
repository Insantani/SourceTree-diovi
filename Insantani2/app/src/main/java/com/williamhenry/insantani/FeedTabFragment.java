package com.williamhenry.insantani;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class FeedTabFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private String url;
    public static final String REQUEST_TAG = "HomeFragment";
    private RequestQueue mQueue;

    private ArrayList<Article> articles;

    public FeedTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_tab, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        articles = new ArrayList<Article>();
        mQueue= CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
        url="http://104.155.213.80/insantani/public/api/feed?page=1&limit=3";

        final CustomJSONObjectRequest jsonRequest= new CustomJSONObjectRequest(Request.Method.GET,
                url,new JSONObject(),
                new Response.Listener<JSONObject>(){
                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONObject result=response.getJSONObject("result");
//                        if(result.getString("next_page_url"))

                            JSONArray data=result.getJSONArray("data");
                            Log.d("response1", data.get(0).toString());
                            for (int i=0;i<data.length();i++){
                                final JSONObject dataDetail=(JSONObject)data.get(i);
                                url="http://104.155.213.80/insantani/public/"+dataDetail.getString("article_picture_url");
                                Log.d("url",url);
                                final ImageRequest imageRequest= new ImageRequest(url,
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                try{
                                                    Log.d("bitmap",bitmap.toString());
                                                    Article article = new Article(dataDetail.getString("author"),
                                                            dataDetail.getString("title"), dataDetail.getString("content"), bitmap,
                                                            dataDetail.getString("article_picture_url"));
//
                                                    articles.add(article);
                                                    mAdapter = new MyAdapter(articles, getContext());
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
//                                @Override
//                                public void onResponse(Bitmap bitmap) {
//                                    mImageView.setImageBitmap(bitmap);
//                                }
//                            }, 0, 0, null,
//                                    new Response.ErrorListener() {
//                                        public void onErrorResponse(VolleyError error) {
//                                            mImageView.setImageResource(R.drawable.image_load_error);
//                                        }
//                                    });

//                            Log.d("articles",articles.toString());
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


        // Inflate the layout for this fragment
        return rootView;
    }


}
