package cz.vutbr.fit.tam.and10;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class KeepDoin extends Activity {
	
	
    private TextView mMessage;
    private String mPassword;
    private EditText mPasswordEdit;

    private String mUsername;
    private EditText mUsernameEdit;
	
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
    
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.login_activity);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
            android.R.drawable.ic_dialog_alert);

        mMessage = (TextView) findViewById(R.id.message);
        mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);

        mUsernameEdit.setText(mUsername);
        mMessage.setText(getMessage());
    }



    /**
     * Returns the message to be displayed at the top of the login dialog box.
     */
    private CharSequence getMessage() {
        getString(R.string.app_name);
        if (TextUtils.isEmpty(mUsername)) {
            // If no username, then we ask the user to log in using an
            // appropriate service.
            final CharSequence msg =
                getText(R.string.login_header_text);
            return msg;
        }
        if (TextUtils.isEmpty(mPassword)) {
            // We have an account but no password
            return getText(R.string.login_activity_loginfail);
        }
        return null;
    }



    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * 
     * @param view The Submit button for which this method is invoked
     */
    public void handleLogin(View view) {

        mUsername = mUsernameEdit.getText().toString();
        mPassword = mPasswordEdit.getText().toString();
        
        Log.i("KeepDoin", "username:" + mUsername + "");
        Log.i("KeepDoin", "password:" + mPassword + "");
        
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            mMessage.setText(getMessage());
        } else {
            mMessage.setText("pekna pica!!!!");
            showProgress();
            Log.i("ToDoGame", "Starting thread!");
            
            // Start authenticating...
            mAuthThread = GameModel.attemptAuth(mUsername, mPassword, mHandler, KeepDoin.this);
        }
    }
    


    /**
     * Shows the progress UI for a lengthy operation.
     */
    protected void showProgress() {
        showDialog(0);
    }



    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.ui_activity_authenticating));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Log.i("KeepGoing", "dialog cancel has been invoked");
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
        // Hide the progress dialog
        dismissDialog(0);

        if (result) {
            finishLogin();
        } else {
            Log.e("KeepDoin", "onAuthenticationResult: failed to authenticate");
            mMessage.setText(getText(R.string.login_activity_loginfail));
        }
    }



    /**
     * 
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     * 
     * @param the confirmCredentials result.
     */
    protected void finishLogin() {
        Log.i("KeepDoin", "finishLogin()");
//        final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);

//        if (mRequestNewAccount) {
//            mAccountManager.addAccountExplicitly(account, mPassword, null);
//            // Set contacts sync for this account.
//            ContentResolver.setSyncAutomatically(account,
//                ContactsContract.AUTHORITY, true);
//        } else {
//            mAccountManager.setPassword(account, mPassword);
//        }
//        final Intent intent = new Intent();
//        mAuthtoken = mPassword;
//        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
//        intent
//            .putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
//        if (mAuthtokenType != null
//            && mAuthtokenType.equals(Constants.AUTHTOKEN_TYPE)) {
//            intent.putExtra(AccountManager.KEY_AUTHTOKEN, mAuthtoken);
//        }
//        setAccountAuthenticatorResult(intent.getExtras());
//        setResult(RESULT_OK, intent);
        finish();
    }
}