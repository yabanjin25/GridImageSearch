package com.example.ayamanaka.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.ayamanaka.gridimagesearch.R;
import com.example.ayamanaka.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.ayamanaka.gridimagesearch.fragments.EditFiltersDialog;
import com.example.ayamanaka.gridimagesearch.models.ImageResult;
import com.example.ayamanaka.gridimagesearch.models.QueryParams;
import com.example.ayamanaka.gridimagesearch.net.GoogleSearchClient;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private GridView gvResults;
    private GoogleSearchClient searchClient;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private QueryParams queryParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        queryParams = new QueryParams("");
        searchClient = new GoogleSearchClient(this);

        searchClient.setGoogleSearchClientListener(new GoogleSearchClient.GoogleSearchClientListener() {
            @Override
            public void onFetchSearchResults(ArrayList<ImageResult> results) {
                imageResults.clear();
                imageResults.addAll(results);
                aImageResults.notifyDataSetChanged();
            }
        });

        imageResults = new ArrayList<>();
        // Create the adapter linking it to the source
        aImageResults = new ImageResultsAdapter(this, this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews()
    {
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the image display activity
                // Create intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // Get image result to display
                ImageResult result = imageResults.get(position);
                // Pass image result into intent
                i.putExtra("url", result.getFullUrl());
                // Launch new activity
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.expandActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryParams.setExpression(query);
                queryParams.setResultsPerPage("8");
                searchClient.fetchSearchResults(queryParams);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //MenuItem filterItem = menu.findItem(R.id.action_filter);
        //filterItem
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_filter) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("query_params", queryParams);
            FragmentManager fm = getSupportFragmentManager();
            EditFiltersDialog editNameDialog = EditFiltersDialog.newInstance("Edit Search Filters");
            editNameDialog.setOnFiltersSavedListener(new EditFiltersDialog.OnFiltersSavedListener() {
                @Override
                public void onFiltersSaved(QueryParams qps) {
                    queryParams = qps;
                }
            });
            editNameDialog.setArguments(bundle);
            editNameDialog.show(fm, "fragment_edit_filters");
        }

        return super.onOptionsItemSelected(item);
    }
}
