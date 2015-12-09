package com.williamhenry.insantani;

/**
 * Created by william on 10/17/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//import android.app.Activity;

public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    ArrayList<Product> mItems;
    private Context context;
    public GridAdapter(ArrayList<Product>mItems,Context context) {
        super();
        this.context=context;
        this.mItems=mItems;
//
//        Product species = new Product();
//        species.setName("Red Chilli");
//        species.setThumbnail(R.mipmap.cabe);
//        species.setDescription("Apart from its uses in cooking, it has medicinal uses as well. It helps in digestion, it develops blood and is a very rich source of vitamin C, which helps in developing the immune system. It is used as spray weapon also for keeping away attackers and mobsters.");
//        species.setFarmerName("Izhar Almizan");
//        species.setPrice(100);
//        species.setStock(5);
//        mItems.add(species);
//
//        species = new Product();
//        species.setName("Jatim Carrots");
//        species.setThumbnail(R.mipmap.carrot);
//        species.setDescription("Carrot roots are eaten as a vegetable and can be consumed fresh or cooked. Carrot juice is consumed as a beverage. The leaves of the plant can be used as feed for animals.");
//        species.setFarmerName("Diovi Azalia");
//        species.setPrice(200);
//        species.setStock(2);
//        mItems.add(species);
//
//        species = new Product();
//        species.setName("Fresh Cauliflower");
//        species.setThumbnail(R.mipmap.col);
//        species.setDescription("Enjoyed cooked or raw, cauliflower is a great addition to your Healthiest Way of Eating, and now it's available year-round. While green vegetables may contain more chlorophyll, cauliflower is also rich in nutrients and, like its cousins, cabbage, kale, and broccoli, provides health-promoting compounds not found in many other vegetables.");
//        species.setFarmerName("Yohana Fransiska");
//        species.setPrice(150);
//        species.setStock(10);
//        mItems.add(species);
//
//        species = new Product();
//        species.setName("Potatoes");
//        species.setThumbnail(R.mipmap.kentang);
//        species.setDescription("Potatoes also contain a variety of phytonutrients that have antioxidant activity. Among these important health-promoting compounds are carotenoids, flavonoids, and caffeic acid, as well as unique tuber storage proteins, such as patatin, which exhibit activity against free radicals.");
//        species.setFarmerName("William Henry");
//        species.setPrice(250);
//        species.setStock(1);
//        mItems.add(species);
//
//        species = new Product();
//        species.setName("Java Cucumber");
//        species.setThumbnail(R.mipmap.timun);
//        species.setDescription("Cucumbers are good sources of phytonutrients (plant chemicals that have protective or disease preventive properties) such flavonoids, lignans and triterpenes, which have antioxidant, anti-inflammatory and anti-cancer benefits, according to World’s Healthiest Foods. The peel and seeds are the most nutrient-dense parts of the cucumber.");
//        species.setFarmerName("Agung Wirayogi");
//        species.setPrice(220);
//        species.setStock(4);
//        mItems.add(species);
//
//        species = new Product();
//        species.setName("Baby Tomatoes");
//        species.setThumbnail(R.mipmap.tomat);
//        species.setDescription("Tomatoes are widely known for their outstanding antioxidant content, including, of course, their oftentimes-rich concentration of lycopene. Did you know that tomatoes do not have to be a deep red color to be an outstanding source of lycopene?");
//        species.setFarmerName("Cantya Sophie");
//        species.setPrice(230);
//        species.setStock(2);
//        mItems.add(species);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item_page, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Product nature = mItems.get(i);
        Log.d("mitems",nature.toString());
        Log.d("i", Integer.toString(i));
        viewHolder.tvspecies.setText(nature.getName());
        viewHolder.grid_price.setText("Rp "+nature.getPrice() + " / "+ nature.getUom());
        viewHolder.imgThumbnail.setImageBitmap(nature.getThumbnail());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
//                Intent intent= new Intent(getAc,ProductActivity.class);
//                startActivtiy(intent);
                Log.d("i", Integer.toString(i));
                Intent intent= new Intent(context,ProductActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title", nature.getName());
//                bundle.putInt("thumbnail",nature.getThumbnail());
//                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//                nature.getThumbnail().compress(Bitmap.CompressFormat.PNG, 100, baos);

//                byte [] b=baos.toByteArray();
//                String image= Base64.encodeToString(b, Base64.DEFAULT);
//                bundle.putString("thumbnail",image);

                bundle.putString("url",nature.getUrl());
                bundle.putString("description",nature.getDescription());
                bundle.putString("fname",nature.getFarmerName());
                bundle.putInt("price", nature.getPrice());
                bundle.putInt("stock", nature.getStock());
                bundle.putInt("id",nature.getId());
                bundle.putString("uom",nature.getUom());
                intent.putExtra("nature",bundle);
//                intent.putExtra("nature_thumbnail",nature.getThumbnail());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvspecies;
        public TextView grid_price;
//        public final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvspecies = (TextView)itemView.findViewById(R.id.tv_species);
            grid_price= (TextView) itemView.findViewById(R.id.grid_price);

        }
//        @Override
//        public void onClick(View v) {
//
//            Intent intent=new Intent(getActivity(),ProductActivity.class);
//            startActivity(intent);
//
////            context.startActivity(new Intent(context,ProductActivity.class));
//        }
    }
}