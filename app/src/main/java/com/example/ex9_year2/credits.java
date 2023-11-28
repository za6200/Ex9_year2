package com.example.ex9_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class credits extends AppCompatActivity {
    /**
     * @author ziv ankri address: za6200@bs.amalnet.k12.il
     * @version 2022.3.1
     * @since 14/11/2023
     * class will credit my parents
     */
    TextView textView;
    Intent backActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        textView = findViewById(R.id.textView2); // Initialize the textView
        textView.setText("thanks to my parents for getting me to who I am :)");
        backActivity = new Intent(this, MainActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("credit")) {

        }
        else if(st.equals("mainActivity"))
        {
            startActivity(backActivity);
        }
        return true;
    }
}