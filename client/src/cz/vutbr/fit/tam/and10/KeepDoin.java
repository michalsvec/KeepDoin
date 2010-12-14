package cz.vutbr.fit.tam.and10;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cz.vutbr.fit.tam.and10.KeepDoinApplication;
import cz.vutbr.fit.tam.and10.activities.AccountInfoHolder;
import cz.vutbr.fit.tam.and10.helpers.GameModel;
import cz.vutbr.fit.tam.and10.helpers.SQLDriver;

public class KeepDoin extends Activity implements AccountInfoHolder {
	
	public static final String PREFS_NAME = "KeepDoinPrefs";

    private final Handler mHandler = new Handler();
    private Thread mAuthThread;
	private String accountName;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAccountName(getGoogleAccount()); 
        if(this.accountName.equals(""))
        	setContentView(R.layout.missing_account);
        else
        	setContentView(R.layout.login_registration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registration_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.server:
        case R.id.secret:
            doSetup(item.getItemId());
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    private void doSetup(final int itemId) {
    	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);
		
		if (itemId == R.id.server)
			input.setText(getRemoteAPIUrl());
		else
			input.setText(getSecret());
		alert.setView(input);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString().trim();
				if (itemId == R.id.server)
					setRemoteAPIUrl(value);
				else
					setSecret(value);
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});

		alert.show();
    }
    
    public void handleLogin(View view) {
    	Log.i("KeepDoin", "handleLogin()");
    	
    	KeepDoinApplication global = (KeepDoinApplication) getApplication();
    	if (!global.isNetworkAvailable()) {
        	Context context = getApplicationContext();
        	Toast toast = Toast.makeText(context, getText(R.string.no_internet_connection), Toast.LENGTH_SHORT);
        	toast.show();
    	} else {
    		showDialog(0);
    		mAuthThread = GameModel.attemptAuth(this.accountName, mHandler, KeepDoin.this);
    	}
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.ui_activity_authenticating));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Log.i("KeepGoing", "login dialog cancel has been invoked");
                if (mAuthThread != null) {
                    mAuthThread.interrupt();
                    finish();
                }
            }
        });
        return dialog;
    }

    public void onAuthenticationResult(boolean result) {
        Log.i("KeepDoin", "onAuthenticationResult(" + result + ")");

        dismissDialog(0);

        if (result) {
        	Log.e("KeepDoin", "authentication successful!");
        	finishAuth();
        } else {
        	Log.e("KeepDoin", "authentication failed!");
        	TextView view = (TextView) findViewById(R.id.notices);
        	view.setText(getText(R.string.login_activity_loginfail));
        }
    }

    protected void finishAuth() {
        Log.i("KeepDoin", "finishAuth()");

        // database check, one two, one two, check check!!!
        try {
			SQLDriver db = new SQLDriver(this);
			db.checkSchema();
			db.closeDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        startApp();
    }

	public void startApp() {
        Intent intent = new Intent(KeepDoin.this, MainWindow.class);

        Log.i("KeepDoin", "starting activity MainWindow");
        startActivity(intent);
	}
	
	public void closeApp(View view) {
		finish();
	}

	private String getGoogleAccount() {
        Account[] accts = AccountManager.get(this).getAccounts(); 
        for(int i = 0; i < accts.length; i++) {
        	Log.d("KeepDoin", accts[i].type+"-/-"+accts[i].name);
        	if(accts[i].type.equals("com.google")) {
        		Log.i("KeepDoin", "account found: "+accts[i].name);
        		return accts[i].name;
        	}
        }
        return "";
    }

	@Override
	public int getAccountId() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getInt("accountId", 0);
	}

	@Override
	public void setAccountId(int id) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.putInt("accountId", id);
		editor.commit();
		
		KeepDoinApplication app = (KeepDoinApplication) getApplication();
		app.accountId = id;
	}

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getRemoteAPIUrl() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getString("remoteAPIUrl", "http://todogame.michalsvec.cz/api/");
	}
	
	public void setRemoteAPIUrl(String server) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.putString("remoteAPIUrl", server);
		editor.commit();
	}

	public String getSecret() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getString("secret", "");
	}
	
	public void setSecret(String keyString) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.putString("secret", keyString);
		editor.commit();
	}
	
	public Boolean getRegistred() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean("registred", false);
	}
	
	public void setRegistred(Boolean value) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.putBoolean("registred", value);
		editor.commit();
	}
}