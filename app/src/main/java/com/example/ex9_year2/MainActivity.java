package com.example.ex9_year2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    /**
     *      @author ziv ankri address: za6200@bs.amalnet.k12.il
     *      * @version	    2022.3.1
     *      * @since       23/11/2023
     *      class will make a file and read from it or write from the edit text
     *      depends on the button clicked save - save the data and add to the text
     *      view reset - reset the file and the all the fields
     *      exit do the same as save and finish the program
     */

    private static final String FILENAME = "test.txt";
    private static final int REQUEST_CODE_PERMISSION = 1;

    TextView textView;
    EditText editText;
    Button save;
    Button reset;
    Button exit;
    Intent credits;
    String strW = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        save = findViewById(R.id.save);
        reset = findViewById(R.id.reset);
        exit = findViewById(R.id.exit);
        credits = new Intent(this, credits.class);

        if (checkPermission()) {
            Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }

        try {
            // Read content from the file on startup
            textView.setText(readFromFile());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Problems while using the file", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
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
            try {
                startActivity(credits);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void reset(View view) {
        /**
         * function will reset all fields and reset the file content
         * param view: when button clicked
         */

        strW = "";
        textView.setText("");
        writeToFile("");
        editText.setText("");
    }

    public void save(View view) {
        /**
         * function will save what's in the edit text and add it to the external file
         * param view: when button clicked
         */

        try {
            strW = readFromFile();
            strW += editText.getText().toString();
            writeToFile(strW);
            textView.setText(strW);
        } catch (Exception e) {
            Toast.makeText(this, "Problems while using the file", Toast.LENGTH_SHORT).show();
        }
    }

    public void exit(View view) {
        /**
         * function will act like save and finish program
         * param view: when button clicked
         */
        // Save data and exit
        save(view);
        finish();
    }

    private void writeToFile(String content) {
        /*
        function will write to the external file
         */
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file, false);
            writer.write(content);

            writer.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error reading the file", Toast.LENGTH_SHORT).show();
        }
    }

    private String readFromFile() {
        /*
        function will read from the external file
         */
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);

            if (!file.exists()) {
                return "";
            }

            // Read content from the file
            StringBuilder sB = new StringBuilder();
            BufferedReader bR = new BufferedReader(new FileReader(file));
            String line = bR.readLine();
            while (line != null) {
                sB.append(line);
                line = bR.readLine();
            }
            return sB.toString();

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error reading the file", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}
