package com.williamhenry.insantani;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<Cart> carts;
    private String url;
    private RequestQueue mQueue;
    public static final String REQUEST_TAG = "ShoppingCart";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    ViewGroup parent2;
    int counter=0;
    private android.support.v4.app.FragmentManager fManager;
    private CartFragment cartFragment;


    // Provide a suitable constructor (depends on the kind of dataset)
    public CartAdapter(ArrayList<Cart> carts, Context context, RequestQueue mQueue, android.support.v4.app.FragmentManager fManager, CartFragment cartFragment) {
        this.carts = carts;
        this.context=context;
        this.pref=this.context.getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        this.editor=this.pref.edit();
        this.mQueue=mQueue;
        this.fManager=fManager;
        this.cartFragment=cartFragment;

    }

    public Cart getItem(int position) {
        return carts.get(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_listview_layout, parent, false);
        // set the view's size, margins, paddings and layout parameter
        parent2 = parent;
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView productName = (TextView) holder.view.findViewById(R.id.product_name);
        TextView qty = (TextView) holder.view.findViewById(R.id.quantity);
        TextView farmer = (TextView) holder.view.findViewById(R.id.farmer);
        TextView price = (TextView) holder.view.findViewById(R.id.price);
        ImageView imageView = (ImageView) holder.view.findViewById(R.id.image);

        productName.setText(carts.get(position).getProductName());
        qty.setText(String.valueOf(carts.get(position).getQty()));
        counter=carts.get(position).getQty();
        farmer.setText(carts.get(position).getFarmer());
        price.setText("Rp " + String.valueOf((int) carts.get(position).getPrice())+" / "+carts.get(position).getUom());
//        cartFragment=new CartFragment();

        imageView.setImageBitmap(carts.get(position).getImage());

        TextView remove= (TextView) holder.view.findViewById(R.id.remove_button);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mQueue= CustomJSONObjectRequest.getInstance(context).getRequestQueue();
                url = "http://104.155.213.80/insantani/public/api/cart/delete?user_id=" + pref.getString("user_id", null) + "&product_id=" +
                        Integer.toString(carts.get(position).getId());
                final StringRequest stringRequestRemove = new StringRequest(Request.Method.DELETE,
                        url, new Response.Listener<String>() {
                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                    @Override
                    public void onResponse(String response) {
                        Log.d("delete", response.toString());
                        try {
                            Log.d("response_delete", response.toString());
                            JSONObject jsonObject = new JSONObject(response.toString());

                            String message = jsonObject.getString("message");
                            Log.d("JSON_response_delete", jsonObject.getString("message"));
//                                      String message=response.getString("message");
                            if (message.equals("Success")) {
                                carts.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, carts.size());
                                cartFragment.refresh();



//                                    list.remove(position);
//                                    recycler.removeViewAt(position);
//                                    mAdapter.notifyItemRemoved(position);
//                                    mAdapter.notifyItemRangeChanged(position, list.size());

                            }
//


                        } catch (Exception e) {
                            Log.d("JSON_error_delete", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error_response_delete", error.toString());
//                        Snackbar snackbar = Snackbar.make(linearLayout, "Email has been used", Snackbar.LENGTH_LONG);
//                        snackbar.setActionTextColor(Color.WHITE);
//
//                        View snackbarView= snackbar.getView();
//                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        textView.setTextColor(Color.WHITE);
//
//                        snackbar.show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        String auth = "Bearer " + pref.getString("access_token", null);
                        Log.d("auth_delete", auth);
                        headers.put("Authorization", auth);
                        return headers;
                    }

                    ;


//                    protected Map<String, String> getParams(){
//                        Map<String, String> params = new HashMap<String,String>();
//                        // the POST parameters:
//                        Log.d("id_pos",Integer.toString(carts.get(position).getId()));
//                        params.put("product_id", Integer.toString(carts.get(position).getId()));
//                        return params;
//                    };


                };


                stringRequestRemove.setTag(REQUEST_TAG);
                mQueue.add(stringRequestRemove);


            }
        });

        TextView edit=(TextView) holder.view.findViewById(R.id.edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater =LayoutInflater.from(context) ;

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setView(inflater.inflate(R.layout.confrim_edit_cart, null));
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
                                final DialogInterface dialog=dialog1;
//                                    Intent intent = new Intent(context, CartFragment.class);
//                                    Bundle bundle = new Bundle();
                                EditText text = (EditText) ((Dialog) dialog).findViewById(R.id.confirmation_quantity);
                                text.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Integer.toString(carts.get(position).getStock()))});
                                final int quantity = Integer.parseInt(text.getText().toString());



                                //edit cart starts here
                                url="http://104.155.213.80/insantani/public/api/cart/update?user_id="+pref.getString("user_id",null);
