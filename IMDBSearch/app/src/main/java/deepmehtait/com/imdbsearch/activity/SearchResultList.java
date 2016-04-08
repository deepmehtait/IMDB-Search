package deepmehtait.com.imdbsearch.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import deepmehtait.com.imdbsearch.R;
import deepmehtait.com.imdbsearch.modal.SearchDetails;
import deepmehtait.com.imdbsearch.modal.SearchResults;
import deepmehtait.com.imdbsearch.utils.ConnectionStatus;

/**
 * Created by Deep on 06-Apr-16.
 */
// Search Result List Activity will search for movies / tv shows for user. It will make GET request to omdbapi.com with search string
public class SearchResultList extends AppCompatActivity {
    // Create Variables which will be useful for creating toolbar, recyclerView, Adapter etc.
    private Toolbar toolbar;
    private TextView tv;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SearchDetails> SDArraylist = new ArrayList<SearchDetails>();
    SearchResults searchResults;
    SearchResults sr = new SearchResults();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Layout for the activity
        setContentView(R.layout.activity_search_result_list);
        // Get Intent Values
        Intent i = getIntent();
        // Get Search String
        String SearchSting = i.getStringExtra("SearchString");
        // Get year
        String year = i.getStringExtra("year");
        // Get Type of search
        String type = i.getStringExtra("Type");
        // Make Search string URL safe, replace space with + symbol
        SearchSting = SearchSting.replace(" ", "+");

        // Reference Recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_s);
        // Create LayoutManager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        // Reference Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(type);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // Create URL based on search Type.
        // type=moive or type=series
        String URL = new String();
        if (type.matches("Movie")) {
            URL = "http://www.omdbapi.com/?s=" + SearchSting + "&type=" + type + "&y=" + year;
        } else {
            URL = "http://www.omdbapi.com/?s=" + SearchSting + "&type=series&y=" + year;
        }
        // Check for Internet Connectivity before making Network Calls
        if (ConnectionStatus.getInstance(this).isOnline()) {
            // If network connectivity Make Volley JsonObject GET request to URL
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL, (JSONObject) null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    // the response is already constructed as a JSONObject!

                    // Create Modal Object for easy use of Objects
                    SearchResults sr = new SearchResults();
                    // Create Gson Object for processing response
                    Gson gs = new Gson();
                    // Parse JsonObject into Class with Gson Object parser
                    sr = gs.fromJson(response.toString(), SearchResults.class);
                    // Check for valid response or search result found.!
                    if (sr.isResponse()) {
                        // Create adapter and pass Search Result ArrayList
                        adapter = new SearchResultListAdapter(sr.getSearch(), getApplicationContext());
                        // Set Layout Manager
                        recyclerView.setLayoutManager(layoutManager);
                        // Set Adapter
                        recyclerView.setAdapter(adapter);

                    } else {
                        // Notify user as no search result found
                        Toast.makeText(getApplicationContext(), "No Search Result", Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);
        } else {
            // If no network connectivity notify user
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


}
