package tk.sefodopo.countdownwatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TimeUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Joshua on 7/28/2016.
 */
public class ActionAddFragment extends DialogFragment {

    public interface ActionAddListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    ActionAddListener mlistener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.action_add_fragment_title)
                .setView(getActivity().getLayoutInflater().inflate(R.layout.action_add_fragment, null))
                .setPositiveButton(R.string.action_add_fragment_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mlistener.onDialogPositiveClick(ActionAddFragment.this);
                    }
                })
                .setNegativeButton(R.string.action_add_fragment_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActionAddFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        try {
            mlistener = (ActionAddListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActionAddListener");
        }
    }
}
