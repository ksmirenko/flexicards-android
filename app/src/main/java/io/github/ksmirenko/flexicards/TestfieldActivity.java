package io.github.ksmirenko.flexicards;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import io.github.ksmirenko.flexicards.datatypes.Card;
import io.github.ksmirenko.flexicards.datatypes.Category;

import java.util.List;

/**
 * Testfield for pieces of app layout and logic. Is used for development only and will not be included into release.
 *
 * @author Kirill Smirenko
 */
public class TestfieldActivity extends AppCompatActivity {

    private List<Category> categories;
    private Category testCategory;
    private Card testCard;
    private CategorySpinnerAdapter categorySpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfield);
        categories = StubDataGenerator.INSTANCE.getStubCategories();
        categorySpinnerAdapter = new CategorySpinnerAdapter(getBaseContext(), categories);
    }

    public void testEditCategory(View view) {
        // preparing category edit dialog
        final Context context = view.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View categoryEditView = inflater.inflate(R.layout.form_category_edit, null, false);
        final EditText editTextCategoryName = (EditText) categoryEditView.findViewById(R.id.edittext_category_name);
        final EditText editTextCategoryLang = (EditText) categoryEditView.findViewById(R.id.edittext_category_lang);
        // filling current category info, if editing existing one
        if (testCategory != null) {
            editTextCategoryName.setText(testCategory.getName(), TextView.BufferType.EDITABLE);
            editTextCategoryLang.setText(testCategory.getLanguage(), TextView.BufferType.EDITABLE);
        }
        // showing the dialog
        new AlertDialog.Builder(context)
                .setView(categoryEditView)
                .setTitle("Edit Category")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                // extracting input data
                                String name = editTextCategoryName.getText().toString();
                                String lang = editTextCategoryLang.getText().toString();
                                testCategory = new Category(0, name, lang);
                                // saving to SQL DB should be here
                            }
                        }).show();
    }

    public void testEditCard(View view) {
        // preparing card edit dialog
        final Context context = view.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View cardEditView = inflater.inflate(R.layout.form_card_add, null, false);
        final Spinner spinnerCardCategory = (Spinner) cardEditView.findViewById(R.id.spinner_card_category);
        spinnerCardCategory.setAdapter(categorySpinnerAdapter);
        final EditText editTextCardFront = (EditText) cardEditView.findViewById(R.id.edittext_card_front);
        final EditText editTextCardBack = (EditText) cardEditView.findViewById(R.id.edittext_card_back);
        // filling current card info, if editing existing one
        if (testCard != null) {
            editTextCardFront.setText(testCard.getFrontContent(), TextView.BufferType.EDITABLE);
            editTextCardBack.setText(testCard.getBackContent(), TextView.BufferType.EDITABLE);
            int spinnerPosition = categorySpinnerAdapter.getCategoryPosition(testCard.getCategoryId());
            spinnerCardCategory.setSelection(spinnerPosition);
        }
        // showing the dialog
        new AlertDialog.Builder(context)
                .setView(cardEditView)
                .setTitle("Edit Card")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                // extracting input data
                                int categoryId = ((Category) spinnerCardCategory.getSelectedItem()).getId();
                                String front = editTextCardFront.getText().toString();
                                String back = editTextCardBack.getText().toString();
                                testCard = new Card(0, categoryId, front, back);
                                // saving to SQL DB should be here
                            }
                        }).show();
    }


    private class CategorySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
        private Context context;
        private List<Category> categories;

        public CategorySpinnerAdapter(Context context, List<Category> categories) {
            this.context = context;
            this.categories = categories;
        }

        public int getCount() {
            return categories.size();
        }

        public Object getItem(int position) {
            return categories.get(position);
        }

        public long getItemId(int position) {
            return categories.get(position).getId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View spinView;
            if (convertView == null) {
                spinView = LayoutInflater.from(context).inflate(R.layout.spinner_item_categories, null);
            } else {
                spinView = convertView;
            }
            TextView tv = (TextView) spinView.findViewById(R.id.textview_spinner_cat_name);
            tv.setText(categories.get(position).getName());
            return spinView;
        }

        public int getCategoryPosition(int categoryId) {
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getId() == categoryId) {
                    return i;
                }
            }
            return 0;
        }
    }
}
