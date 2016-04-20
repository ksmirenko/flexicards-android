package com.ksmirenko.flexicards.core.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ksmirenko.flexicards.core.R;
import com.ksmirenko.flexicards.core.data.Category;

/**
 * Activity for category screen.
 *
 * @author Kirill Smirenko
 */
public class CategoryActivity extends AppCompatActivity {
    /**
     * The fragment argument representing the category ID that this fragment represents.
     */
    public static final String ARG_CATEGORY_NAME = "cat_name";

    public static Context getAppContext() {
        return CategoryActivity.context;
    }

    private static Context context;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryActivity.context = getApplicationContext();
        setContentView(R.layout.activity_category);

        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(CategoryActivity.ARG_CATEGORY_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // manually adding category fragment if the state is null
            Bundle arguments = new Bundle();
            arguments.putLong(CategoryFragment.ARG_CATEGORY_ID,
                    getIntent().getLongExtra(CategoryFragment.ARG_CATEGORY_ID, 0));
            CategoryFragment fragment = new CategoryFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.category_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_dictionary) {
            // launcing dictionary activity
            Intent dictIntent = new Intent(this, DictionaryActivity.class);
            dictIntent.putExtra(DictionaryActivity.ARG_CATEGORY_ID,
                    getIntent().getLongExtra(CategoryFragment.ARG_CATEGORY_ID, 0));
            startActivity(dictIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
