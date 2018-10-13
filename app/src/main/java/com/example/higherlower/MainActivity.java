package com.example.higherlower;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random();
    private ImageView mImageDice;
    private ListView mThrowListView;
    private List<Throw> throwList;
    private ArrayAdapter throwListAdapter;
    private FloatingActionButton mHigherButton;
    private FloatingActionButton mLowerButton;

    private TextView scoreView;
    private TextView highscoreView;

    private int previousNumber = 3;
    private int currentNumber;
    private int currentScore = 0;
    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *      Binding everything.
         */

        scoreView = findViewById(R.id.scoreText);
        highscoreView = findViewById(R.id.highscoreText);
        mImageDice = findViewById(R.id.diceImageView);
        mThrowListView = findViewById(R.id.throwListView);
        mHigherButton = findViewById(R.id.higherButton);
        mLowerButton = findViewById(R.id.lowerButton);

        /**
         *      Make in adapter in MainActivity
         */
        throwList = new ArrayList<>();
        throwListAdapter = new ArrayAdapter(this, R.layout.throwlist, R.id.throwlist, throwList);
        mThrowListView.setAdapter(throwListAdapter);

        /**
         *      This is the "Onclick" that is used for the HigherButton.
         */
        mHigherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooses a random number from the method "roll"
                roll();
                //checks the score and in this check the true stands for higher.
                checkScore(v, true);
            }
        });

        /**
         *      This is the "Onclick" that is used for the LowerButton.
         */
        mLowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooses a random number from the method "roll"
                roll();
                //checks the score and in this check the true stands for lower.
                checkScore(v, false);
            }
        });


    }

    /**
     * This method generates a random number between 1 to 6.
     * When the number has been generated it puts it in the list.
     * With every number there is given another image.
     */
    private void roll() {
        Throw diceNumber = new Throw();
        diceNumber.setThrowNumber(random.nextInt(6) + 1);
        currentNumber = diceNumber.getThrowNumber();

        throwList.add(diceNumber);
        throwListAdapter.notifyDataSetChanged();

        switch (diceNumber.getThrowNumber()) {
            case 1:
                mImageDice.setImageResource(R.drawable.d1);
                break;
            case 2:
                mImageDice.setImageResource(R.drawable.d2);
                break;
            case 3:
                mImageDice.setImageResource(R.drawable.d3);
                break;
            case 4:
                mImageDice.setImageResource(R.drawable.d4);
                break;
            case 5:
                mImageDice.setImageResource(R.drawable.d5);
                break;
            case 6:
                mImageDice.setImageResource(R.drawable.d6);
                break;
        }
    }

    /**
     * This method is an if and else clause where the check is made if the user has won or not.
     * Besides that it changes also the highscore and currentscore on the screen.
     */
    private void checkScore(View v, boolean choice) {

        if ((choice && currentNumber > previousNumber) || ((!choice) && currentNumber < previousNumber)) {
            currentScore++;
            if (currentScore > highscore) {
                highscore = currentScore;
                highscoreView.setText("Highscore: " + highscore);
            }
            Snackbar.make(v, "You Win!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (currentNumber == previousNumber) {
            Snackbar.make(v, "It's a Draw", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            currentScore = 0;
            Snackbar.make(v, "You Lose!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        previousNumber = currentNumber;
        scoreView.setText("Score: " + currentScore);
    }
}
