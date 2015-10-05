package blake.cmput301a1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Prompter {
    private Activity caller;
    private DialogFragment infoFragment;

    public Prompter(Activity whoCalled) {
        caller = whoCalled;
    }

    /* Help on opening a dialeg form http://developer.android.com/guide/topics/ui/dialogs.html#DialogFragment */
    public void reactionTimerInfoPrompt(final Reactor reactor) {
        infoFragment = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.reaction_timer_how_to);
                builder.setNeutralButton("OK", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        reactor.startReactor();
                    }
                });

                return builder.create();
            }
        };

        infoFragment.show(caller.getFragmentManager(), "Info");
    }
}
