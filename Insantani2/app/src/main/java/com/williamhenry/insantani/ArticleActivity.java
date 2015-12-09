package com.williamhenry.insantani;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class ArticleActivity extends Activity {
    private String url;
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "ArticleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayShowTitleEnabled(true);

        Bundle extras = getIntent().getExtras();
        Bundle item = (Bundle) extras.get("article");
        Log.d("article", item.getString("title"));
        Log.d("author", item.getString("author"));
        Log.d("desc", item.getString("desc"));
//        Log.d("image", item.getString("image"));
//        getActionBar().setTitle(item.getString("article"));
        final ImageView image = (ImageView) findViewById(R.id.imageViewArticle);
        TextView title = (TextView) findViewById(R.id.titleArticle);
        TextView author = (TextView) findViewById(R.id.authorArticle);
        TextView desc = (TextView) findViewById(R.id.descriptionArticle);

//        byte [] encodeByte= Base64.decode(item.getString("image"), Base64.DEFAULT);
//        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

//        Bitmap x = (Bitmap)item.get("image");
//        Drawable draw = getResources().getDrawable(x);
        mQueue= CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        url="http://104.155.213.80/insantani/public/"+item.getString("url");
        Log.d("url", url);
        final ImageRequest imageRequest= new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        try{
                            Log.d("bitmap",bitmap.toString());
                            image.setImageBitmap(bitmap);
//                            Article article = new Article(dataDetail.getString("author"),
//                                    dataDetail.getString("title"), dataDetail.getString("content"), bitmap,
//                                    dataDetail.getString("article_picture_url"));
////
//                            articles.add(article);
//                            mAdapter = new MyAdapter(articles, getContext());
//                            mRecyclerView.setAdapter(mAdapter);
                        }catch(Exception e){
                            Log.d("error_picture_article",e.toString());

                        }

                    }
                },0,0,null,new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("error_response_image_article", error.toString());
            }
        });
        imageRequest.setTag(REQUEST_TAG);
        mQueue.add(imageRequest);



        title.setText(item.getString("title"));
        author.setText(item.getString("author"));
        desc.setText(item.getString("desc"));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        TextView title = (TextView) findViewById(R.id.titleArticle);
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

        return super.onOptionsItemSelected(item);
    }

}
