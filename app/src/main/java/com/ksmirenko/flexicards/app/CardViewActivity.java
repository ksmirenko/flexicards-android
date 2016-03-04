package com.ksmirenko.flexicards.app;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.ksmirenko.flexicards.app.adapters.CardsPagerAdapter;

/**
 * Activity for card viewing, i.e. main purpose of the app.
 *
 * @author Kirill Smirenko
 */
public class CardViewActivity extends AppCompatActivity
        implements CardContainerFragment.Callbacks {
    /**
     * The argument representing ID of the currently viewed module.
     */
    public static final String ARG_MODULE_ID = "module_id";
    private ViewPager cardContainerPager;
    private int cardsTotalCount;
    private int cardsAnsweredCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);

        long moduleId = getIntent().getLongExtra(ARG_MODULE_ID, 0);
        Cursor cursor = DatabaseManager.INSTANCE.getModuleCards(moduleId);
//        CardsPagerAdapter pagerAdapter = new CardsPagerAdapter(getSupportFragmentManager(), cursor);
        CardsPagerAdapter pagerAdapter = new CardsPagerAdapter(getFragmentManager(), cursor);
        cardContainerPager = (ViewPager) findViewById(R.id.viewpager_card_container);
        cardContainerPager.setAdapter(pagerAdapter);

        // initializing counters
        cardsTotalCount = cursor.getCount();
        cardsAnsweredCount = 0;
    }

    @Override
    public void onCardButtonClicked(boolean knowIt) {
        int newPosition = cardContainerPager.getCurrentItem() + 1;
        if (newPosition == cardsTotalCount) {
            // TODO: module finish dialog
            finish();
        }
        else {
            if (knowIt) cardsAnsweredCount++;
            cardContainerPager.setCurrentItem(newPosition, true);
        }
    }
}
