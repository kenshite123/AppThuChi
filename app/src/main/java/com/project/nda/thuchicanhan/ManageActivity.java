package com.project.nda.thuchicanhan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.nda.adapter.ViewPagerAdapter;

public class ManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Khai báo Navigation và Toolbar
    NavigationView navigationView = null;
    Toolbar toolbar = null;

    AlertDialog builder;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TabLayout tabLayout;



    Intent intent = null;
    //Tạo mảng tiêu đề của các ViewPager
    private String[] pageTitle = {"Chi Tiền", "Thu Tiền", "Tài Khoản", "Thống Kê"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        viewPager = (ViewPager)findViewById(R.id.view_pager);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        //Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_action_person));


        // cài đặt mặc định nút navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //cài đặt Tab layout (number of Tabs = number of ViewPager pages)
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        //set gravity cho tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Xử lý sự kiện navigation item
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        final ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //xử lý qua lại Tab khi trượt ViewPager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {
                if (position == 1)
                {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //thay đổi ViewPager page khi tab được chọn


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.spendmoney) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.recievemoney) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.wallet) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.report) {
            viewPager.setCurrentItem(3);
        } else if (id == R.id.reset)
        {
            Intent intent = new Intent(ManageActivity.this, ResetDataActivity.class);
            startActivity(intent);

        } else if (id == R.id.getdata) {
            builder = new AlertDialog.Builder(ManageActivity.this).create(); // Read
            // Update
            builder.setTitle(" Lấy dữ liệu từ máy chủ");
            builder
                    .setMessage("Dữ liệu trên thiết bị của bạn sẽ được thay thế bằng dữ liệu từ máy chủ, bạn có chắc chắn điều này");
            builder.setButton(Dialog.BUTTON_POSITIVE, "Chấp nhận",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Toast.makeText(ManageActivity.this, " Lấy dữ liệu thành công !", Toast.LENGTH_SHORT).show();
                        }

                    });
            builder.setButton(Dialog.BUTTON_NEGATIVE, "Hủy",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                                                }
                    });
            builder.show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            Intent intCloseApp = new Intent(Intent.ACTION_MAIN);
            intCloseApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intCloseApp.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intCloseApp.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intCloseApp.addCategory(Intent.CATEGORY_HOME);
            intCloseApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intCloseApp);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.syns) {
            Intent intent = new Intent(ManageActivity.this, SyncActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.changepass) {
            Intent intent = new Intent(ManageActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.out) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
