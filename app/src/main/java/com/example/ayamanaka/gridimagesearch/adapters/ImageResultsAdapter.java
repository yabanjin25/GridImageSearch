package com.example.ayamanaka.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayamanaka.gridimagesearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.ayamanaka.gridimagesearch.activities.SearchActivity;
import com.example.ayamanaka.gridimagesearch.models.ImageResult;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    private final int REQUEST_CODE = 20;
    private final Context context;
    private final List<ImageResult> imageResults;
    private SearchActivity activity;

    public ImageResultsAdapter(Context context, SearchActivity activity, List<ImageResult> imageResults) {
        super(context, R.layout.item_image_result, imageResults);
        this.context = context;
        this.activity = activity;
        this.imageResults = imageResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ImageResult result = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            // create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        ImageView ivResultImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        ivResultImage.setImageResource(0);
        tvTitle.setText(Html.fromHtml(result.getTitle()));
        // Insert the image using picasso (send out async)
        Picasso.with(getContext())
                .load(result.getThumbUrl())
                .into(ivResultImage);
        //Return the created item as a view
        return convertView;
    }
}