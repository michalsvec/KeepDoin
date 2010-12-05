package cz.vutbr.fit.tam.and10;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.activities.BaseActivity;
import cz.vutbr.fit.tam.and10.helpers.GameModel;

public class KeepDoin extends BaseActivity {
	
	public static final String PREFS_NAME = "KeepDoinPrefs";


    private TextView mMessage;

    private enum authTypes {AUTH_LOGIN, AUTH_REG};
    private authTypes authType; 
    private CharSequence dialogMsg;

    /** 
     * pomoci tohoto handleru se postuje zprava zpatky z vlakna do aktivity 
     * preda se environment, ktere reprezentuje tuto aktivitu a handler ma metodu post, kterou se 
     * vyvola metoda v teto aktivite, ktera zajisti dokonceni prihlasovani
     */
    private final Handler mHandler = new Handler();
    
    /**
     * Promenna vlakna, ktere zpracovava pozadavek na server
     */
    private Thread mAuthThread;



    private static String getGoogleAccount(AccountManager mgr) {
        String name = "";

        Account[] accts = mgr.getAccounts(); 

        for(int i=0; i<accts.length; i++) {
        	Log.d("KeepDoin", accts[i].type+"-/-"+accts[i].name);

        	// existuje google account
        	if(accts[i].type.equals("com.google")) {
        		Log.i("KeepDoin", "account found: "+accts[i].name);
        		name = accts[i].name;
        		break;
        	}
        }
        
       	return name;
    	
    }



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // kontrola, zda mame zadaneho uzivatele nekde v databazi
        // pokud ne, nabidne se mu registrace
        // pokud ano, spusti se normalne aplikace
        this.accountName = getGoogleAccount(AccountManager.get(this));
        
        
        

        // pokud neni v telefonu zadny ucet google,vyhodi hlasku a ukonci se 
        if(this.accountName.equals("")) {
        	setContentView(R.layout.missing_account);
        } 
        // kontrola, zda uz je uzivatel prihlaseny na nasem serveru - ta je ulozena
        // v SharedPreferences
        else {
        	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            // TODO: pro testovaci ucely vzdy vyskoci uvodni obrazovka - pak smazat
        	boolean logged = settings.getBoolean("userLogged", false);
        	//	boolean logged = false;
        	
            // uzivatel uz je prihlasen, normalne startujeme aplikaci
            if(logged) {
            	Log.i("KeepDoin", "user logged - starting app");
            	this.accountId = settings.getInt("accountId", 0);
            	
            	finishAuth();
            }
            // neprihlasen (prvni prhlaseni, nebo smazani preferences) 
            // nabidne se registrace, nebo login
            else {
            	Log.i("KeepDoin", "user NOT logged");
            	setContentView(R.layout.login_registration);
            }
        }
    }



    public void closeApp(View view) {
    	finish();
    }



    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * 
     * @param view The Submit button for which this method is invoked
     */
    public void handleLogin(View view) {
        Log.i("KeepDoin", "handleLogin()");
        
        // gets global class
        KDGlobal global = (KDGlobal) getApplication();
        if(!global.isNetworkAvailable()) {
        	Context context = getApplicationContext();
        	Toast toast = Toast.makeText(context, getText(R.string.no_internet_connection), Toast.LENGTH_SHORT);
        	toast.show();
        	return;
        }
        
        this.dialogMsg = getText(R.string.ui_activity_authenticating);
        authType = authTypes.AUTH_LOGIN;
        
        showProgress();
        Log.i("KeepDoin", "Starting thread!");

        // Start authenticating...
        mAuthThread = GameModel.attemptAuth(this.accountName, "login", mHandler, KeepDoin.this);
    }



    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * 
     * @param view The Submit button for which this method is invoked
     */
    public void handleRegistration(View view) {
        Log.i("KeepDoin", "handleRegistration()");
        
        // gets global class
        KDGlobal global = (KDGlobal) getApplication();
        if(!global.isNetworkAvailable()) {
        	Context context = getApplicationContext();
        	Toast toast = Toast.makeText(context, getText(R.string.no_internet_connection), Toast.LENGTH_SHORT);
        	toast.show();
        	return;
        }

        
        this.dialogMsg = getText(R.string.ui_activity_registering);
        authType = authTypes.AUTH_REG;
        
        showProgress();
        Log.i("KeepDoin", "Starting thread!");
            
        // Start authenticating...
        mAuthThread = GameModel.attemptAuth(this.accountName, "registration", mHandler, KeepDoin.this);

    }
    


    /**
     * Shows the progress UI for a lengthy operation.
     */
    protected void showProgress() {
        showDialog(0);
    }



    /**
     * pretizena metoda pro vytvareni dialogu - pokud se zavoila showDialog, tak se vola tato metoda
     * 
     * zobrazi lodovaci dialog
     */
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.dialogMsg);
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



    /**
     * Called when the authentication process completes (see attemptLogin()).
     */
    public void onAuthenticationResult(boolean result) {
        Log.i("KeepDoin", "onAuthenticationResult(" + result + ")");

        dismissDialog(0);

        if (authType == authTypes.AUTH_LOGIN) {
        	if(result) {
        		saveLogin();
        		finishAuth();
        	}
        	else {
        		Log.e("KeepDoin", "onAuthenticationResult: AUTH_LOGIN fail");
                mMessage = (TextView) findViewById(R.id.notices);
                mMessage.setText(getText(R.string.login_activity_loginfail));
        	}
        }
        else if (authType == authTypes.AUTH_REG) {
        	if(result) {
        		saveLogin();
        		finishAuth();
        	}
        	else {
        		Log.e("KeepDoin", "onAuthenticationResult: AUTH_REG fail");
                mMessage = (TextView) findViewById(R.id.notices);
                mMessage.setText(getText(R.string.login_activity_regfail));
        	}
        
        } else {
        	Log.e("KeepDoin", "onAuthenticationResult: UNKNOWN ERROR");
            mMessage = (TextView) findViewById(R.id.notices);
            mMessage.setText(getText(R.string.unknown_error));
        }
    }

    
    
    private void saveLogin() {
    	// saves local preferences
    	// permanent login 
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("userLogged", true);
        editor.putInt("accountId", accountId);
        editor.commit();	// need to commit changes before it ends
        
    }
    


    /**
     * 
     * po uspesnem prihlaseni/registraci se zobrazi timto obrazovka(aktivita) s aplikaci
     * 
     * @param the confirmCredentials result.
     */
    protected void finishAuth() {
        Log.i("KeepDoin", "finishAuth()");

        // stores account informations to global storage
        KDGlobal global = (KDGlobal) getApplication();
        global.accountId = this.accountId;
        global.accountName = this.accountName;


        Intent intent = new Intent(KeepDoin.this, MainWindow.class);

        Log.i("KeepDoin", "starting activity MainWindow");
        startActivity(intent);
    }
}