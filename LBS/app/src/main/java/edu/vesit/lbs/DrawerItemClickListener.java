package edu.vesit.lbs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener
{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Activity mContainerActivity;
    private String[] mNavMenuTitles;

    public DrawerItemClickListener(Activity mContainerActivity, DrawerLayout mDrawerLayout, ListView mDrawerList, String[] mNavMenuTitles)
    {
        this.mContainerActivity = mContainerActivity;
        this.mDrawerLayout = mDrawerLayout;
        this.mDrawerList = mDrawerList;
        this.mNavMenuTitles = mNavMenuTitles;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        displayView(position);
    }

    public void displayView(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case 0 : fragment = new ViewBooksFragment();
                     break;
            /*case 1 : fragment = new SearchBooksFragment();
                     break; */
            case 1 : MainScreenActivity.logout();
                     break;
            default : break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = mContainerActivity.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mContainerActivity.setTitle(mNavMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else
            Log.e("MainActivity", "Error in creating fragment");
    }
}
