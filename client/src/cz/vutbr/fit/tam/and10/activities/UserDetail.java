package cz.vutbr.fit.tam.and10.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.Gravatar;
import cz.vutbr.fit.tam.and10.helpers.User;
import cz.vutbr.fit.tam.and10.ui.BadgesAdapter;

public class UserDetail extends Activity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // launch intent - we get user ID from extra
        Bundle extras = getIntent().getExtras();
        int userId = 0;
        userId = extras.getInt("userId");
        
        // TODO: zjistit, jestli tohle opravdu funguje, protoze to neni dane v manifestu jako android:name atribut u tagu application
        if(userId == 0) {
        	AccountInfoHolder appState = ((AccountInfoHolder)getApplicationContext());
        	userId = appState.getAccountId();
        }

        // nastaveni layoutu pro friendy
        setContentView(R.layout.user_detail);

        GridView gridview = (GridView) findViewById(R.id.badges);
        gridview.setAdapter(new BadgesAdapter(this, 6));
        
        // nacteni informaci o uzivateli
        User user = new User(userId);
        try {
			user.loadData(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        TextView real_name = (TextView) findViewById(R.id.real_name);
        real_name.setText(user.getName());
        
        TextView rank = (TextView) findViewById(R.id.rank);
        rank.setText("rank:"+user.getRank());
        
        ImageView avatar = (ImageView) findViewById(R.id.avatar);

		FileInputStream fos = null;
		String email = user.getEmail();
		String hash = Gravatar.getGravatarHash(email);
		Log.i("KeepDoin", "opening: "+hash+" for email:"+email);
		try {
			fos = this.openFileInput(hash);
			avatar.setImageBitmap(BitmapFactory.decodeStream(fos));
			fos.close();
		// if file is unavailable - loads gravatar default image
		} catch (FileNotFoundException e) {
			avatar.setImageResource(R.drawable.gravatar_default);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private MainMenu menu;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = new MainMenu(this, R.menu.main_menu_friends, menu);
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
