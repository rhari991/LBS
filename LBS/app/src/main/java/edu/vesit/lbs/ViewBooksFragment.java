package edu.vesit.lbs;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewBooksFragment extends Fragment
{
    String account_no;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_view_books, container, false);
        SharedPreferences storedCredentials = getActivity().getSharedPreferences("STORED_CREDENTIALS", Context.MODE_PRIVATE);
        account_no = storedCredentials.getString("accountNo", "");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager());
        IssuedBookFragment fragment = new IssuedBookFragment().newInstance(1);
        adapter.addFragment(fragment, "BOOK 1");
        fragment = new IssuedBookFragment().newInstance(2);
        adapter.addFragment(fragment, "BOOK 2");
        viewPager.setAdapter(adapter);
    }
}
