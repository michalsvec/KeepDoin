package cz.vutbr.fit.tam.and10.activities;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.GameModel;
import cz.vutbr.fit.tam.and10.helpers.Synchronization;

public class BaseActivity extends Activity {

	
	public String accountName;
	public int accountId;

	
	public BaseActivity() {
		// TODO Auto-generated constructor stub
        //KDGlobal global = (KDGlobal) getApplication();
        //this.accountId =  global.accountId;
        //this.accountName = global.accountName;

	}

	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	ProgressDialog dialog = null;
    	
          switch (item.getItemId())
          {
            case R.id.menu_sync:
            	dialog = ProgressDialog.show(this, "", "Synchronizing...", true);
            	
            	Synchronization synchro = new Synchronization(this);
            	synchro.synchronize();
            	
                dialog.cancel();
                break;
            
            case R.id.menu_add_friend:
            	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        		final EditText input = new EditText(this);
        		alert.setView(input);
        		
        		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int whichButton) {
        				String email = input.getText().toString().trim();
        				
        				GameModel model = new GameModel();
        				
        				try {
							model.postFriendRequest(13, email);
						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
        				Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
        			}
        		});

        		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
    			});
        		
        		alert.show();
            	
            	break;
          }
          return true;
    }
	
}
