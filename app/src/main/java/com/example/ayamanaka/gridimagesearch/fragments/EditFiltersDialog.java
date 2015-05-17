package com.example.ayamanaka.gridimagesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ayamanaka.gridimagesearch.R;
import com.example.ayamanaka.gridimagesearch.models.QueryParams;

/**
 * Created by ayamanaka on 5/16/15.
 */
public class EditFiltersDialog extends DialogFragment {
    private EditText etSiteFilter;
    private Spinner spinnerSizeFilter;
    private Spinner spinnerColorFilter;
    private Spinner spinnerTypeFilter;
    private QueryParams queryParams;
    private Button btCancel;
    private Button btSave;
    private OnFiltersSavedListener saveListener;

    public EditFiltersDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditFiltersDialog newInstance(String title) {
        EditFiltersDialog frag = new EditFiltersDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public interface OnFiltersSavedListener {
        public void onFiltersSaved(QueryParams queryParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_filters, container);
        queryParams = (QueryParams) getArguments().getSerializable("query_params");
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        spinnerSizeFilter = (Spinner) view.findViewById(R.id.spinnerSizeFilter);
        spinnerColorFilter = (Spinner) view.findViewById(R.id.spinnerColorFilter);
        spinnerTypeFilter = (Spinner) view.findViewById(R.id.spinnerTypeFilter);
        btSave = (Button) view.findViewById(R.id.btSaveFilters);
        btCancel = (Button) view.findViewById(R.id.btCancel);

        String title = getArguments().getString("title", "Choose Search Filters");
        getDialog().setTitle(title);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerSize = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), R.array.size_filter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSizeFilter.setAdapter(adapterSpinnerSize);
        spinnerSizeFilter.setSelection(adapterSpinnerSize.getPosition(queryParams.getSize()));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerColor = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), R.array.color_filter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerColorFilter.setAdapter(adapterSpinnerColor);
        spinnerColorFilter.setSelection(adapterSpinnerColor.getPosition(queryParams.getColor()));


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerType = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), R.array.type_filter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTypeFilter.setAdapter(adapterSpinnerType);
        spinnerTypeFilter.setSelection(adapterSpinnerType.getPosition(queryParams.getImageType()));

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass relevant data back as a result
                setQueryParams();

                if (saveListener != null) {
                    saveListener.onFiltersSaved(queryParams);
                }

                dismiss();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Show soft keyboard automatically
        etSiteFilter.setText(queryParams.getSiteFilter());
        etSiteFilter.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    private void setQueryParams()
    {
        queryParams.setColor(spinnerColorFilter.getSelectedItem().toString().toLowerCase());
        queryParams.setImageType(spinnerTypeFilter.getSelectedItem().toString().toLowerCase());
        queryParams.setSize(spinnerSizeFilter.getSelectedItem().toString().toLowerCase());
        queryParams.setSiteFilter(etSiteFilter.getText().toString().toLowerCase());
    }

    public void setOnFiltersSavedListener(OnFiltersSavedListener listener)
    {
        this.saveListener = listener;
    }
}
