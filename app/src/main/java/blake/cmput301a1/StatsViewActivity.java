package blake.cmput301a1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StatsViewActivity extends AppCompatActivity {

    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger = new Logger(this);
        setUpReactionStats();
    }

    public void setUpReactionStats() {
        final TextView max_all_text = (TextView) findViewById(R.id.max_all_text);
        final TextView min_all_text = (TextView) findViewById(R.id.min_all_text);
        final TextView avg_all_text = (TextView) findViewById(R.id.avg_all_text);
        final TextView med_all_text = (TextView) findViewById(R.id.med_all_text);

        final TextView max_10_text = (TextView) findViewById(R.id.max_10_text);
        final TextView min_10_text = (TextView) findViewById(R.id.min_10_text);
        final TextView avg_10_text = (TextView) findViewById(R.id.avg_10_text);
        final TextView med_10_text = (TextView) findViewById(R.id.med_10_text);

        final TextView max_100_text = (TextView) findViewById(R.id.max_100_text);
        final TextView min_100_text = (TextView) findViewById(R.id.min_100_text);
        final TextView avg_100_text = (TextView) findViewById(R.id.avg_100_text);
        final TextView med_100_text = (TextView) findViewById(R.id.med_100_text);

        final long[] reactionTimes = logger.reactionStats();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                max_all_text.setText(Long.toString(reactionTimes[0]));
                min_all_text.setText(Long.toString(reactionTimes[1]));
                avg_all_text.setText(Long.toString(reactionTimes[2]));
                med_all_text.setText(Long.toString(reactionTimes[3]));

                max_10_text.setText(Long.toString(reactionTimes[4]));
                min_10_text.setText(Long.toString(reactionTimes[5]));
                avg_10_text.setText(Long.toString(reactionTimes[6]));
                med_10_text.setText(Long.toString(reactionTimes[7]));

                max_100_text.setText(Long.toString(reactionTimes[8]));
                min_100_text.setText(Long.toString(reactionTimes[9]));
                avg_100_text.setText(Long.toString(reactionTimes[10]));
                med_100_text.setText(Long.toString(reactionTimes[11]));
            }
        });
    }
}
