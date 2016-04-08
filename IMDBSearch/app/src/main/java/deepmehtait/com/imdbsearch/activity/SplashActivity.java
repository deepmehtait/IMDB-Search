package deepmehtait.com.imdbsearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import deepmehtait.com.imdbsearch.sqlite.SqliteHelper;

/**
 * Created by Deep on 04-Apr-16.
 */

// Display Splash Screen while DB gets created and loaded
public class SplashActivity extends AppCompatActivity {
    SqliteHelper mySqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create Sqlite Db
        mySqliteHelper = new SqliteHelper(this);
        // Start Dashboard Activity
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}