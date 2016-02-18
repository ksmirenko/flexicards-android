package com.ksmirenko.flexicards.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.ksmirenko.flexicards.app.datatypes.Category;

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

        // filling the list with modules
        if (modulesAdapter != null) {
            ((ListView) rootView.findViewById(R.id.listview_modules)).setAdapter(modulesAdapter);
        }

        return rootView;
    }
}
