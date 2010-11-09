package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class UserDetail extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // launch intent - we get user ID from extra
        Intent intent = new Intent();
        Bundle extras = getIntent().getExtras();
        int userId = extras.getInt("userId");
        
        // nastaveni layoutu pro friendy
        setContentView(R.layout.userdetail);

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
