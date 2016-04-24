package com.ksmirenko.flexicards.core.layout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ksmirenko.flexicards.core.FlexiDatabaseProvider;
import com.ksmirenko.flexicards.core.R;
import com.ksmirenko.flexicards.core.data.Category;

/**
 * Activity for category screen.
 *
 * @author Kirill Smirenko
 */
public class CategoryActivity extends AppCompatActivity {
    public static final String ARG_CATEGORY_NAME = "cat_name";
    // content shock: toefl-specific constants in core
    private static final String TOEFL_DB_NAME = "toeflcards.db";
    private static final long TOEFL_CATEGORY_ID = 1;
    private static final String TOEFL_CATEGORY_NAME = "TOEFL Cards";

    /**
     * Context for the fragments to work normally.
     */
    public static Context getAppContext() {
        return CategoryActivity.context;
    }

    private static Context context;

    private long categoryId;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryActivity.context = CategoryActivity.this;
        setContentView(R.layout.activity_category);

        // loading or initializing category ID and database
        // in any odd situation, show TOEFL category
        categoryId = getIntent().getLongExtra(CategoryFragment.ARG_CATEGORY_ID, TOEFL_CATEGORY_ID);
        String categoryName = getIntent().getStringExtra(CategoryActivity.ARG_CATEGORY_NAME);
        if (categoryName == null || categoryName.equals("")) {
            categoryName = TOEFL_CATEGORY_NAME;
        }
        if (!FlexiDatabaseProvider.INSTANCE.hasDb()) {
            FlexiDatabaseProvider.INSTANCE.init(context, TOEFL_DB_NAME);
        }

        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(!categoryName.equals(TOEFL_CATEGORY_NAME));

        if (savedInstanceState == null) {
            // manually adding category fragment if the state is null
            Bundle arguments = new Bundle();
            arguments.putLong(CategoryFragment.ARG_CATEGORY_ID, categoryId);
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
            dictIntent.putExtra(DictionaryActivity.ARG_CATEGORY_ID, categoryId);
            startActivity(dictIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
