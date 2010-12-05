package cz.vutbr.fit.tam.and10.activities;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import cz.vutbr.fit.tam.and10.KDGlobal;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.GameModel;
import cz.vutbr.fit.tam.and10.helpers.User;
import cz.vutbr.fit.tam.and10.ui.FriendListAdapter;

public class FriendsTab extends BaseActivity {
	
	private User friends[] = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        // load friend list
        JSONObject friendsList = null;
        JSONArray friendsArray = null;
        
        
        
        GameModel model = new GameModel();
        try {
        	KDGlobal global = (KDGlobal) getApplication();

        	try {
				friendsList = model.getFriendsList(global.accountId);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			friendsArray = friendsList.getJSONArray("users");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		this.friends = new User[friendsArray.length()];
		for (int i = 0; i < friendsArray.length(); i++) {
			JSONObject user = null;
			try {
				user = friendsArray.getJSONObject(i);
				User friend = new User(user.getInt("id"));
				friend.setName(user.getString("real_name"));
				friend.setEmail(user.getString("email"));
				
				friends[i] = friend;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		

		// initialize friends gridview
		GridView gridview = (GridView) findViewById(R.id.friendsview);
        gridview.setAdapter(new FriendListAdapter(this, friends));

        
        // event na kliknuti
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            	// nechapu tuhle posahanou syntaxi, ale funguje, takze to tu nechavam
            	int userID = FriendsTab.this.friends[position].getId();

            	Intent myIntent = new Intent(FriendsTab.this, UserDetail.class);
            	myIntent.putExtra("userId", userID);
            	FriendsTab.this.startActivity(myIntent);
            }
        });
        

    }
}
