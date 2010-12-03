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

import android.content.Context;
import android.util.Log;

public class Synchronization {

	private Context mContext;
	
	
	
	
	public Synchronization(Context c) {
		mContext = c;
	}



	public void getFriendIcons() {
		
        // load friend list
        JSONObject friendsList = null;

		
        GameModel model = new GameModel();
        try {
			friendsList = model.getFriendsList();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		JSONArray friendsArray = null;
		
		try {
			friendsArray = friendsList.getJSONArray("users");

			for (int i = 0; i < friendsArray.length(); i++) {
				JSONObject user = null;
				user = friendsArray.getJSONObject(i);
				String email = user.getString("email");
				
				String gravatar_url = Gravatar.getGravatarURL(email);
				String gravatar_hash = Gravatar.getGravatarHash(email);
				
				Log.i("KeepDoin", "saving gravatar for:"+email+" - hash:"+gravatar_hash);
				
				downloadURL(gravatar_url, gravatar_hash);
				Log.i("KeepDoin", gravatar_url);
			}
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FileOutputStream fos = mContext.openFileOutput(target_file, mContext.MODE_PRIVATE);
			fos.write(baf.toByteArray());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public void synchronize() {
		
		getFriendIcons();
	}
}
