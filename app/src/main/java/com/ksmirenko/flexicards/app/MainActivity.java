package com.ksmirenko.flexicards.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import com.ksmirenko.flexicards.app.adapters.CategoryCursorAdapter;
import com.ksmirenko.flexicards.app.datatypes.Category;

import java.util.List;

/**
 * Main activity - category selection screen.
 *
 * @author Kirill Smirenko
 */
public class MainActivity extends AppCompatActivity {
    private static Context context;
    private DatabaseManager dbmanager;

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(toolbar);
        // initializing DB tables if not exist
        DatabaseManager.INSTANCE.init();
        //DatabaseManager.INSTANCE.resetAll(); // FOR TESTING ONLY
        // calling StubDataGenerator
        StubDataGenerator.INSTANCE.fillDatabaseIfEmpty(DatabaseManager.INSTANCE);
        // filling the main list view with categoryInfos
        ListView listView = (ListView) findViewById(R.id.listview_dictionary);
        Cursor cursor = DatabaseManager.INSTANCE.getCategories();
        final CategoryCursorAdapter adapter = new CategoryCursorAdapter(this, cursor);
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
        return false;
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
