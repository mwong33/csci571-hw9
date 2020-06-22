package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Form Fields
    private EditText keyword;
    private EditText priceFrom;
    private EditText priceTo;
    private CheckBox conditionNew;
    private CheckBox conditionUsed;
    private CheckBox conditionUnspecified;
    private Spinner sortBy;

    // Warnings
    private TextView keywordWarning;
    private TextView priceRangeWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Form fields
        keyword = findViewById(R.id.keyword);
        priceFrom = findViewById(R.id.priceFrom);
        priceTo = findViewById(R.id.priceTo);
        conditionNew = findViewById(R.id.conditionNew);
        conditionUsed = findViewById(R.id.conditionUsed);
        conditionUnspecified = findViewById(R.id.conditionUnspecified);
        sortBy = findViewById(R.id.sortBy);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.sortByOptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy.setAdapter(adapter);
        sortBy.setOnItemSelectedListener(this);

        // Initialize the warnings
        keywordWarning = findViewById(R.id.keywordsWarning);
        priceRangeWarning = findViewById(R.id.priceRangeWarning);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void search(View view) {

        // boolean to check if any fields are invalid
        boolean invalidFields = false;

        // Check to make sure keyword field is NOT empty
        String keywordString = keyword.getText().toString();

        if (keywordString == null || keywordString.length() == 0) {
            keywordWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else {
            keywordWarning.setVisibility(View.GONE);
        }

        // Check to make sure price ranges are valid
        String priceFromString = priceFrom.getText().toString();
        String priceToString = priceTo.getText().toString();

        if (!priceFromString.equals("") && Integer.parseInt(priceFromString) < 0) {
            priceRangeWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else if (!priceToString.equals("") && Integer.parseInt(priceToString) < 0) {
            priceRangeWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else if ((!priceFromString.equals("") && !priceToString.equals("")) &&
                (Integer.parseInt(priceFromString) > Integer.parseInt(priceToString))) {
            priceRangeWarning.setVisibility(View.VISIBLE);
            invalidFields = true;
        } else {
            priceRangeWarning.setVisibility(View.GONE);
        }

        if (invalidFields) {
            Toast.makeText(MainActivity.this, "Please fix all fields with errors",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View view) {

        // Remove ALL warnings
        keywordWarning.setVisibility(View.GONE);
        priceRangeWarning.setVisibility(View.GONE);

        // Clear/Reset ALL fields
        keyword.setText("");

        priceFrom.setText("");
        priceTo.setText("");

        conditionNew.setChecked(false);
        conditionUsed.setChecked(false);
        conditionUnspecified.setChecked(false);

        sortBy.setSelection(0);

    }
}