package cz.vutbr.fit.tam.and10;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.activities.FriendsTab;
import cz.vutbr.fit.tam.and10.activities.ManageCategories;
import cz.vutbr.fit.tam.and10.helpers.GameModel;
import cz.vutbr.fit.tam.and10.helpers.Synchronization;

public class MainMenu extends Activity {

	private Activity activity;
	public String dialogMsg;
	
	public MainMenu(Context a, int menuResource, Menu menu) {
		activity = (Activity) a;
		MenuInflater inflater = activity.getMenuInflater();
		inflater.inflate(menuResource, menu);
	}

	private void doSynchronize() {
		
		Synchronization sync = new Synchronization(activity);
		sync.synchronize();
		
	}

	private void doManageCategories() {
		activity.startActivity(new Intent(activity, ManageCategories.class));
	}

	private void doAddFriend() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		final EditText input = new EditText(activity);
		alert.setView(input);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
				Toast.makeText(activity.getApplicationContext(),
						"Request sent", Toast.LENGTH_SHORT).show();
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

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_sync:
			//ProgressDialog dialog = ProgressDialog.show(((Context) activity), "", "Loading. Please wait...", true);
			doSynchronize();
			//dialog.dismiss();
			return true;

		case R.id.menu_manage_categories:
			doManageCategories();
			return true;

		case R.id.menu_add_friend:
			doAddFriend();
			return true;
		}
		return false;
	}
}
