package com.genar.hktportal.activity;

import android.Manifest;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.genar.hktportal.R;
import com.genar.hktportal.adapter.HataAdapter;
import com.genar.hktportal.helper.Utils;
import com.genar.hktportal.model.HataModel;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainTabActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, EasyPermissions.PermissionCallbacks{

    ArrayList<HataModel> hataListTab1 = new ArrayList<>();
    ArrayList<HataModel> hataListTab2 = new ArrayList<>();
    ArrayList<HataModel> hataListTab3 = new ArrayList<>();


    public void init(){

        hataListTab1.add(new HataModel("İp Kaçırma", 16));
        hataListTab1.add(new HataModel("Elektrik arızası", 13));
        hataListTab1.add(new HataModel("İğne kırılması", 12));
        hataListTab1.add(new HataModel("Kumaş boyama",9));

        hataListTab2.add(new HataModel("Cem Yanar", 13));
        hataListTab2.add(new HataModel("Nedim Yener", 8));
        hataListTab2.add(new HataModel("Ayşe Güneş", 7));
        hataListTab2.add(new HataModel("Veli Arslan",5));

        hataListTab3.add(new HataModel("356 HB", 32));
        hataListTab3.add(new HataModel("356 SW", 24));
        hataListTab3.add(new HataModel("356 Sedan", 12));
        hataListTab3.add(new HataModel("356 Default",11));
    }



    private static final int CAMERA_REQUEST = 1457;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        init();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,ArrayList<HataModel> list) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList("hataList",list);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tab, container, false);

            RecyclerView rvHataList;
            rvHataList = (RecyclerView) rootView.findViewById(R.id.hatalist_recyclerview);

            ArrayList<HataModel> hataList = getArguments().getParcelableArrayList("hataList");

            /*ArrayList<HataModel> hataList = new ArrayList<>();
            hataList.add(new HataModel("İp Kaçırma", 6));
            hataList.add(new HataModel("Elektrik arızası", 3));
            hataList.add(new HataModel("İğne kırılması", 2));
            hataList.add(new HataModel("Kumaş boyama",1));*/


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rvHataList.setLayoutManager(layoutManager);
            HataAdapter hataAdapter = new HataAdapter (hataList,getActivity());
            rvHataList.setAdapter(hataAdapter);


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<HataModel> list = new ArrayList<>();
            if (position == 0){
                list = hataListTab1;
            }else if(position == 1){
                list = hataListTab2;
            }else{
                list = hataListTab3;
            }
            return PlaceholderFragment.newInstance(position + 1, list);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Top 10 Hata";
                case 1:
                    return "Top 10 Operator";
                case 2:
                    return "Top 10 Bölüm";
            }
            return null;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_knnekle) {
            cameraPermissionRequest();
        } else if (id == R.id.nav_btn1) {

        } else if (id == R.id.nav_btn2) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @AfterPermissionGranted(CAMERA_REQUEST)
    private void cameraPermissionRequest() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Utils.startActivityWithoutFinish(this, AddKnnActivity.class);
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    CAMERA_REQUEST, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Utils.startActivityWithoutFinish(this, AddKnnActivity.class);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "Bu özelliği kullanmak için kamera izni gerekiyor.", Toast.LENGTH_SHORT).show();
    }

}
