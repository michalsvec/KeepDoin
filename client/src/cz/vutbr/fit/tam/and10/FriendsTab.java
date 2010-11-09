package cz.vutbr.fit.tam.and10;

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

public class FriendsTab extends Activity {
	
	private User friends[] = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

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
