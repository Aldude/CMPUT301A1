package blake.cmput301a1;

import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Reactor {
    private long startTime;
    private Button timerButton;
    private Activity owner;
    private Random random;
    private Logger logger;

    public Reactor(Button button, Activity caller) {
        timerButton = button;
        owner = caller;
        random = new Random(System.currentTimeMillis()); //Seeded for extra randomness!
        logger = new Logger(caller);
    }

    public void startReactor() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flipButton();
            }
        }, getDelay());
    }

    private void newTimer() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flipButton();
            }
        }, getDelay() + 1500); // 1500ms (1.5s) is added to allow the user time to read their result
    }

    public void flipButton() {
        startTime = System.currentTimeMillis();

        /* runOnUiThread solution from Amatthewrobbinsdev on:
           http://stackoverflow.com/questions/18656813/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
         */
        owner.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timerButton.setText(R.string.reaction_timer_go);
            }
        });
    }

    public void react() {
        // Game behaviour relies on button text. May be a bad idea...
        if(timerButton.getText().toString().compareTo(owner.getString(R.string.reaction_timer_wait)) == 0) {
            Toast displayResult = Toast.makeText(owner, "Too early!", Toast.LENGTH_SHORT);
            displayResult.show();
        } else {
            long reactionTime = System.currentTimeMillis() - startTime;
            Toast displayResult = Toast.makeText(owner, "You hit the button in " + reactionTime + "ms", Toast.LENGTH_SHORT);
            displayResult.show();

            owner.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timerButton.setText(R.string.reaction_timer_wait);
                }
            });

            newTimer();

            logger.addReaction(reactionTime);
        }
    }

    private long getDelay() {
        return ((Math.abs(random.nextLong()) % 1990) + 10);
    }
}
