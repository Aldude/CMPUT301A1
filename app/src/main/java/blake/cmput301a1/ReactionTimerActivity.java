package blake.cmput301a1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ReactionTimerActivity extends AppCompatActivity {

    private Reactor reactor;
    private Prompter prompter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_timer);
        reactor = new Reactor((Button) findViewById(R.id.button5), this);
        prompter = new Prompter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        prompter.reactionTimerInfoPrompt(reactor);
    }

    public void reactionSend(View view) {
        reactor.react();
    }
}
