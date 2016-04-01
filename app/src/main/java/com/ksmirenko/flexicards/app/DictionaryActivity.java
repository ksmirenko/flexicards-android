package com.ksmirenko.flexicards.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
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

    private CursorAdapter adapter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_dictionary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.dictionary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // filling the main list view
        long categoryId = getIntent().getLongExtra(ARG_CATEGORY_ID, 0);
        final Cursor cursor = DatabaseManager.INSTANCE.getDictionary(categoryId);
        //adapter = new DictionaryCursorAdapter(this, cursor);
        adapter = new SimpleCursorAdapter(this, R.layout.listview_item_dictionary, cursor,
                DatabaseManager.CardQuery.Companion.getCursorAdapterArg(),
                new int[]{R.id.textview_listitem_dict_front, R.id.textview_listitem_dict_back},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        adapter.setFilterQueryProvider(new DatabaseManager.DictionaryFilterQueryProvider(categoryId));
        final ListView listView = (ListView) findViewById(R.id.listview_dictionary);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_dictionary, menu);

        // associating searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_dict).getActionView();
        searchView.setMaxWidth(10000);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            adapter.getFilter().filter(query);
            adapter.notifyDataSetChanged();
        }
    }
}
