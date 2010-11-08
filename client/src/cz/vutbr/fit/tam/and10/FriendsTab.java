package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class FriendsTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        GridView gridview = (GridView) findViewById(R.id.friendsview);
        gridview.setAdapter(new ImageAdapter(this, 0));


        // event na kliknuti
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // zobrazeni poradi uzivatele - pozustatek z tutorialu
            	//Toast.makeText(FriendsTab.this, "" + position, Toast.LENGTH_SHORT).show();

            	Intent myIntent = new Intent(FriendsTab.this, UserDetail.class);
            	FriendsTab.this.startActivity(myIntent);
            }
        });
        

    }
}
