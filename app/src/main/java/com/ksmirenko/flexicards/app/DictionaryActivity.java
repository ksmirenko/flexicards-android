package com.ksmirenko.flexicards.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.ksmirenko.flexicards.app.adapters.DictionaryCursorAdapter;

/**
 * Dictionary activity - category selection screen.
 *
 * @author Kirill Smirenko
 */
public class DictionaryActivity extends AppCompatActivity {
    /**
     * The argument representing ID of the category whose dictionary is viewed.
     */
    public static final String ARG_CATEGORY_ID = "category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(toolbar);

        // filling the main list view with categoryInfos
        long categoryId = getIntent().getLongExtra(ARG_CATEGORY_ID, 0);
        Cursor cursor = DatabaseManager.INSTANCE.getDictionary(categoryId);
        final DictionaryCursorAdapter adapter = new DictionaryCursorAdapter(this, cursor);
        ListView listView = (ListView) findViewById(R.id.listview_dictionary);
        listView.setAdapter(adapter);
        // TODO: onclicklistener for showing details
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
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
}
