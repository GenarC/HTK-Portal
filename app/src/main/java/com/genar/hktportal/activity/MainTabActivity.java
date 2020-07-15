package com.genar.hktportal.activity;

import android.Manifest;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

import com.genar.hktportal.GlobalApplicaiton;
import com.genar.hktportal.R;
import com.genar.hktportal.adapter.HataAdapter;
import com.genar.hktportal.api.HataService;
import com.genar.hktportal.helper.Utils;
import com.genar.hktportal.model.Hata;
import com.genar.hktportal.response.HataResponse;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainTabActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, EasyPermissions.PermissionCallbacks{

    List<Hata> hataListTop = new ArrayList<>();
    List<Hata> hataListTopOperator = new ArrayList<>();
    List<Hata> hataListTopBolum = new ArrayList<>();


    private static final int CAMERA_REQUEST = 1457;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
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

        hataListTop = getIntent().getExtras().getParcelableArrayList("TopList");
        hataListTopOperator = getIntent().getExtras().getParcelableArrayList("TopOperatorList");
        hataListTopBolum = getIntent().getExtras().getParcelableArrayList("TopBolumList");


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
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public ArrayList<Hata> hataList;
        private int position;
        private Button btnRefresh;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber,ArrayList<Hata> list) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList("hataList",list);
            args.putInt("pos",sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tab, container, false);


            RecyclerView rvHataList = (RecyclerView) rootView.findViewById(R.id.hatalist_recyclerview);
            btnRefresh = (Button) rootView.findViewById(R.id.btnyenile);

            hataList = getArguments().getParcelableArrayList("hataList");
            position = getArguments().getInt("pos");

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rvHataList.setLayoutManager(layoutManager);
            final HataAdapter hataAdapter = new HataAdapter (hataList,getActivity(),position);
            rvHataList.setAdapter(hataAdapter);


            btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://www.genar.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    HataService service = retrofit.create(HataService.class);

                    Call<HataResponse> hataResult = service.hataResponse("topXHata",GlobalApplicaiton.TOP_LIST_COUNT);

                    hataResult.enqueue(new Callback<HataResponse>() {
                        @Override
                        public void onResponse(Call<HataResponse> call, Response<HataResponse> response) {
                            if(response.body().getSuccess() == 3){
                                if(position == 0){
                                    hataList = (ArrayList<Hata>) response.body().getHatalarMost();
                                }else if(position == 1){
                                    hataList = (ArrayList<Hata>) response.body().getHatalarOperator();
                                }else{
                                    hataList = (ArrayList<Hata>) response.body().getHatalarBolum();
                                }
                                hataAdapter.refreshList(hataList);
                            }else{
                                Toast.makeText(getContext(), "Hata listesi alınırken hata oluştu, daha sonra tekrar deneyin.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<HataResponse> call, Throwable t) {

                        }
                    });
                }
            });
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
            List<Hata> list = new ArrayList<>();
            if (position == 0){
                list = hataListTop;
            }else if(position == 1){
                list = hataListTopOperator;
            }else{
                list = hataListTopBolum;
            }
            return PlaceholderFragment.newInstance(position, (ArrayList<Hata>) list);
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
                    return "Top " + GlobalApplicaiton.TOP_LIST_COUNT + " Hata";
                case 1:
                    return "Top " + GlobalApplicaiton.TOP_LIST_COUNT + " Operator";
                case 2:
                    return "Top " + GlobalApplicaiton.TOP_LIST_COUNT + " Bölüm";
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
