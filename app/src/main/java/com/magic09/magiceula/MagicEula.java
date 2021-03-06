package com.magic09.magiceula;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * MagicEula provides a simple End User Licence Agreement (EULA) presented
 * in a dialog with Accept and Reject buttons.
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

        // Check we have all the information required to show the EULA
		if (appName.equals("") || appVersion.equals("") || eulaMessage.equals("")) {
			Log.e(TAG, "App name, version or EULA message not set before calling showEula()?");
            throw new IllegalArgumentException("App name, version or EULA message not set before calling showEula()?");
		}

        // Check if the EULA has already been accepted
		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		if (!myPrefs.getBoolean(EULA_ACCEPTED_KEY, false)) {

			// Display EULA dialog fragment
            MagicEulaDialogFragment dialogFragment = new MagicEulaDialogFragment();
            Bundle fragBundle = new Bundle();
            fragBundle.putString(MagicEulaDialogFragment.TITLE, appName);
            fragBundle.putString(MagicEulaDialogFragment.VERSION, appVersion);
            fragBundle.putString(MagicEulaDialogFragment.MESSAGE, eulaMessage);
            dialogFragment.setArguments(fragBundle);
            dialogFragment.show(mActivity.getFragmentManager(), "MAGICEULA");
		}
	}
	
	/**
	 * Method sets the app name to display.
	 * @param name The app name to display.
	 */
	public void setAppName(String name) {
		appName = name;
	}
	
	/**
	 * Method sets the version information to display.
	 * @param version The version information to display
	 */
	public void setAppVersion(String version) {
		appVersion = version;
	}
	
	/**
	 *  Method sets the EULA message to display using the argument message.
	 * @param message The EULA message to display.
	 */
	public void setMessage(String message) {
		eulaMessage = message;
	}
	
	/**
	 * Method sets the EULA notes using the passed text file resource
	 * in the argument resId.  Requires context.
	 * @param context Context used to access resources.
	 * @param resId The id of the html text file from which to get the EULA message to display.
	 */
	public void setMessageFromTextFile(Context context, int resId) {
		eulaMessage = readEulaFromRawHtmlTextFile(context, resId);
	}
	
	/**
	 * Method returns true if the user has already accepted
	 * the EULA otherwise false.
	 * @return The boolean representing if the EULA has been previously accepted.
	 */
	public boolean getEulaAccepted() {
		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
		return myPrefs.getBoolean(EULA_ACCEPTED_KEY, false);
	}
	
	/**
	 * Method reads the text file (raw) resource specified in the argument
	 * resId and returns a String with its contents.
	 * @param context Context used to access resources.
	 * @param resId The id of the html text file from which to get the EULA message to display.
	 * @return The text retrieved from the html text file resource.
	 */
	private String readEulaFromRawHtmlTextFile(Context context, int resId) {
		String result = null;
		
		try {
			InputStream inputStream = context.getResources().openRawResource(resId);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
		    BufferedReader buffreader =
		    		new BufferedReader(inputreader);
		    String line;
		    StringBuilder text = new StringBuilder();
		    try {
		        while ((line = buffreader.readLine()) != null) {
		        	if (text.length() > 0)
			            text.append('\n');
		            text.append(line);
		        }
		        result = text.toString();
		    } catch (IOException e) {
		        result = null;
		    }
		} catch (NotFoundException e) {
			Log.e(TAG, "Cannot find text file resource to display!");
			e.printStackTrace();
		}
		
		// Ensure we display any HTML encoded characters correctly
		result = Html.fromHtml(result).toString();
	    return result;
	}

}
