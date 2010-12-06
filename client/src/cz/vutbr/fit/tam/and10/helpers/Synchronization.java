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

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import cz.vutbr.fit.tam.and10.KDGlobal;
import cz.vutbr.fit.tam.and10.activities.BaseActivity;

public class Synchronization {

	private BaseActivity mContext;
	private SQLDriver db;



	public Synchronization(Context c) {
		mContext = (BaseActivity) c;	// pretypovai kvuli atributum
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
        	KDGlobal global = (KDGlobal) mContext.getApplication();
			friendsList = model.getApiResult(global.accountId, "friendsanduser");

			// friends table truncate
			this.db.truncateTable("friends");
			Cursor cur = db.db.rawQuery("SELECT name FROM sqlite_master ORDER BY name;", new String [] {});

			cur.moveToFirst();
			while (cur.isAfterLast() == false) {
				Log.i("KeepDoin", "tabulka:"+cur.getString(cur.getColumnIndex("name")));
				cur.moveToNext();
			}
			cur.close();


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
		
		KDGlobal global = (KDGlobal) mContext.getApplication();
        mContext.accountId =  global.accountId;
		synchronizeFriendsAndUser();
	}
}