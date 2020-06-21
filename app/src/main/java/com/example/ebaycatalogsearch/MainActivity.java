package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner sortBySpinner = findViewById(R.id.sortBy);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.sortByOptions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(adapter);
        sortBySpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void search(View view) {
        // Check to make sure keyword field is NOT empty
        EditText keyword = (EditText) findViewById(R.id.keyword);
        String keywordString = keyword.getText().toString();

        if (keywordString == null || keywordString.length() == 0) {
            TextView keywordWarning = (TextView) findViewById(R.id.keywordsWarning);
            keywordWarning.setVisibility(View.VISIBLE);
        } else {
            TextView keywordWarning = (TextView) findViewById(R.id.keywordsWarning);
            keywordWarning.setVisibility(View.GONE);
        }

        // Check to make sure price ranges are valid
        EditText priceFrom = (EditText) findViewById(R.id.priceFrom);
        EditText priceTo = (EditText) findViewById(R.id.priceTo);

        System.out.println(priceFrom.getText().toString());

//        int priceFromValue = Integer.parseInt(priceFrom.getText().toString());
//        int priceToValue = Integer.parseInt(priceTo.getText().toString());
//
//        System.out.println(priceFromValue);
//        System.out.println(priceToValue);
    }
}