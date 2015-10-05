package blake.cmput301a1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    public void launchGameshowBuzzer(View view) {
        startActivity(new Intent(this, ReactionTimerActivity.class));
    }

    public void launchReactionTimer(View view) {
        startActivity(new Intent(this, ReactionTimerActivity.class));
    }

    public void launchStatisticsView(View view) {
        startActivity(new Intent(this, StatsViewActivity.class));
    }
}
