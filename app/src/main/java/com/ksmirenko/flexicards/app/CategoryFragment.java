package com.ksmirenko.flexicards.app;

import android.app.Activity;
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
import android.widget.Toast;
import com.ksmirenko.flexicards.app.adapters.ModuleCursorAdapter;

public class CategoryFragment extends Fragment {
    /**
     * The fragment argument representing the category ID that this fragment represents.
     */
    public static final String ARG_CATEGORY_ID = "cat_id";

    // Request code and arguments for CardViewActivity result
    public static final int RES_REQUEST_CODE = 1;
    public static final String ARG_CARDS_UNANSWERED = "CARDS_UNANSWERED";
    public static final String ARG_CARDS_UNANSWERED_CNT = "CARDS_UNANSWERED_CNT";
    public static final String ARG_CARDS_TOTAL_CNT = "CARDS_TOTAL_CNT";

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
                    Intent detailIntent = new Intent(getContext(), CardViewActivity.class);
                    detailIntent.putExtra(CardViewActivity.ARG_MODULE_ID, id);
                    startActivityForResult(detailIntent, RES_REQUEST_CODE);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int unansweredCount = data.getIntExtra(ARG_CARDS_UNANSWERED_CNT, 0);
            int totalCount = data.getIntExtra(ARG_CARDS_TOTAL_CNT, 0);
            // TODO: save user progress on module
            //String unanswered = data.getStringExtra(ARG_CARDS_UNANSWERED);
            // TODO: show dialog with [rerun all | rerun unanswered | close]
            Toast.makeText(
                    getContext(),
                    "Cards answered: " + (totalCount - unansweredCount) + "/" + totalCount,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
