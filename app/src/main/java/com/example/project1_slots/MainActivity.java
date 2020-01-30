package com.example.project1_slots;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project1_slots.CONSTANTS;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Balsamo";

    // Dealing with Money
    TextView wallet;
    int startupCash = CONSTANTS.STARTUP_CASH;
    int money = startupCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start up Money
        wallet = (TextView)findViewById(R.id.money);
        wallet.setText(String.format("$%s", Integer.toString(startupCash)));
        Log.d(TAG, "Money you have on startUp should = 5: " + Integer.toString(money)); //debug statement
    }

    public void goButt(View view) {
    }

    public void resetButt(View view) {
    }
}
