package com.magic09.magiceula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * MagicEula provides a simple End User Licence Agreement (EULA) presented
 * in a dialog with Accept and Reject buttons.  It is only shown on first
 * install and when app has been updated.
 * @author dream09
 *
 */
public class MagicEula {

	static final String TAG = "MagicEula";
	public static final String EULA_ACCEPTED_KEY = "com.magic09.magiceula.accepted";
	
	/* Variables */
	private Activity mActivity;
	private String appName;
	private String appVersion;
	private String eulaMessage;
	
	
	
	/**
	 * Constructor
	 */
	public MagicEula(Activity context) {
		mActivity = context;
		appName = "";
		appVersion = "";
		eulaMessage = "";
	}
	
	
	
	/* Methods */
	
	/**
	 * Method uses shared preferences to record if the EULA has been
	 * accepted so that it is only shown once to the user.  If rejected,
	 * the app is closed.
	 */
	public void showEula() {
		
		if (appName == "" || appVersion == "" || eulaMessage == "") {
			Log.d(TAG, "App name, version or EULA message not passed?");
			return;
		}
		
		final SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		if (!myPrefs.getBoolean(EULA_ACCEPTED_KEY, false)) {
			
			// Inflate and setup the view.
			LayoutInflater inflater = (LayoutInflater) mActivity.getLayoutInflater();
			View eulaView = inflater.inflate(R.layout.eula_view, null);
			TextView eulaAppTitle = (TextView) eulaView.findViewById(R.id.eula_app_title);
			eulaAppTitle.setText(appName);
			TextView eulaAppVersion = (TextView) eulaView.findViewById(R.id.eula_app_version);
			eulaAppVersion.setText(mActivity.getString(R.string.version_prefix) + " " + appVersion);
			TextView eulaText = (TextView) eulaView.findViewById(R.id.eula_message);
			eulaText.setText(eulaMessage);
			
			// Build the alert and show.
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
    		builder.setView(eulaView)
    			.setPositiveButton(mActivity.getString(R.string.button_accept), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences.Editor editor = myPrefs.edit();
						editor.putBoolean(EULA_ACCEPTED_KEY, true).commit();
						dialog.dismiss();
					}
				})
    			.setNegativeButton(mActivity.getString(R.string.button_reject), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mActivity.finish();
					}
				})
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						mActivity.finish();
					}
				});
    		
    		AlertDialog dialog = builder.create();
    		dialog.show();
		}
	}
	
	/**
	 * Method sets the app name to display.
	 * @param name
	 */
	public void setAppName(String name) {
		appName = name;
	}
	
	/**
	 * Method sets the version information to display.
	 * @param version
	 */
	public void setAppVersion(String version) {
		appVersion = version;
	}
	
	/**
	 *  Method sets the EULA message to display using the argument message.
	 * @param message
	 */
	public void setMessage(String message) {
		eulaMessage = message;
	}
	
	/**
	 * Method sets the EULA notes using the passed text file resource
	 * in the argument resId.  Requires context.
	 * @param context
	 * @param resId
	 */
	public void setMessageFromTextFile(Context context, int resId) {
		eulaMessage = readEulaFromRawTextFile(context, resId);
	}
	
	/**
	 * Method returns true if the user has already accepted
	 * the EULA otherwise false.
	 * @return
	 */
	public boolean getEulaAccepted() {
		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		return myPrefs.getBoolean(EULA_ACCEPTED_KEY, false);
	}
	
	/**
	 * Method reads the text file (raw) resource specified in the argument
	 * resId and returns a String with its contents.
	 * @param context
	 * @param resId
	 * @return
	 */
	private String readEulaFromRawTextFile(Context context, int resId) {
		String result = null;
		
		try {
			InputStream inputStream = context.getResources().openRawResource(resId);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
		    BufferedReader buffreader = new BufferedReader(inputreader);
		    String line;
		    StringBuilder text = new StringBuilder();
		    try {
		        while (( line = buffreader.readLine()) != null) {
		            text.append(line);
		            text.append('\n');
		        }
		        result = text.toString();
		    } catch (IOException e) {
		        result = null;
		    }
		} catch (NotFoundException e) {
			Log.e(TAG, "Cannot find text file resourse to display!");
			e.printStackTrace();
		}
		
		// Ensure we display any HTML encoded characters correctly
		result = Html.fromHtml(result).toString();
	    return result;
	}
	
}
