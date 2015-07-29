package com.magic09.magiceula;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * MagicEulaDialogFragment provides a DialogFragment to display the
 * EULA with accept and reject buttons.
 * @author dream09
 */
public class MagicEulaDialogFragment extends DialogFragment {

    /* Variables */
    public static final String TITLE = "title";
    public static final String VERSION = "version";
    public static final String MESSAGE = "message";



    /* Constructor */
    public MagicEulaDialogFragment() {
        // Empty constructor required for a DialogFragment
    }



    /* Overridden methods */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get arguments
        String title = getArguments().getString(TITLE);
        String version = getArguments().getString(VERSION);
        String message = getArguments().getString(MESSAGE);

        // Setup view
        View eulaView = inflater.inflate(R.layout.eula_view, null);
        TextView eulaAppTitle = (TextView) eulaView.findViewById(R.id.eula_app_title);
        if (title != null && eulaAppTitle != null) {
            eulaAppTitle.setText(title);
        }
        TextView eulaAppVersion = (TextView) eulaView.findViewById(R.id.eula_app_version);
        if (version != null && eulaAppVersion != null) {
            eulaAppVersion.setText(getActivity().getString(R.string.version_prefix) + " " + version);
        }
        TextView eulaText = (TextView) eulaView.findViewById(R.id.eula_message);
        if (message != null && eulaText != null) {
            eulaText.setText(message);
        }

        return eulaView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getActivity().getString(R.string.button_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkResult(true);
                        dismiss();
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.button_reject), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkResult(false);
                        dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        checkResult(false);
                        dismiss();
                    }
                });

        return builder.create();
    }



    /* Methods */

    /**
     * Method reacts to the argument result.  If true stores that the EULA
     * was accepted else finishes the parent activity.
     * @param result True if accepted, false if rejected.
     */
    private void checkResult(boolean result) {
        if (result) {
            // EULA accepted so store in shared preferences
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                    .putBoolean(MagicEula.EULA_ACCEPTED_KEY, true).commit();
        } else {
            // EULA rejected close activity
            getActivity().finish();
        }
    }
}
