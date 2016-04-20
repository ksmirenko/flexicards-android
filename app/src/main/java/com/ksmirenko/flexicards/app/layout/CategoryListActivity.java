package com.ksmirenko.flexicards.app.layout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.*;
import android.widget.*;

import com.ksmirenko.flexicards.app.R;
import com.ksmirenko.flexicards.app.StubDataGenerator;
import com.ksmirenko.flexicards.app.adapters.CategoryCursorAdapter;
import com.ksmirenko.flexicards.core.data.Category;
import com.ksmirenko.flexicards.core.DatabaseManager;
import com.ksmirenko.flexicards.core.layout.CategoryActivity;
import com.ksmirenko.flexicards.core.layout.CategoryFragment;

import java.util.List;

/**
 * Main activity - category selection screen.
 *
 * @author Kirill Smirenko
 */
public class CategoryListActivity extends AppCompatActivity {
    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(toolbar);
        // initializing DB tables if not exist
        DatabaseManager.INSTANCE.init();
        //DatabaseManager.INSTANCE.resetAll(); // FOR TESTING ONLY
        // calling StubDataGenerator
        StubDataGenerator.INSTANCE.fillDatabaseIfEmptyOrOutdated(DatabaseManager.INSTANCE);
        // filling the main list view with categoryInfos
        ListView listView = (ListView) findViewById(R.id.listview_dictionary);
        Cursor cursor = DatabaseManager.INSTANCE.getCategories();
        final CategoryCursorAdapter adapter = new CategoryCursorAdapter(this, cursor);
        assert listView != null;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // launching category detail activity
                Intent detailIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                detailIntent.putExtra(CategoryFragment.ARG_CATEGORY_ID, id);
                detailIntent.putExtra(CategoryActivity.ARG_CATEGORY_NAME, adapter.getCategoryName(position));
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                showAboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAboutDialog() {
        final SpannableString s = new SpannableString(getString(R.string.text_about));
        Linkify.addLinks(s, Linkify.ALL);

        final AlertDialog d = new AlertDialog.Builder(CategoryListActivity.this)
                .setPositiveButton(android.R.string.ok, null)
                .setMessage(s)
                .create();
        d.show();

        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

        /*LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dlgView = inflater.inflate(R.layout.fragment_about, null, false);
        final TextView tv = (TextView) dlgView.findViewById(R.id.textview_about_contents);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        // showing module settings dialog dialog
        new AlertDialog.Builder(CategoryListActivity.this)
                .setView(dlgView)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                .show();*/
    }

    private static class CategoryArrayAdapter extends ArrayAdapter<Category> {
        public CategoryArrayAdapter(Context context, List<Category> categories) {
            super(context, R.layout.listview_item_categories, categories);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Category catInfo = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.listview_item_categories, null);
            }
            TextView languageView = (TextView) convertView.findViewById(R.id.textview_mainscr_cat_language);
            languageView.setText(catInfo.getLanguage());
            if (position == 0 || !getItem(position - 1).getLanguage().equals(catInfo.getLanguage())) {
                languageView.setVisibility(View.VISIBLE);
            } else {
                languageView.setVisibility(View.GONE);
            }
            ((TextView) convertView.findViewById(R.id.textview_mainscr_cat_name))
                    .setText(catInfo.getName());
            return convertView;
        }
    }
}
