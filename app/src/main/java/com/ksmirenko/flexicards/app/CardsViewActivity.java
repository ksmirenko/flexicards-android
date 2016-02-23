package com.ksmirenko.flexicards.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class CardsViewActivity extends AppCompatActivity {
    /**
     * The fragment argument representing the category ID that this fragment represents.
     */
    public static final String ARG_MODULE_ID = "module_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);

        long moduleId = getIntent().getLongExtra(ARG_MODULE_ID, 0);
        Cursor cursor = DatabaseManager.INSTANCE.getModuleCards(moduleId);
//        ((ViewPager) findViewById(R.id.viewpager_card_container)).setAdapter();
    }
}
