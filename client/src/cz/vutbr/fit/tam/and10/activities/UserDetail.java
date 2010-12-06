package cz.vutbr.fit.tam.and10.activities;

import java.io.IOException;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.KeepDoin;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.User;
import cz.vutbr.fit.tam.and10.ui.BadgesAdapter;

public class UserDetail extends BaseActivity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // launch intent - we get user ID from extra
        Bundle extras = getIntent().getExtras();
        int userId = 0;
        userId = extras.getInt("userId");
        
        // TODO: zjistit, jestli tohle opravdu funguje, protoze to neni dane v manifestu jako android:name atribut u tagu application
        if(userId == 0) {
        	KeepDoin appState = ((KeepDoin)getApplicationContext());
        	userId = appState.accountId;
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

	}
}
