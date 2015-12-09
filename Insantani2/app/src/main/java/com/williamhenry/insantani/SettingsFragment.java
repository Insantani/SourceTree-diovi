package com.williamhenry.insantani;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SettingsFragment extends Fragment {
    private SharedPreferences pref;
    private Editor editor;
    private RelativeLayout relativeLayout;


    public SettingsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        pref= this.getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final boolean checkToken= pref.contains("access_token");
        final boolean checkRefreshToken= pref.contains("refresh_token");
        final boolean tokenType= pref.contains("token_type");
        final boolean user_id=pref.contains("user_id");
        Button logout= (Button) rootView.findViewById(R.id.logoutButton);
        relativeLayout=(RelativeLayout) rootView.findViewById(R.id.LogoutLayout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("token", Boolean.toString(checkToken));
                if(!checkToken && !tokenType && !checkRefreshToken && !user_id ){
//                    logout.setEnabled(false);

                    Snackbar snackbar = Snackbar.make(relativeLayout, "Login First", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);

                    View snackbarView= snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);

                    snackbar.show();
                }else{
                    editor=pref.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent= new Intent (getActivity(),MainActivity.class);
                    startActivity(intent);

//                    Snackbar snackbar = Snackbar.make(relativeLayout, "Logout", Snackbar.LENGTH_LONG);
//                    snackbar.setActionTextColor(Color.WHITE);
//
//                    View snackbarView= snackbar.getView();
//                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setTextColor(Color.WHITE);
//                    snackbar.show();

//                    Intent intent= new Intent ()
                }








            }


        });












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

}
