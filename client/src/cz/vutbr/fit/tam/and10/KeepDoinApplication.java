package cz.vutbr.fit.tam.and10;

import android.app.Application;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Global class which contains data, we want to share across the application.
 * 
 * getting in any activity: KeepDoinApplication global = (KeepDoinApplication) getApplication();
 * @author misa
 *
 */
public class KeepDoinApplication extends Application {

	public String accountName;
	public int accountId;
	/**
	 * Activity manager for restarting tabs and activities
	 * created in MainWindow.class
	 *
	 * Usage:
	 * manager.destroyActivity("tab3", true);
	 * manager.startActivity("tab3", new Intent(this, ThirdTab.class));
	 * manager.getActivity("tab3");
	 */
	public LocalActivityManager manager;
	
	// detection if the internet connection is active
	public boolean isNetworkAvailable()
	{
	    boolean HaveConnectedWifi = false;
	    boolean HaveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                HaveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                HaveConnectedMobile = true;
	    }
	    return HaveConnectedWifi || HaveConnectedMobile;
	}
}
