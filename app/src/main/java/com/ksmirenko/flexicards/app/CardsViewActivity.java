package com.ksmirenko.flexicards.app;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
//        Cursor cursor = DatabaseManager.INSTANCE.getCards(moduleId, true);
        // TODO: fill ViewFlipper, add animation and card changing
    }
}
