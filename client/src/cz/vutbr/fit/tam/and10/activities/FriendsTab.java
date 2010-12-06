package cz.vutbr.fit.tam.and10.activities;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.GameModel;
import cz.vutbr.fit.tam.and10.helpers.SQLDriver;
import cz.vutbr.fit.tam.and10.helpers.User;
import cz.vutbr.fit.tam.and10.ui.FriendListAdapter;

public class FriendsTab extends BaseActivity {
	
	//private User friends[] = null;


	private ArrayList<User> friends;
	private SQLDriver sqlDriver = null;
	GameModel model = null; 
	
	
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("KeepDoin", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        try {
			this.sqlDriver = new SQLDriver(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.model = new GameModel();
		this.friends = sqlDriver.getFriends();

		Log.i("KeepDoin", this.friends.toString());
		
		// initialize friends gridview
		GridView gridview = (GridView) findViewById(R.id.friendsview);
        gridview.setAdapter(new FriendListAdapter(this, this.friends));
        Log.i("KeepDoin", "Adaper set");


        // event na kliknuti
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            	// nechapu tuhle posahanou syntaxi, ale funguje, takze to tu nechavam
            	int userID = FriendsTab.this.friends.get(position).getId();

            	Intent myIntent = new Intent(FriendsTab.this, UserDetail.class);
            	myIntent.putExtra("userId", userID);
            	FriendsTab.this.startActivity(myIntent);
            }
        });
        

    }
}
