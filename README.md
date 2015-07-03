MagicEula
=========
An Android library project that provides a simple way of presenting an EULA (End User Licence Agreement) on first run of an application.  This project was developed in [Android Studio](http://developer.android.com/tools/studio/).

Adding MagicEula to your project
--------------------------------
**1. Gradle dependency (Android Studio)**

 - 	Add the following to your `build.gradle`:
 ```gradle
repositories {
	    maven { url "https://jitpack.io" }
}

dependencies {
	    compile 'com.github.dream09:MagicEula:v2.2'
}
```

**2. Maven**
- Add the following to your `pom.xml`:
 ```xml
<repository>
       	<id>jitpack.io</id>
	    <url>https://jitpack.io</url>
</repository>

<dependency>
	    <groupId>com.github.dream09</groupId>
	    <artifactId>MagicEula</artifactId>
	    <version>v2.2</version>
</dependency>
```

**3. Jar file only**
 - Get the [**latest release .jar file**](https://github.com/dream09/MagicEula/releases) from the releases area
 - Copy the **MagicEula-X.X.jar** file into the `libs` folder of your Android project
 - Start using the library

Using MagicEula
---------------
* Ensure you have imported the project as described above and that it is set as a library project.

* Setup in the *onCreate* method of your main activity, for example:
```javascript
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
