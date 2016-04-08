package deepmehtait.com.imdbsearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import deepmehtait.com.imdbsearch.R;

import deepmehtait.com.imdbsearch.fragments.Movie;
import deepmehtait.com.imdbsearch.fragments.TV;
import deepmehtait.com.imdbsearch.modal.SearchDetails;
import deepmehtait.com.imdbsearch.modal.SearchResults;
import deepmehtait.com.imdbsearch.sqlite.SqliteHelper;


/**
 * Created by Deep on 04-Apr-16.
 */
// User Dashboard Activity with Two Tab View and Movie, TV Fragments
public class DashboardActivity extends AppCompatActivity {
    // Create Variables which will be useful for creating toolbar, tablayout, viewpager, viewPagerAdapter, imageButton
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ImageButton Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Layout for the activity
        setContentView(R.layout.activity_dashboard);
        // Reference toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // set support for backward compatibility
        setSupportActionBar(toolbar);
        // Set Toolbar Title
        getSupportActionBar().setTitle("IMDB Search");
        // Set Toolbar Icon
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // Reference Tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        // Reference ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Create ViewPagerAdapter with support fragment manager for backward compatibility
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Create Movie Fragment and set it as tab 0(First Tab)
        Movie mv = new Movie();
        viewPagerAdapter.addFragments(mv, "Movie");
        // Create TV Fragment and set it as tab 1(Second Tab)
        TV tv = new TV();
        viewPagerAdapter.addFragments(tv, "TV");
        viewPagerAdapter.getItemPosition(viewPager);
        // Set Adapter
        viewPager.setAdapter(viewPagerAdapter);
        // Set TabLayour
        tabLayout.setupWithViewPager(viewPager);
        // Notify any changes
        viewPager.getAdapter().notifyDataSetChanged();
        // Create Floating Search Button and handle onClick events
        Search = (ImageButton) findViewById(R.id.imageButton);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SearchForm Activity
                Intent i = new Intent(getApplicationContext(), SearchForm.class);
                startActivity(i);
            }
        });

    }

    // Handle onResume state and refresh the viewPager to get updated data on Movies, TV series to display
    @Override
    protected void onResume() {
        super.onResume();
        viewPager.getAdapter().notifyDataSetChanged();
    }
}
