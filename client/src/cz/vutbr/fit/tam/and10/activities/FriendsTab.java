package cz.vutbr.fit.tam.and10.activities;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.GameModel;
import cz.vutbr.fit.tam.and10.helpers.SQLDriver;
import cz.vutbr.fit.tam.and10.helpers.User;
import cz.vutbr.fit.tam.and10.ui.FriendListAdapter;

public class FriendsTab extends Activity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	public FriendListAdapter adapter= null;
	
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
		
		if(this.friends.size() == 0) {
			Toast.makeText(this, "No friends yet. Don't be shy! Make some!", Toast.LENGTH_LONG).show();
			return;
		}

		// friends adapter
		adapter = new FriendListAdapter(this, this.friends);
		
		// initialize friends gridview
		GridView gridview = (GridView) findViewById(R.id.friendsview);
        gridview.setAdapter(adapter);
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
        
        if(this.sqlDriver != null)
        	this.sqlDriver.closeDB();
    }
    
    private MainMenu menu;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = new MainMenu(FriendsTab.this, R.menu.main_menu_friends, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return this.menu.onOptionsItemSelected(item);
    }
    
    @Override
	public int getAccountId() {
		return accountId;
	}

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
