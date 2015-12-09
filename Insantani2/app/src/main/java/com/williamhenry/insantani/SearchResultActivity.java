package com.williamhenry.insantani;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.util.ArrayList;


public class SearchResultActivity extends AppCompatActivity {

    private IngredientsTabFragment ingredientsTabFragment;
    private FarmerTabFragment farmerTabFragment;

    private TabLayout tabLayout;

    private String url;
    private RequestQueue mQueue;
//    private ArrayList<Article> articles;
    public static final String REQUEST_TAG = "Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setTitle("");

        // Enabling Back navigation on Action Bar icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        ingredientsTabFragment = new IngredientsTabFragment();
        farmerTabFragment = new FarmerTabFragment();

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ingredientsTabFragment, "Ingredients");
        adapter.addFragment(farmerTabFragment, "Farmer");

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_result, menu);

        // Get the SearchView and set the searchable configuration

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        searchView.setQueryHint("Search");

        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        final SearchView.OnQueryTextListener queryTextlistener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the search box.
                searchView.setQuery(query, false);
                Log.d("onQueryTextSubmit", query);
                if (tabLayout.getSelectedTabPosition() == 0) {
                    ingredientsTabFragment.search(query);

                }
                else{
                    farmerTabFragment.search(query);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SearchResultActivity", "New text: " + newText);
//                if (newText.equals(null))
//                    newText="";
                // This is your adapter that will be filtered.
//                searchView.setQuery(newText, false);
                if (tabLayout.getSelectedTabPosition() == 0) {

                    ingredientsTabFragment.search(newText);
                    getSupportFragmentManager().beginTransaction().detach(ingredientsTabFragment).attach(ingredientsTabFragment).commit();
//                    Fragment currentFragment = getFragmentManager().findFragmentById();
//                    FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
//                    fragTransaction.detach(currentFragment);
//                    fragTransaction.attach(currentFragment);
//                    fragTransaction.commit();

                }
                else{
                    farmerTabFragment.search(newText);
                }
                return false;
            }
        };

        searchView.setOnQueryTextListener(queryTextlistener);
        searchView.callOnClick();

        // To skip the first search
//        searchView.setQuery("apple", true);
//        searchView.setQuery("", true);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                queryTextlistener.onQueryTextSubmit(searchView.getQuery().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                queryTextlistener.onQueryTextSubmit(searchView.getQuery().toString());
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.) {
//            return true;
//        }

        switch (id) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }


}