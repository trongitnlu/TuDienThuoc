package com.tudien.tudienthuoc;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tudien.tudienthuoc.adapter.ListBenh;
import com.tudien.tudienthuoc.adapter.ListThuocModel;
import com.tudien.tudienthuoc.adapter.TabDaXem;
import com.tudien.tudienthuoc.adapter.TabTatCa;
import com.tudien.tudienthuoc.adapter.TabTatCaBenh;
import com.tudien.tudienthuoc.adapter.TabYeuThich;
import com.tudien.tudienthuoc.controller.Controller;
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.database.DbAssetBenh;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.database.DbAssetThuoc;
import com.tudien.tudienthuoc.model.Account;
import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.ThuocModel;
import com.tudien.tudienthuoc.user.DangKiDialog;
import com.tudien.tudienthuoc.user.DangNhapDialog;
import com.tudien.tudienthuoc.user.ProfileDialog;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private SearchView searchView;
    public ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    public static boolean checkLogin = false;
    public static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.controller = new Controller(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Không hiện tiêu đề
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initMain();
        mData = FirebaseDatabase.getInstance().getReference();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        //   dangKiUser();
        controller.checkVersion();

        //
        initSearch();
        quang_cao();

    }


    private void initSearch() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 100, 10, 10);
        linearSearch = new LinearLayout(this);
        linearSearch.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearSearch.setPadding(10, 115, 10, 10);
        linearSearch.setHorizontalGravity(LinearLayout.VERTICAL);
        linearSearch.setVisibility(View.GONE);
        listView1 = new ListView(this);
        //
        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout1.setPadding(10, 10, 10, 10);
        linearLayout1.addView(listView1);
        linearLayout1.setBackgroundResource(R.drawable.back_search);
        linearSearch.addView(linearLayout1);
        addContentView(linearSearch, layoutParams);
    }

    LinearLayout linearSearch;
    ListView listView1;
    public static String MENU_LOAI;

    private void action_search(String nameSearch) {
        if (MENU_LOAI != null) {
            if (MENU_LOAI.equals("BENH")) {
                //search benh
                actionSearchBenh(nameSearch);
            } else {
                //search thuoc
                actionSearchThuoc(nameSearch);
            }
        } else {
            actionSearchThuoc(nameSearch);
            //search thuoc
        }


    }

    private void actionSearchThuoc(String nameSearch) {
        DbAssetThuoc dbAssetThuoc = new DbAssetThuoc(this);
        ArrayList<ThuocModel> list = dbAssetThuoc.querySearch(nameSearch);
        ListThuocModel listThuocModel = new ListThuocModel(this, R.layout.row_iteam_search, list);
        listThuocModel.setActivity(this);
        listView1.setAdapter(listThuocModel);
    }

    private void actionSearchBenh(String nameSearch) {
        DbAssetBenh dbAssetBenh = new DbAssetBenh(this);
        ArrayList<Benh> list = dbAssetBenh.querySearch(nameSearch);
        ListViewAdapter listBenh = new ListBenh(this, R.layout.row_iteam_search, list);
        listBenh.setActivity(this);
        listView1.setAdapter(listBenh);
    }

    private void initMain() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (MENU_LOAI != null) {
            if (MENU_LOAI.equals("BENH")) {
                viewPagerAdapter.addFragment(new TabTatCaBenh(this), "TẤT CẢ");
                TabYeuThich tabYeuThich = new TabYeuThich(this);
                TabYeuThich.LOAI = "BENH";
                viewPagerAdapter.addFragment(tabYeuThich, "YÊU THÍCH");
                TabDaXem tabDaXem = new TabDaXem(this);
                TabDaXem.LOAI = "BENH";
                viewPagerAdapter.addFragment(tabDaXem, "ĐÃ XEM");
            } else {
                viewPagerAdapter.addFragment(new TabTatCa(this), "TẤT CẢ");
                TabYeuThich tabYeuThich = new TabYeuThich(this);
                TabYeuThich.LOAI = "BENH";
                viewPagerAdapter.addFragment(tabYeuThich, "YÊU THÍCH");
                TabDaXem tabDaXem = new TabDaXem(this);
                TabDaXem.LOAI = "BENH";
                viewPagerAdapter.addFragment(tabDaXem, "ĐÃ XEM");
            }
        } else {
            viewPagerAdapter.addFragment(new TabTatCa(this), "TẤT CẢ");
            TabYeuThich tabYeuThich = new TabYeuThich(this);
            TabYeuThich.LOAI = "BENH";
            viewPagerAdapter.addFragment(tabYeuThich, "YÊU THÍCH");
            TabDaXem tabDaXem = new TabDaXem(this);
            TabDaXem.LOAI = "BENH";
            viewPagerAdapter.addFragment(tabDaXem, "ĐÃ XEM");
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuangCao.class);
                startActivity(intent);
            }
        });
        ACCOUNT = getAccount();
        if (ACCOUNT != null)
            DangNhapDialog.ID_USER = ACCOUNT.id;
    }

    public static Account ACCOUNT;

    public Account getAccount() {
        DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
        return assetBookmark.queryUser();
    }

    private MenuItem searchMenuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);
        searchMenuItem = menu.findItem(R.id.search_view);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    linearSearch.setVisibility(View.VISIBLE);
                    action_search(newText);
                } else {
                    linearSearch.setVisibility(View.GONE);
                }
                return false;
            }
        });
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.dangki:
                controller.initShowDialog(new DangKiDialog());
            default:
                ;
        }
        if (id == R.id.profile) {
            DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
            if (assetBookmark.count() > 0) {
                FragmentManager manager = getFragmentManager();
                ProfileDialog dialog = new ProfileDialog();
                dialog.setActivity(this);
                dialog.show(manager, null);
            } else {
                controller.initShowDialog(new DangNhapDialog());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.chat:
                DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
                Account account = null;
                if ((account = assetBookmark.queryUser()) != null) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    intent.putExtra("id", account.id);
                    startActivityForResult(intent, 0);
                } else {
                    controller.initShowDialog(new DangNhapDialog());
                }
                break;
            case R.id.benh:
                reloadLoaiMenu("BENH");
                break;
            case R.id.thuoc:
                reloadLoaiMenu("THUOC");
                break;
            case R.id.nav_share:
                controller.share();
                break;
            case R.id.timbenhvien:
                controller.goActivity(TimBenhVienActivity.class);
                break;
            case R.id.phanhoi:
                controller.sendMail();
                break;
            case R.id.about:
                controller.goActivity(AboutActivity.class);
                break;
            case R.id.like:
                controller.goLike(tabLayout);
                break;

            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void reloadLoaiMenu(String loaiMenu) {
        String menu;
        if ((menu = MENU_LOAI) != null) {
            if (!menu.equals(loaiMenu)) {
                reloadActivity(loaiMenu);
            }

        } else {
            reloadActivity(loaiMenu);
        }

    }

    private void reloadActivity(String loaiMenu) {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        MainActivity.MENU_LOAI = loaiMenu;
        startActivity(intent);
    }


    DatabaseReference mData;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (linearSearch.getVisibility() == View.VISIBLE) {
                linearSearch.setVisibility(View.GONE);
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Đóng ứng dụng?");
                alertDialogBuilder
                        .setMessage("Bấm Có để thoát!")
                        .setCancelable(false)
                        .setPositiveButton("Có",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                        System.exit(1);
                                    }
                                })

                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

    }


    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public void quang_cao() {
        mAdView = (AdView) findViewById(R.id.adView);
        //Load ads
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Nếu quảng cáo đã tắt tiến hành load quảng cáo
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                loadInterstitialAd();
            }
        });
        //Load sẵn quảng cáo khi ứng dụng mở
        loadInterstitialAd();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    //Load InterstitialAd
    private void loadInterstitialAd() {
        if (mInterstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }

    }

}
