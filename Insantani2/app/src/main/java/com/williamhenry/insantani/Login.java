package com.williamhenry.insantani;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends Fragment {
    private String url;
    private RequestQueue mQueue;
    private RelativeLayout relativeLayout;
    public static final String REQUEST_TAG = "Login";

    private Context context;
    private SharedPreferences pref;
    private Editor editor;
    private TextView email;
    private TextView password;
    private String accessToken;


    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().hide();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        relativeLayout= (RelativeLayout) rootView.findViewById(R.id.relativeLayoutLogin);
        context = getContext();
        pref= this.getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor= pref.edit();

        Button registration = (Button) rootView.findViewById(R.id.register_login_button);
        email = (TextView) rootView.findViewById(R.id.signIn_email);
        password = (TextView) rootView.findViewById(R.id.signIn_password);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,Registration.class);
                Bundle bundle = new Bundle();
                bundle.putString("Email", email.getText().toString());
                bundle.putString("Password", password.getText().toString());
                intent.putExtra("User", bundle);
                context.startActivity(intent);
            }
        });
        Button login = (Button) rootView.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mQueue= CustomVolleyRequestQueue.getInstance(getContext()).getRequestQueue();
                url="http://104.155.213.80/insantani/public/oauth/token";
                final StringRequest stringRequest= new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>(){
                    //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                    @Override
                    public void onResponse(String response){
                        Log.d("login", response.toString());
                        try {
                            Log.d("response_login", response.toString());
                            JSONObject jsonObject= new JSONObject(response.toString());
                            editor.putString("access_token",jsonObject.getString("access_token"));
                            editor.putString("token_type",jsonObject.getString("token_type"));
                            editor.putString("refresh_token", jsonObject.getString("refresh_token"));
                            accessToken=jsonObject.getString("access_token");
                            editor.commit();
                            Log.d("access_token", jsonObject.getString("access_token"));
                            Log.d("token_type", jsonObject.getString("token_type"));
                            Log.d("refresh_token",jsonObject.getString("refresh_token"));

//                            Snackbar snackbar = Snackbar.make(relativeLayout, "Login Success", Snackbar.LENGTH_LONG);
//                            snackbar.setActionTextColor(Color.WHITE);
//
//                            View snackbarView= snackbar.getView();
//                            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                            textView.setTextColor(Color.WHITE);
//
//                            snackbar.show();

//                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                            startActivity(intent);

                            //private section here
                            url="http://104.155.213.80/insantani/public/private";
                            final StringRequest stringRequestPrivate= new StringRequest(Request.Method.GET,
                                    url, new Response.Listener<String>(){
                                //                    private ArrayList<Article> articles1=new ArrayList<Article>();
                                @Override
                                public void onResponse(String response){
                                    Log.d("private", response.toString());
                                    try {
                                        Log.d("response_private", response.toString());
                                        JSONObject jsonObject= new JSONObject(response.toString());
                                        editor.putString("user_id",jsonObject.getString("user_id"));
//                            editor.putString("token_type",jsonObject.getString("token_type"));
//                            editor.putString("refresh_token", jsonObject.getString("refresh_token"));
                                        editor.commit();
                                        Log.d("user_id", jsonObject.getString("user_id"));
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

                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);


                                    } catch(Exception e){
                                        Log.d("JSON_error_private",e.toString());
                                    }
                                }
                            },new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse(VolleyError error){

                                    Log.d("error_response_private",error.toString());
                                    Snackbar snackbar = Snackbar.make(relativeLayout, "Invalid Token", Snackbar.LENGTH_LONG);
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
                                    String auth="Bearer "+accessToken;
                                    Log.d("Auth",auth);
                                    headers.put("Authorization",auth);
//                        params.put("email", email.getText().toString());
//
//                        params.put("password", password.getText().toString());
//                        params.put("scope","read");
//                        params.put("client_id", "testclient");
//                        params.put("client_secret","testpass");
                                    return headers;
                                };


                            };


                            stringRequestPrivate.setTag(REQUEST_TAG);
                            mQueue.add(stringRequestPrivate);




                        } catch(Exception e){
                            Log.d("JSON_error_login",e.toString());
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Log.d("error_response_login",error.toString());
                        Snackbar snackbar = Snackbar.make(relativeLayout, "Login Failed", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);

                        View snackbarView= snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);

                        snackbar.show();
                    }
                }){


                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String,String>();
                        // the POST parameters:
                        params.put("grant_type","password");
                        params.put("email", email.getText().toString());

                        params.put("password", password.getText().toString());
                        params.put("scope","read");
                        params.put("client_id", "testclient");
                        params.put("client_secret","testpass");
                        return params;
                    };


                };


                stringRequest.setTag(REQUEST_TAG);
                mQueue.add(stringRequest);









//                Intent intent= new Intent(context, MainActivity.class);
//
//                context.startActivity(intent);
            }
        });

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


}
