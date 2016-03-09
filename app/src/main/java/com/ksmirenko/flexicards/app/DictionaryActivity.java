package com.ksmirenko.flexicards.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.widget.*;
import com.ksmirenko.flexicards.app.adapters.DictionaryCursorAdapter;
import com.ksmirenko.flexicards.app.datatypes.Category;

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

        // filling the main list view with categoryInfos
        long categoryId = getIntent().getLongExtra(ARG_CATEGORY_ID, 0);
        final Cursor cursor = DatabaseManager.INSTANCE.getDictionary(categoryId);
        final DictionaryCursorAdapter adapter = new DictionaryCursorAdapter(this, cursor);
        final ListView listView = (ListView) findViewById(R.id.listview_dictionary);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // FIXME: maybe we should reuse the window?
                // preparing view for popup window
                final Context context = view.getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View cardDetailView = inflater.inflate(R.layout.fragment_dict_card_detail, null); // NB: listView??
                final TextView tvFront = (TextView) cardDetailView.findViewById(R.id.textview_card_detail_front);
                final TextView tvBack = (TextView) cardDetailView.findViewById(R.id.textview_card_detail_back);
                // setting textviews to be scrollable
                tvFront.setMovementMethod(new ScrollingMovementMethod());
                tvBack.setMovementMethod(new ScrollingMovementMethod());
                // fetching content from cursor and filling textviews
                cursor.moveToPosition(position);
                tvFront.setText(cursor.getString(DatabaseManager.CardQuery.Companion.getCOLUMN_INDEX_FRONT()));
                tvBack.setText(cursor.getString(DatabaseManager.CardQuery.Companion.getCOLUMN_INDEX_BACK()));
                // preparing and showing popup window
                final PopupWindow cardDetailWindow = new PopupWindow(
                        cardDetailView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                cardDetailWindow.setOutsideTouchable(true);
                cardDetailWindow.setTouchable(true);
                cardDetailWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                cardDetailWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        cardDetailWindow.dismiss();
                        // FIXME: another window immediately opens when touching another list item outside this window
                        return true;
                    }
                });
                cardDetailWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);
            }
        });
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
}
