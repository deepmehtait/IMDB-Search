package deepmehtait.com.imdbsearch.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import deepmehtait.com.imdbsearch.R;
import deepmehtait.com.imdbsearch.activity.SearchResultListAdapter;
import deepmehtait.com.imdbsearch.modal.SearchDetails;
import deepmehtait.com.imdbsearch.sqlite.SqliteHelper;

public class TV extends Fragment {
    SqliteHelper mySqlite;
    private View MyView;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public TV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Create SqliteHelper Object for getting TV Series List
        mySqlite = new SqliteHelper(getActivity().getApplicationContext());

        ArrayList<SearchDetails> arSD = new ArrayList<SearchDetails>();
        Cursor res = mySqlite.getAllSeries();

        if (res.getCount() == 0) {
            // // No rows Notify User
            Toast.makeText(getActivity().getApplicationContext(), "No Series", Toast.LENGTH_SHORT).show();
        } else {
            while (res.moveToNext()) {
                SearchDetails sd = new SearchDetails();
                sd.setImdbID(res.getString(0));
                sd.setPoster(res.getString(1));
                sd.setTitle(res.getString(2));
                sd.setYear(res.getString(3));
                sd.setType(res.getString(4));
                arSD.add(sd);

            }
        }


        MyView = inflater.inflate(R.layout.activity_search_result_list, container, false);
        recyclerView = (RecyclerView) MyView.findViewById(R.id.recycler_view_s);
        recyclerView.setPadding(0, 150, 0, 0);
        layoutManager = new LinearLayoutManager(MyView.getContext());
        adapter = new SearchResultListAdapter(arSD, MyView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // return view
        return MyView;
    }


}
