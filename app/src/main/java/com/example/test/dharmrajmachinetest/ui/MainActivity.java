package com.example.test.dharmrajmachinetest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.ui.base.BaseActivity;
import com.example.test.dharmrajmachinetest.ui.fragment.AddCategoryFragment;
import com.example.test.dharmrajmachinetest.ui.fragment.AddProductFragment;
import com.example.test.dharmrajmachinetest.ui.fragment.GenerateBillFragment;
import com.example.test.dharmrajmachinetest.ui.fragment.WelcomeFragment;
import com.example.test.dharmrajmachinetest.utility.Constants;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addFragment(WelcomeFragment.newInstance(), false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        Intent intent = null;
        int id = item.getItemId();

        if (id == R.id.nav_addCategory) {
            addFragment(AddCategoryFragment.newInstance(), true);
            //intent = new Intent(this, AddCategoryActivity.class);
        } else if (id == R.id.nav_addItem) {
            addFragment(AddProductFragment.newInstance(null), true);
            //intent = new Intent(this, AddItemActivity.class);
        } else if (id == R.id.nav_generateBill) {
            addFragment(GenerateBillFragment.newInstance(null), true);
            //intent = new Intent(this, GenerateBillActivity.class);
        }else if (id == R.id.nav_editProduct) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.EditableFragment, true);
            addFragment(GenerateBillFragment.newInstance(bundle), true);
            //intent = new Intent(this, GenerateBillActivity.class);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
       /* if(intent!=null){
            startActivity(intent);
        }*/
        return true;
    }

    @Override
    public int getContainerViewId() {
        return R.id.containerView;
    }

}
