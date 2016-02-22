package com.ksmirenko.flexicards.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class CategoryFragment extends Fragment {
    /**
     * The fragment argument representing the category ID that this fragment represents.
     */
    public static final String ARG_CATEGORY_ID = "cat_id";

    /**
     * The category's modules.
     */
    private CursorAdapter modulesAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments.containsKey(ARG_CATEGORY_ID)) {
            // loading cursor to the list of modules
            Cursor cursor = DatabaseManager.INSTANCE.getModules(arguments.getLong(ARG_CATEGORY_ID));
            modulesAdapter = new ModuleCursorAdapter(getContext(), cursor);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        // filling the list with modules and setting up onClick
        if (modulesAdapter != null) {
            ListView listView = (ListView) rootView.findViewById(R.id.listview_modules);
            listView.setAdapter(modulesAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // launching card view activity
                    Intent detailIntent = new Intent(getContext(), CardsViewActivity.class);
                    detailIntent.putExtra(CardsViewActivity.ARG_MODULE_ID, id);
                    startActivity(detailIntent);
                }
            });
        }

        return rootView;
    }
}
