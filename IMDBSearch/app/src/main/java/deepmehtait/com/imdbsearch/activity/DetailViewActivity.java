package deepmehtait.com.imdbsearch.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import deepmehtait.com.imdbsearch.R;
import deepmehtait.com.imdbsearch.modal.DetailView;
import deepmehtait.com.imdbsearch.modal.SearchDetails;
import deepmehtait.com.imdbsearch.modal.SearchResults;
import deepmehtait.com.imdbsearch.sqlite.SqliteHelper;
import deepmehtait.com.imdbsearch.utils.ConnectionStatus;

/**
 * Created by Deep on 07-Apr-16.
 */
// Displays Detailed View for Movie/TV shows and saves them into DB
public class DetailViewActivity extends AppCompatActivity {
    // Create Variables which will be useful for creating Sql Operation,ImageView etc.
    ImageView PosterImage;
    Toolbar toolbar;
    TextView Title, year, ImdbRating, Released, Director, Actors, Plot, Genre, Runtime, Rated, Language, Awards;
    SqliteHelper mySqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Layout for the activity
        setContentView(R.layout.activity_detail_view);
        // Reference Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // Get Intent Data
        Intent i = getIntent();
        String imdbid = i.getStringExtra("imdbID");
        String type = i.getStringExtra("type");
        // Request URL
        String URL = "http://www.omdbapi.com/?plot=full&r=json&i=" + imdbid;
        // Check for Internet Connectivity before making Network Calls
        if (ConnectionStatus.getInstance(this).isOnline()) {
            // If network connectivity Make Volley JsonObject GET request to URL
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL, (JSONObject) null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // the response is already constructed as a JSONObject!

                    // Create Gson Object for processing response
                    Gson gs = new Gson();
                    // Create SqliteHelper Object which will be used to insert data
                    mySqlite = new SqliteHelper(getApplicationContext());
                    // Create Modal Object for easy use of Objects
                    DetailView dv = new DetailView();
                    //// Parse JsonObject into Class with Gson Object parser
                    dv = gs.fromJson(response.toString(), DetailView.class);
                    // Check for valid response
                    if (dv.getResponse().matches("True")) {
                        // Insert Data into DB
                        boolean isinsterted = mySqlite.insertData(dv.getImdbID(), dv.getPoster(), dv.getTitle(), dv.getYear(), dv.getType());
                        // Create Reference
                        PosterImage = (ImageView) findViewById(R.id.bigPoster);
                        Title = (TextView) findViewById(R.id.mTitle);
                        year = (TextView) findViewById(R.id.mYear);
                        ImdbRating = (TextView) findViewById(R.id.mRating);
                        Released = (TextView) findViewById(R.id.mReleased);
                        Director = (TextView) findViewById(R.id.mDirector);
                        Actors = (TextView) findViewById(R.id.mActors);
                        Plot = (TextView) findViewById(R.id.mPlot);
                        Genre = (TextView) findViewById(R.id.mGenre);
                        Runtime = (TextView) findViewById(R.id.mRuntime);
                        Rated = (TextView) findViewById(R.id.mRated);
                        Language = (TextView) findViewById(R.id.mLanguage);
                        Awards = (TextView) findViewById(R.id.mAwards);
                        if (dv.getPoster() != null && !dv.getPoster().matches("N/A")) {
                            // Get Images using Picasso
                            Picasso.with(getApplicationContext()).load(dv.getPoster()).fit().centerCrop().into(PosterImage);
                        } else {
                            Log.d("in Picasso Else", " no image");
                            PosterImage.setImageResource(R.drawable.noimg);

                        }

                        if (dv.getTitle() != null && !dv.getTitle().matches("N/A")) {
                            Title.setText(dv.getTitle());
                        }
                        if (dv.getYear() != null && !dv.getYear().matches("N/A")) {
                            year.setText("Year: " + dv.getYear());
                        }
                        if (dv.getImdbRating() != null && !dv.getImdbRating().matches("N/A")) {
                            ImdbRating.setText(dv.getImdbRating() + "/10");
                        }
                        if (dv.getReleased() != null && !dv.getReleased().matches("N/A")) {
                            Released.setText("Released: " + dv.getReleased());
                        }
                        if (dv.getDirector() != null && !dv.getDirector().matches("N/A")) {
                            Director.setText("Director: " + dv.getDirector());
                        }
                        if (dv.getActors() != null && !dv.getActors().matches("N/A")) {
                            Actors.setText("Actors: " + dv.getActors());
                        }
                        if (dv.getPlot() != null && !dv.getPlot().matches("N/A")) {
                            Plot.setText("Plot: " + dv.getPlot());
                        }
                        if (dv.getGenre() != null && !dv.getGenre().matches("N/A")) {
                            Genre.setText("Gener: " + dv.getGenre());
                        }
                        if (dv.getRuntime() != null && !dv.getRuntime().matches("N/A")) {
                            Runtime.setText("Runtime: " + dv.getRuntime());
                        }
                        if (dv.getRated() != null && !dv.getRated().matches("N/A")) {
                            Rated.setText("Rated: " + dv.getRated());
                        }
                        if (dv.getLanguage() != null && !dv.getLanguage().matches("N/A")) {
                            Language.setText("Language: " + dv.getLanguage());
                        }
                        if (dv.getAwards() != null && !dv.getAwards().matches("N/A")) {
                            Awards.setText("Awards: " + dv.getAwards());
                        }
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
