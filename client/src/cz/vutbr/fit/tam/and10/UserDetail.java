package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class UserDetail extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // nastaveni layoutu pro friendy
        setContentView(R.layout.userdetail);
        
        GridView gridview = (GridView) findViewById(R.id.badges);
        gridview.setAdapter(new ImageAdapter(this, 6));
        
        
	}
}
