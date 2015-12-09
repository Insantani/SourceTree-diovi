package com.williamhenry.insantani;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    private FeedTabFragment feedTabFragment;
    private ShopTabFragment shopTabFragment;

    private TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getActionBar().show();
        getActivity().getActionBar().setElevation(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        feedTabFragment = new FeedTabFragment();
        shopTabFragment = new ShopTabFragment();

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(feedTabFragment, "Feed");
        adapter.addFragment(shopTabFragment, "Shop");

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }



}
