package deepmehtait.com.imdbsearch.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import deepmehtait.com.imdbsearch.R;
import deepmehtait.com.imdbsearch.activity.SearchResultListAdapter;
import deepmehtait.com.imdbsearch.modal.SearchDetails;
import deepmehtait.com.imdbsearch.sqlite.SqliteHelper;

/**
 * A simple {@link Fragment} subclass.
 */
// Movie Fragment
public class Movie extends Fragment {

    SqliteHelper mySqlite;

    private View myView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public Movie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Create SqliteHelper Object for getting Movie List
        mySqlite = new SqliteHelper(getActivity().getApplicationContext());
        // Arraylist to store result
        ArrayList<SearchDetails> arSD = new ArrayList<SearchDetails>();
        // Cursor Object for resutl
        Cursor res = mySqlite.getAllMovies();
        // If any rows Display
        if (res.getCount() == 0) {
            // No rows Notify User
            Toast.makeText(getActivity().getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
        } else {
            // Loop till all rows
            while (res.moveToNext()) {
                // Create Object and Store in ArrayList
                SearchDetails sd = new SearchDetails();
                sd.setImdbID(res.getString(0));
                sd.setPoster(res.getString(1));
                sd.setTitle(res.getString(2));
                sd.setYear(res.getString(3));
                sd.setType(res.getString(4));
                // Add
                arSD.add(sd);

            }
        }

        // Reference View Object
        myView = inflater.inflate(R.layout.activity_search_result_list, container, false);
        // Create Recycler view
        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view_s);
        // Add padding
        recyclerView.setPadding(0, 150, 0, 0);
        // Create LayoutManager
        layoutManager = new LinearLayoutManager(myView.getContext());
        // Create adapter
        adapter = new SearchResultListAdapter(arSD, myView.getContext());
        // set layout
        recyclerView.setLayoutManager(layoutManager);
        // Set adapter
        recyclerView.setAdapter(adapter);
        // Return view
        return myView;
    }

}
