package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.User;
import cz.vutbr.fit.tam.and10.ui.BadgesAdapter;

public class MeTab extends Activity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // launch intent - we get user ID from extra
        int userId = 1;
        
//    	KeepDoin appState = ((KeepDoin)getApplicationContext());
//    	userId = appState.accountId;

        // nastaveni layoutu pro friendy
        setContentView(R.layout.user_detail);

        GridView gridview = (GridView) findViewById(R.id.badges);
        gridview.setAdapter(new BadgesAdapter(this, 6));
        
        // nacteni informaci o uzivateli
        User user = new User(userId);
        user.loadData();
        TextView real_name = (TextView) findViewById(R.id.real_name);
        real_name.setText(user.getName());
        
        TextView rank = (TextView) findViewById(R.id.rank);
        rank.setText("rank:"+user.getRank());

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
