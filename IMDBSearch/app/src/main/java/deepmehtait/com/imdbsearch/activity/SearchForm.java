package deepmehtait.com.imdbsearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import deepmehtait.com.imdbsearch.R;
import deepmehtait.com.imdbsearch.utils.ConnectionStatus;

/**
 * Created by Deep on 06-Apr-16.
 */
// SearchForm Activity Will let user Provide form to search movies, tv series
public class SearchForm extends AppCompatActivity {
    // Create Variables which will be useful for creating toolbar, Search form inputs
    private Toolbar toolbar;
    private Button SearchButton;
    private EditText SearchString, yearNumb;
    private RadioGroup SearchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Layout for the activity
        setContentView(R.layout.activity_searchform);
        // Reference toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // set support for backward compatibility
        setSupportActionBar(toolbar);
        // Set Toolbar Title
        getSupportActionBar().setTitle("Search");
        // Set Toolbar Icon
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        // Reference form elements
        SearchString = (EditText) findViewById(R.id.SearchName);
        yearNumb = (EditText) findViewById(R.id.year);
        SearchType = (RadioGroup) findViewById(R.id.SearchTypeRG);
        SearchButton = (Button) findViewById(R.id.BtnSearch);
        // Set Search Button onClickListner
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SS = SearchString.getText().toString();
                String Year = yearNumb.getText().toString();
                String RadioSelection = new String();
                // Identify Type of Search Movie or Tv series
                if (SearchType.getCheckedRadioButtonId() != -1) {
                    int id = SearchType.getCheckedRadioButtonId();
                    View radioButton = SearchType.findViewById(id);
                    int radioId = SearchType.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) SearchType.getChildAt(radioId);
                    RadioSelection = (String) btn.getText();
                }
                // Check for Internet Connectivity before making Network Calls
                if (ConnectionStatus.getInstance(getApplicationContext()).isOnline()) {
                    // If network connectivity Start Search List Activity
                    Intent i = new Intent(getApplicationContext(), SearchResultList.class);
                    // Put Intent Extras
                    i.putExtra("SearchString", SS);
                    i.putExtra("year", Year);
                    i.putExtra("Type", RadioSelection);
                    startActivity(i);
                } else {
                    // If no network connectivity notify user
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
