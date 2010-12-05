package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.BadgesAdapter;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.User;

public class MeTab extends Activity {
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
}