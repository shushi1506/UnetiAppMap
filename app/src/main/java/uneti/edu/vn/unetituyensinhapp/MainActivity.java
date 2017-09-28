package uneti.edu.vn.unetituyensinhapp;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBar ab;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);

        }
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                        toolbar.setTitle("Home");
                        return true;
                    case R.id.nav_contact:
                        Intent inte1=new Intent(getApplicationContext(),TestHTMLActivity.class);
                        startActivity(inte1);
                        Toast.makeText(getApplicationContext(), "Contact", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.nav_khoa_cntt:
                        Intent intentu=new Intent(getApplicationContext(),KhoaCNTTActivity.class);
                        startActivity(intentu);
                        toolbar.setTitle("Khoa CNTT");
                        return true;
                    case R.id.nav_gpss:
                        Intent inte=new Intent(getApplicationContext(),MapActivity.class);
                        startActivity(inte);
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new InfoFragMent(), "Thông tin tuyển sinh");
        adapter.addFragment(new NganhDaoTaoFragMent(), "Các Ngành Đào Tạo");
        adapter.addFragment(new DiemChuanFragMent(), "Điểm Chuẩn");
        adapter.addFragment(new InfoFragMent(), "Thông tin sinh viens");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
