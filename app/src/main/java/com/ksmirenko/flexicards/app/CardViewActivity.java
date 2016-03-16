package com.ksmirenko.flexicards.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.ksmirenko.flexicards.app.adapters.CardsPagerAdapter;

import java.util.ArrayList;

/**
 * Activity for card viewing, i.e. main purpose of the app.
 *
 * @author Kirill Smirenko
 */
public class CardViewActivity extends AppCompatActivity
        implements CardContainerFragment.Callbacks {
    // intent arguments
    public static final String ARG_MODULE_ID = "module_id";
    public static final String ARG_IS_BACK_FIRST = "back_first";
    public static final String ARG_IS_RANDOM = "random";
    public static final String ARG_IS_UNANSWERED_ONLY = "unanswered";

    private ViewPager cardContainerPager;
    private Cursor cardCursor;

    private long moduleId;
    private int cardsTotalCount;
    private ArrayList<Long> cardsUnansweredIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);

        // extracting arguments
        Intent intent = getIntent();
        moduleId = intent.getLongExtra(ARG_MODULE_ID, 0);
        boolean isBackFirst = intent.getBooleanExtra(ARG_IS_BACK_FIRST, false);
        boolean isRandom = intent.getBooleanExtra(ARG_IS_RANDOM, true);
        boolean isUnansweredOnly = intent.getBooleanExtra(ARG_IS_UNANSWERED_ONLY, false);

        // obtaining cards
        cardCursor = DatabaseManager.INSTANCE.getModuleCards(moduleId, isRandom, isUnansweredOnly);
        if (isUnansweredOnly && cardCursor.getCount() == 0) {
            cardCursor = DatabaseManager.INSTANCE.getModuleCards(moduleId, isRandom, false);
            Toast.makeText(this, R.string.no_unanswered_cards, Toast.LENGTH_SHORT).show();
        }

        // setting up adapter
//        CardsPagerAdapter pagerAdapter = new CardsPagerAdapter(getSupportFragmentManager(), cardCursor);
        CardsPagerAdapter pagerAdapter = new CardsPagerAdapter(getFragmentManager(), cardCursor, isBackFirst);
        cardContainerPager = (ViewPager) findViewById(R.id.viewpager_card_container);
        cardContainerPager.setAdapter(pagerAdapter);

        // initializing counters
        cardsTotalCount = cardCursor.getCount();
        cardsUnansweredIds = new ArrayList<Long>();
    }

    // callback for cards' buttons
    @Override
    public void onCardButtonClicked(boolean knowIt) {
        int position = cardContainerPager.getCurrentItem();
        // saving information about last viewed card
        if (!knowIt) {
            cardCursor.moveToPosition(position);
            // should I just write "0" for column index in the next line? too long
            cardsUnansweredIds.add(cardCursor.getLong(DatabaseManager.CardQuery.Companion.getCOLUMN_INDEX_ID()));
        }
        if (position + 1 >= cardsTotalCount) {
            // setting module passage result and closing the activity
            Intent intent = new Intent();
            intent.putExtra(CategoryFragment.RES_ARG_CARDS_UNANSWERED, Utils.INSTANCE.listToString(cardsUnansweredIds));
            intent.putExtra(CategoryFragment.RES_ARG_CARDS_UNANSWERED_CNT, cardsUnansweredIds.size());
            intent.putExtra(CategoryFragment.RES_ARG_CARDS_TOTAL_CNT, cardsTotalCount);
            intent.putExtra(CategoryFragment.RES_ARG_MODULE_ID, moduleId);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            cardContainerPager.setCurrentItem(position + 1, true);
        }
    }
}
