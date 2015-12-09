package com.williamhenry.insantani;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by agungwy on 10/20/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Article> articles;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articles, parent, false);
        // set the view's size, margins, paddings and layout parameter

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView title = (TextView) holder.view.findViewById(R.id.title);
        TextView author = (TextView) holder.view.findViewById(R.id.author);
        TextView desc = (TextView) holder.view.findViewById(R.id.description);
        ImageView imageView = (ImageView) holder.view.findViewById(R.id.imageView);

        title.setText(articles.get(position).getTitle());
        author.setText(articles.get(position).getAuthor());
        desc.setText(articles.get(position).getDescription());

        imageView.setImageBitmap(articles.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
//                Intent intent= new Intent(getAc,ProductActivity.class);
//                startActivtiy(intent);

                Intent intent= new Intent(context,ArticleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", articles.get(position).getTitle());
                bundle.putString("author", articles.get(position).getAuthor());
                bundle.putString("desc", articles.get(position).getDescription());
                bundle.putString("url",articles.get(position).getUrl());
//                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//                articles.get(position).getImage().compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte [] b=baos.toByteArray();
//                String image= Base64.encodeToString(b, Base64.DEFAULT);
//                bundle.putString("image", image);
                intent.putExtra("article", bundle);
                context.startActivity(intent);

            }
        });
;


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }
}
