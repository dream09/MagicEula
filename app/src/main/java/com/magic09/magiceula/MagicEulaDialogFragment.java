package com.magic09.magiceula;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by magic09 on 04/07/15.
 */
public class MagicEulaDialogFragment extends DialogFragment {

    /* Variables */
    public static final String TITLE = "title";
    public static final String VERSION = "version";
    public static final String MESSAGE = "message";



    /* Interfaces */

    /**
     * Interface to return selection.
     * @author dream09
     *
     */
    public interface EulaDialogListener {
        public void onEulaResult(Boolean result);
    }



    /* Constructor */
    public MagicEulaDialogFragment() {
        // Empty constructor required for a DialogFragment
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof EulaDialogListener)) {
            throw new ClassCastException(activity.toString() + " must implement EulaDialogListener!");
        }
    }

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
                        sendResult(true);
                        dismiss();
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.button_reject), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(false);
                        dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        sendResult(false);
                        dismiss();
                    }
                });

        return builder.create();
    }



    /* Methods */

    /**
     * Method uses EulaDialogListener interface to send the argument
     * result.
     * @param result
     */
    private void sendResult(boolean result) {
        EulaDialogListener activity = (EulaDialogListener) getActivity();
        activity.onEulaResult(result);
    }
}
