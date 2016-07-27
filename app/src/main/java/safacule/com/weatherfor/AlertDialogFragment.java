package safacule.com.weatherfor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Debde_000 on 24-07-2016.
 */
public class AlertDialogFragment extends DialogFragment {
    public static final String TITLE_ID = "xxx";
    public static final String MESSAGE_ID = "yyy";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(R.string.error_ok_button_text, null);

        if (bundle != null) {
            builder.setTitle(bundle.getString(TITLE_ID, "Regrets!"))        // second argument is default message in
                                                                            // case key entered "TITLE_ID/MESSAGE_ID" is wrong
                    .setMessage(bundle.getString(MESSAGE_ID, "If this occurs, please report to developer!"));
        } else {
            // setting values randomly if bundle has no value
            builder.setTitle("Sorry!")
                    .setMessage("Services are down lately!");
        }

//        AlertDialog dialog = builder.create();
        return  builder.create();
    }
}
