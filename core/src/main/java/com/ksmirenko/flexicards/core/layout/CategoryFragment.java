package com.ksmirenko.flexicards.core.layout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.ksmirenko.flexicards.core.DatabaseManager;
import com.ksmirenko.flexicards.core.R;
import com.ksmirenko.flexicards.core.adapters.ModuleCursorAdapter;

/**
 * Fragment for category screen, contains a dictionary button and list of modules.
 *
 * @author Kirill Smirenko
 */
public class CategoryFragment extends Fragment {
    /**
     * The fragment argument representing the category ID that this fragment represents.
     */
    public static final String ARG_CATEGORY_ID = "cat_id";

    // Request code and arguments for CardActivity result
    public static final int RES_REQUEST_CODE = 1;
    public static final String RES_ARG_CARDS_UNANSWERED = "CARDS_UNANSWERED";
    public static final String RES_ARG_CARDS_UNANSWERED_CNT = "CARDS_UNANSWERED_CNT";
    public static final String RES_ARG_CARDS_TOTAL_CNT = "CARDS_TOTAL_CNT";
    public static final String RES_ARG_MODULE_ID = "MODULE_ID";

    private long categoryId;
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
            categoryId = arguments.getLong(ARG_CATEGORY_ID);
            Cursor cursor = DatabaseManager.INSTANCE.getModules(categoryId);
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
                    // preparing module settings dialog
                    final long moduleId = id;
                    final Context context = view.getContext();
                    LayoutInflater inflater =
                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dlgView = inflater.inflate(R.layout.fragment_module_settings, null, false);
                    final TextView tvWhichSide = (TextView) dlgView.findViewById(R.id.textview_dlg_module_whichside);
                    final Switch switchWhichSide = (Switch) dlgView.findViewById(R.id.switch_dlg_module_whichside);
                    final CheckBox cbRandom = (CheckBox) dlgView.findViewById(R.id.cb_dlg_module_random);
                    final CheckBox cbUnanswered = (CheckBox) dlgView.findViewById(R.id.cb_dlg_module_unanswered);
                    switchWhichSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            tvWhichSide.setText(isChecked ? R.string.back_side_first : R.string.front_side_first);
                        }
                    });
                    // showing module settings dialog dialog
                    new AlertDialog.Builder(context)
                            .setView(dlgView)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                            // launching card view activity
                                            Intent detailIntent = new Intent(getContext(), CardActivity.class);
                                            detailIntent.putExtra(CardActivity.ARG_MODULE_ID, moduleId);
                                            detailIntent.putExtra(CardActivity.ARG_IS_BACK_FIRST,
                                                    switchWhichSide.isChecked());
                                            detailIntent.putExtra(CardActivity.ARG_IS_RANDOM,
                                                    cbRandom.isChecked());
                                            detailIntent.putExtra(CardActivity.ARG_IS_UNANSWERED_ONLY,
                                                    cbUnanswered.isChecked());
                                            startActivityForResult(detailIntent, RES_REQUEST_CODE);
                                        }
                                    }).show();
                }
            });
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int unansweredCount = data.getIntExtra(RES_ARG_CARDS_UNANSWERED_CNT, -1);
            int totalCount = data.getIntExtra(RES_ARG_CARDS_TOTAL_CNT, -1);
            String unanswered = data.getStringExtra(RES_ARG_CARDS_UNANSWERED);
            long moduleId = data.getLongExtra(RES_ARG_MODULE_ID, -1);
            DatabaseManager.INSTANCE.updateModuleProgress(moduleId, unanswered);
            Toast.makeText(
                    getContext(),
                    getString(R.string.cards_answered) + (totalCount - unansweredCount) + "/" + totalCount,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
