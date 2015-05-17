package com.example.ayamanaka.gridimagesearch.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.ayamanaka.gridimagesearch.activities.SearchActivity;
import com.example.ayamanaka.gridimagesearch.models.ImageResult;
import com.example.ayamanaka.gridimagesearch.models.QueryParams;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GoogleSearchClient {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

    private GoogleSearchClientListener listener;
    private AsyncHttpClient asyncClient;
    private SearchActivity searchActivity;

    public interface GoogleSearchClientListener {
        public void onFetchSearchResults(ArrayList<ImageResult> results);
    }

    public GoogleSearchClient(SearchActivity searchActivity)
    {
        this.listener = null;
        this.asyncClient = new AsyncHttpClient();
        this.searchActivity = searchActivity;
    }

    public void fetchSearchResults(QueryParams query) {
        if (!isNetworkAvailable()) {
            Toast.makeText(searchActivity, "No internet connection. Please connect to internet and try again.", Toast.LENGTH_LONG).show();
            return;
        }

        String searchUrl = getSearchUrl(query);

        // Trigger the GET request
        asyncClient.get(searchUrl, new JsonHttpResponseHandler() {
            // onSuccess (worked)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<ImageResult> imageResults = new ArrayList<>();
                // Iterate each of the result items and decode the item into a java object
                JSONArray resultsJSON = null;
                try {
                    resultsJSON = response.getJSONObject("responseData").getJSONArray("results");     // array of results
                    for (int i = 0; i < resultsJSON.length(); i++) {
                        // get JSONObject at that position
                        JSONObject resultJSON = resultsJSON.getJSONObject(i);
                        // decode the attributes of the JSON into a data model
                        ImageResult result = getImageResultFromJSONObject(resultJSON);
                        imageResults.add(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (listener != null) {
                    listener.onFetchSearchResults(imageResults); // <---- fire listener here
                }
            }

            // onFailure (fail)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(searchActivity, "An error occurred while trying to retrieve results.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setGoogleSearchClientListener(GoogleSearchClientListener listener) {
        this.listener = listener;
    }

    protected ImageResult getImageResultFromJSONObject(JSONObject resultJSONObject)
    {
        try {
            ImageResult imageResult = new ImageResult();

            imageResult.setId(resultJSONObject.getString("imageId"));
            imageResult.setHeight(resultJSONObject.getString("height"));
            imageResult.setWidth(resultJSONObject.getString("width"));
            imageResult.setFullUrl(resultJSONObject.getString("url"));
            imageResult.setThumbUrl(resultJSONObject.getString("tbUrl"));
            imageResult.setSiteOrigin(resultJSONObject.getString("visibleUrl"));
            imageResult.setTitle(resultJSONObject.getString("title"));
            imageResult.setTitleNoFormatting(resultJSONObject.getString("titleNoFormatting"));

            return imageResult;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String getSearchUrl(QueryParams query)
    {
        return BASE_URL + query.toString();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) searchActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
