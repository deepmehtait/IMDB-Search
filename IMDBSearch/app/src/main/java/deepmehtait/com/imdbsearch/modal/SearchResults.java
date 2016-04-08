package deepmehtait.com.imdbsearch.modal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 04-Apr-16.
 */
// Modal Class for Search Results
public class SearchResults {
    public boolean isResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        Response = response;
    }

    public ArrayList<SearchDetails> getSearch() {
        return Search;
    }

    public void setSearch(ArrayList<SearchDetails> search) {
        Search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    ArrayList<SearchDetails> Search;
    int totalResults;

    boolean Response;

}
