package cz.vutbr.fit.tam.and10.helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.KeepDoinApplication;
import cz.vutbr.fit.tam.and10.activities.AccountInfoHolder;

public class Synchronization {

	private Activity mContext;
	private SQLDriver db;



	public Synchronization(Context c) {
		mContext = (Activity) c;	// pretypovai kvuli atributum

		try {
			db = new SQLDriver(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Download friend list, save to local sql database and downloads avatars
	 */
	public void synchronizeFriendsAndUser() {
		
        // load friend list
        JSONObject friendsList = null;
        JSONArray friendsArray = null;
        
        GameModel model = new GameModel();
        try {

        	KeepDoinApplication global = (KeepDoinApplication) mContext.getApplication();
        	try {
        		friendsList = model.getApiResult(global.accountId, "friendsanduser");
        	} catch(ClientProtocolException e) {
        		Toast.makeText(mContext, "Friends synchronization failed with 404. Try again later", Toast.LENGTH_LONG).show();
        		Log.e("KeepDoin", "synchronizeFriendsAndUser()", e);
        		return;
        	}

			// friends table truncate
			this.db.truncateTable("friends");

			if(friendsList != null) {
				friendsArray = friendsList.getJSONArray("friendsanduser");
	
				for (int i = 0; i < friendsArray.length(); i++) {
					JSONObject user = null;
					user = friendsArray.getJSONObject(i);

					// downloads gravatar
					String email = user.getString("email");
					String gravatar_url = Gravatar.getGravatarURL(email);
					String gravatar_hash = Gravatar.getGravatarHash(email);
					Log.i("KeepDoin", "saving gravatar for:"+email+" - hash:"+gravatar_hash);
					downloadURL(gravatar_url, gravatar_hash);
					Log.i("KeepDoin", gravatar_url);

					// saves credentials about user to sql database
					Log.i("TodoGame","userObject: \n"+user.toString()+"\n");
					db.insertFriend(user);
				}
			}
			else 
				return;
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void downloadURL(String file_url, String target_file) throws IOException {
		URL url = null;
		try {
			url = new URL(file_url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		File file = new File(target_file);
		
		URLConnection ucon = url.openConnection();
		InputStream is = ucon.getInputStream();
		
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayBuffer baf = new ByteArrayBuffer(50);
		int current = 0;
		
		try {
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			FileOutputStream fos = mContext.openFileOutput(target_file, Context.MODE_PRIVATE);
			fos.write(baf.toByteArray());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 



	public void synchronize() {
		KeepDoinApplication global = (KeepDoinApplication) mContext.getApplication();
		((AccountInfoHolder)mContext).setAccountId(global.accountId);
		synchronizeFriendsAndUser();

		db.closeDB();
	}
}
