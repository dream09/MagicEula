MagicEula
=========
An Android library project that provides a simple way of presenting an EULA (End User Licence Agreement) on first run of an application.  This project was developed in [Eclipse](http://www.eclipse.org/downloads/) using the [Android ADT plugin for Eclipse](http://developer.android.com/sdk/installing/installing-adt.html) using JDK 7.


Cloning MagicEULA source from GitHub
------------------------------------
Follow these steps to clone the source if you would like to use this project and/or contribute.

1. Switch to the *Git Repository Exploring* perspective in Eclipse.
2. Copy the URI for this project https://github.com/dream09/MagicEula.git.
3. Click *Clone a Git Repository* and paste the URI from step 2.
4. The *Host* and *Repository* path fields should populate automatically. Click *Next >*.
5. If you wish to use the latest stable version as a library for another project ensure the *master* branch is checked. If you wish to contribute to MagicEula ensure the *develop* branch is checked.
6. Make any changes you wish in the **Local Destination** dialogue (remember - short paths close to root and without spaces are recommended), and click *Finish*.
7. Wait for the repository to be cloned.
8. If you would like to use the latest stable version check out the latest tag, for example v1.0, by:
	- Right-click the repository and select *Switch To → Other...*
	- Select Tags → v1.0 (or whatever is the latest)
	- Click *Checkout*
9. Right-click the repository and select *Import Projects...*.
10. Select the *Use the New Project wizard* option and click *Finish*.
11. Select *Android Project from Existing Code* under the *Android* folder and click *Next >*.
12. Click *Browse* and locate the project directory you cloned to in step 6 then click *Finish*.
13. Switch to the *Java* perspective.
14. Right-click the MagicEula project and click *Properties*.
15. Under *Android* options check the *Is Library* check box and click *OK*.


Using MagicEula
---------------
* Ensure you have imported the project as described above and that it is set as a library project.

* Setup in the *onCreate* method of your main activity, for example:
```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

	// Check if EULA should be displayed.
    MagicEula myEula = new MagicEula(this);
    if (!myEula.getEulaAccepted()) {
    	
    	// Get our version info ready for passing to the EULA.
        String version = "";
        try {
        	PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
        	version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        
        // Setup and show EULA.
        myEula.setAppName(getString(R.string.app_name));
        myEula.setAppVersion(version);
        myEula.setMessage(getString(R.string.app_eula));
        myEula.showEula();
    }
}
```


Contributing to MagicEula
---------------------------------

If you wish to contribute please create a feature branch from the *develop* branch and name *feature-yourfeature*.
