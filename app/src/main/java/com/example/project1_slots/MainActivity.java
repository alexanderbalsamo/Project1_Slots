package com.example.project1_slots;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    private static final String TAG = "Balsamo";

    // Animation
    private Animation animRotate;

    // Dealing with Money
    TextView wallet;
    int startupCash = CONSTANTS.STARTUP_CASH;
    int money = startupCash;

    // Flowers
    List<ImageView> slots;
    List<Integer> flowers;

    // Buttons
    ImageButton goButt;
    ImageButton resetButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Buttons
        goButt = findViewById(R.id.goButt);
        resetButt = findViewById(R.id.resetButt);

        // Start up Money
        wallet = findViewById(R.id.money);
        wallet.setText(String.format("$%s", Integer.toString(startupCash)));
        Log.d(TAG, "Money you have on startUp should = 5: " + Integer.toString(money)); //debug statement

        // Grab ImageViews
        slots = new ArrayList<>();
        ImageView s1 = (ImageView)findViewById(R.id.slot1);
        ImageView s2 = (ImageView)findViewById(R.id.slot2);
        ImageView s3 = (ImageView)findViewById(R.id.slot3);
        slots.add(s1);
        slots.add(s2);
        slots.add(s3);

        // Grab Flowers
        flowers = new ArrayList<>();
        int f1 = R.drawable.f1;
        int f2 = R.drawable.f2;
        int f3 = R.drawable.f3;
        flowers.add(f1);
        flowers.add(f2);
        flowers.add(f3);

        // load animation
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        // set listener
        animRotate.setAnimationListener(this);
    }

    public void goButt(View view) {
        rotateFlower();
    }

    private void tempFlowers() {
        for(int i = 0; i < CONSTANTS.NUMB_FLOWERS; i++){
            ImageView slot = slots.get(i); // get each ImageViews
            slot.setImageResource(R.drawable.tmp); // Set each ImageView to be a tmp.png
        }
    }

    private void rotateFlower() {
        for(int i = 0; i < CONSTANTS.NUMB_FLOWERS; i++){
            ImageView slot = slots.get(i); // get each ImageViews
            slot.startAnimation(animRotate);
        }
    }

    private void showReset() {
        ImageButton resetButt = findViewById(R.id.resetButt); // get Reset Button ID
        resetButt.setVisibility(View.VISIBLE); // make button visible
    }

    private void chooseFlowers() {
        // Pull the slot machine
        ArrayList<Integer> flowersList = new ArrayList<>();
        for(int i = 0; i < CONSTANTS.NUMB_FLOWERS; i++) {
            Random rand = new Random();
            int randFlower = flowers.get(rand.nextInt(CONSTANTS.NUMB_FLOWERS)); // get a random flower
            ImageView slot = slots.get(i);
            slot.setImageResource(randFlower); // set the slot imageView to be a random flower
            flowersList.add(randFlower); //save list for comparisons
        }

        // Find the number of matching flowers
        int maxOccurances = 0;
        for(int j = 0; j < flowersList.size(); j++){
            int occurances = Collections.frequency(flowersList, flowersList.get(j));
            if(occurances > maxOccurances)
                maxOccurances = occurances;
        }

        // Record Score!
        if(maxOccurances == 1)
            money = money - CONSTANTS.COST_PER_ROLL;
        else if(maxOccurances == 2)
            money = money + CONSTANTS.MATCH_2;
        else
            money = money + CONSTANTS.MATCH_3;

        // Update your wallet
        wallet.setText(String.format("$%s", Integer.toString(money)));

        // Empty Wallet Condition
        if(money == CONSTANTS.YOUR_BROKE) {
            goButt.setEnabled(false);
            goButt.setVisibility(View.INVISIBLE);
        }
    }

    public void resetButt(View view) {
        money = startupCash; // reset money to $5
        wallet.setText(String.format("$%s", Integer.toString(money))); // reset wallet
        resetButt.setVisibility(View.INVISIBLE); // turn reset Button invisible
    }

    @Override
    public void onAnimationStart(Animation animation) {
        tempFlowers();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        showReset();
        chooseFlowers();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
