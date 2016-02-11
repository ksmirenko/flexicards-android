package com.ksmirenko.flexicards.app;

import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import com.ksmirenko.flexicards.app.datatypes.Category;

import java.util.List;

/**
 * Main activity - category selection screen.
 *
 * @author Kirill Smirenko
 */
public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing DB manager and database
        dbmanager = new DatabaseManager(getBaseContext());
        dbmanager.reinit();
        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        // calling StubDataGenerator so that he would create stub categories DB
        StubDataGenerator.INSTANCE.fillDatabaseWithCategories(dbmanager);
        // filling the main list view with categoryInfos
        ListView listView = (ListView) findViewById(R.id.categories_listview);
        Cursor cursor = dbmanager.getCategories();
        CategoryCursorAdapter adapter = new CategoryCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // settings action event handling should be here
                return true;
            case R.id.action_add_card:
                // card add action event handling should be here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            }
            else {
                languageView.setVisibility(View.GONE);
            }
            ((TextView) convertView.findViewById(R.id.textview_mainscr_cat_name))
                    .setText(catInfo.getName());
            return convertView;
        }
    }
}
