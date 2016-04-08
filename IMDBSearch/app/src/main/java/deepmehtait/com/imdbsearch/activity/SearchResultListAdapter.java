package deepmehtait.com.imdbsearch.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import deepmehtait.com.imdbsearch.R;
import deepmehtait.com.imdbsearch.modal.SearchDetails;

/**
 * Created by Deep on 07-Apr-16.
 */
// Creates RecyclerView with Card View and parses ArrayList to create view
public class SearchResultListAdapter extends RecyclerView.Adapter<SearchResultListAdapter.SearchResultListViewHolder> {
    // Save Arraylist for future use
    List<SearchDetails> searchDetailsArrayList = new ArrayList<SearchDetails>();
    // Application Context
    Context context;

    // constructure for SearchResultListAdapter
    public SearchResultListAdapter(String name, String year) {
        SearchDetails s = new SearchDetails();
        s.setTitle(name);
        s.setYear(year);
        searchDetailsArrayList.add(s);
    }

    // constructure for SearchResultListAdapter
    public SearchResultListAdapter(ArrayList<SearchDetails> searchDetailsList, Context context) {
        this.searchDetailsArrayList = searchDetailsList;
        this.context = context;
    }

    // Inflate views and return viewholder
    @Override
    public SearchResultListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        SearchResultListViewHolder srlvh = new SearchResultListViewHolder(view, context);
        return srlvh;
    }

    // Buind viewholder with data based on position
    @Override
    public void onBindViewHolder(SearchResultListViewHolder holder, int position) {
        SearchDetails sd = searchDetailsArrayList.get(position);
        // Set Movie/TV Show title
        holder.movie_name.setText(sd.getTitle());
        // Set Year
        holder.movie_year.setText(sd.getYear());
        // Save Object Value for that position
        holder.searchDetails = searchDetailsArrayList.get(position);
        // Download Image of poster from URL, Check if no url display default image
        if (sd.getPoster() != null && !sd.getPoster().matches("N/A")) {
            // Use Picasso .load image .into to get image from URL
            Picasso.with(context).load(sd.getPoster()).centerCrop().fit().into(holder.poster_image);
        } else {
            // set default image for no image url
            holder.poster_image.setImageResource(R.drawable.noimg);

        }

    }

    @Override
    public int getItemCount() {
        return searchDetailsArrayList.size();
    }

    // Create onclick event on card view and Start Detail view activity for that element
    public static class SearchResultListViewHolder extends RecyclerView.ViewHolder {
        ImageView poster_image;
        TextView movie_name, movie_year;
        SearchDetails searchDetails;

        public SearchResultListViewHolder(View view, final Context context) {
            super(view);
            poster_image = (ImageView) view.findViewById(R.id.posterImage);
            movie_name = (TextView) view.findViewById(R.id.movieTitle);
            movie_year = (TextView) view.findViewById(R.id.movieYear);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // clicked
                    int postion = getAdapterPosition();

                    // Start New Activity and Put Intent Extras
                    Intent i = new Intent(context.getApplicationContext(), DetailViewActivity.class);
                    i.putExtra("imdbID", searchDetails.getImdbID());
                    i.putExtra("type", searchDetails.getType());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(i);

                }
            });

        }
    }
}
