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
import android.widget.TextView;


/**
 * MagicEulaDialogFragment provides a DialogFragment to display the
 * EULA with accept and reject buttons.
 * @author dream09
 *
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Prevent EULA being cancelled
        setCancelable(false);

        // Get arguments
        String title = getArguments().getString(TITLE);
        String version = getArguments().getString(VERSION);
        String message = getArguments().getString(MESSAGE);

        // Setup view
        LayoutInflater inflater = getActivity().getLayoutInflater();
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

        // Create EULA dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(eulaView)
                .setPositiveButton(getActivity().getString(R.string.button_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // EULA accepted so store in shared preferences
                        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                                .putBoolean(MagicEula.EULA_ACCEPTED_KEY, true).commit();
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.button_reject), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // EULA rejected close activity
                        getActivity().finish();

                    }
                });

        return builder.create();
    }

}
