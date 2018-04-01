package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    //Deklarasi Variable Firebase Auth
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inisialisasi Firebase (Auth)
        auth = FirebaseAuth.getInstance();


        TabLayout tab = findViewById(R.id.tab);
        final ViewPager viewPager = findViewById(R.id.pager);

        //Set Text untuk setiap Tab
        tab.addTab(tab.newTab().setText("Posts"));
        tab.addTab(tab.newTab().setText("My Posts"));
        tab.setTabGravity(tab.GRAVITY_FILL);

        //Memangil PagerAdapter
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tab.getTabCount());
        //Set ViewPager Adapater
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        //Ketika Tab Di pilih
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //set viewpager sesuai get posisi tab
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

    //Get Menu untuk menu Logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //ketika ] menu Logout dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get item id
        int id = item.getItemId();

        //Jika id logout di pilih
        if (id == R.id.logout){
            //logout
            auth.signOut();
            //pindah halaman ke Login
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Get user yang sedang login
        FirebaseUser currentUser = auth.getCurrentUser();
        //Bila ada User yang belum login akan dipindahkan ke halaman login
        if (currentUser == null){
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }
    }

    //onClick untuk AddPost
    public void addPost(View view){
        Intent i = new Intent(MainActivity.this, AddPost.class);
       startActivity(i);
    }
}
