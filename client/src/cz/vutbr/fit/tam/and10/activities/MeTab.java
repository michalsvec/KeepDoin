package cz.vutbr.fit.tam.and10.activities;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cz.vutbr.fit.tam.and10.KeepDoinApplication;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.User;

public class MeTab extends Activity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	KeepDoinApplication global = (KeepDoinApplication) getApplication();
    	int userId = global.accountId;

        // nastaveni layoutu pro friendy
        setContentView(R.layout.user_detail);

//        GridView gridview = (GridView) findViewById(R.id.badges);
//        gridview.setAdapter(new BadgesAdapter(this, 6));
        
        // nacteni informaci o uzivateli
        User user = new User(userId);
        try {
			user.loadData(this);
		} catch (IOException e) {
			Toast.makeText(this, "Synchronize first. Then, you'll see some really nice guy here.", Toast.LENGTH_LONG).show();
			return;
		}
       
		String email = user.getEmail();
		setRealName(email.substring(0, email.indexOf("@")));
		setRank(user.getRank());
    }
    
    public void setRealName(String name) {
    	TextView view = (TextView) findViewById(R.id.real_name);
    	view.setText(name);
    }
    
    public void setRank(String rank) {
    	TextView view = (TextView) findViewById(R.id.rank);
    	view.setText("rank: " + rank);
    }
    
    private MainMenu menu;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = new MainMenu(this, R.menu.main_menu, menu);
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
