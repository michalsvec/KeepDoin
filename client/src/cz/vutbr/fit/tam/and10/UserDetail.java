package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class UserDetail extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // nastaveni layoutu pro friendy
        setContentView(R.layout.userdetail);
        
        GridView gridview = (GridView) findViewById(R.id.badges);
        gridview.setAdapter(new ImageAdapter(this, 6));
        
        // nacteni informaci o uzivateli
        
        User user = new User(1);
        TextView real_name = (TextView) findViewById(R.id.real_name);
        real_name.setText(user.getName());
        
        TextView rank = (TextView) findViewById(R.id.rank);
        rank.setText("rank:"+user.getRank());

	}
}
