package com.ksmirenko.flexicards.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Activity for category screen that shows a link to dictionary and modules list.
 *
 * @author Kirill Smirenko
 */
public class CategoryActivity extends AppCompatActivity {
    /**
     * The fragment argument representing the category ID that this fragment represents.
     */
    public static final String ARG_CATEGORY_NAME = "cat_name";

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // setting up top action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_activity_toolbar);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // old solution for exiting, but it was freezing the app for too long
            //NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