//                        Integer.toString(carts.get(position).getId());
                                final StringRequest stringRequestEdit= new StringRequest(Request.Method.PUT,
                                        url, new Response.Listener<String>(){
                                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                                    @Override
                                    public void onResponse(String response){
                                        Log.d("edit", response.toString());
                                        try {
                                            Log.d("response_edit", response.toString());
                                            JSONObject jsonObject= new JSONObject(response.toString());

                                            String message = jsonObject.getString("message");
                                            Log.d("JSON_response_edit", jsonObject.getString("message"));
//                                      String message=response.getString("message");
                                            if (message.equals("Success")){
//                                                carts.remove(position);
//                                                notifyItemRemoved(position);
//                                                notifyItemRangeChanged(position,carts.size());
                                                dialog.dismiss();
                                                Cart item=(Cart)carts.get(position);
                                                item.setQuantity(quantity);
                                                notifyDataSetChanged();

                                                cartFragment.refresh();
//                                                inflater.inflate(R.layout.fragment_cart, parent2, false );

                                            }
//




                                        } catch(Exception e){
                                            Log.d("JSON_error_edit",e.toString());
                                        }
                                    }
                                },new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error){

                                        Log.d("error_response_edit",error.toString());

                                    }
                                }){
                                    @Override
                                    public Map<String,String> getHeaders(){
                                        Map<String,String> headers= new HashMap<String, String>();
                                        String auth="Bearer "+pref.getString("access_token",null);
                                        Log.d("auth_delete",auth);
                                        headers.put("Authorization",auth);
                                        return headers;
                                    };


                                    protected Map<String, String> getParams(){
                                        Map<String, String> params = new HashMap<String,String>();
                                        // the POST parameters:
                                        Log.d("id_pos",Integer.toString(carts.get(position).getId()));
                                        params.put("product_id", Integer.toString(carts.get(position).getId()));
                                        params.put("product_quantity", Integer.toString(quantity));
                                        return params;
                                    };


                                };


                                stringRequestEdit.setTag(REQUEST_TAG);
                                mQueue.add(stringRequestEdit);

                            }
                        });
                alertDialog.show();
                Button plus = (Button) alertDialog.findViewById(R.id.increase_button);
                Button min = (Button) alertDialog.findViewById(R.id.decrease_button);
                EditText qty = (EditText) alertDialog.findViewById(R.id.confirmation_quantity);
                qty.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Integer.toString(carts.get(position).getStock()))});
                final TextView text = (TextView) alertDialog.findViewById(R.id.confirmation_quantity);
                text.setText(Integer.toString(counter));
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
                                                if(counter<carts.get(position).getStock()) {
                                                    counter += 1;
                                                    text.setText("" + counter);
                                                }else{
                                                    text.setText(Integer.toString(carts.get(position).getStock()));
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


            }
        });
//        in=LayoutInflater.from(context);
//        View v1= LayoutInflater.from(context).inflate(R.layout.fragment_cart,null);
//        Button next= (Button) v1.findViewById(R.id.next);
//        next.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent= new Intent (context,CheckoutActivity.class);
////                Bundle bundle= new Bundle();
//
////                bundle.putStringArrayList("cart",carts);
//                intent.putExtra("checkout_items",carts);
//                context.startActivity(intent);
//            }
//        });




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return carts.size();
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
