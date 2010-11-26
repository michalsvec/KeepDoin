package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AllTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.all_tasks);

        TextView textview = new TextView(this);
        textview.setText("Seznam vsech ukolu");
        setContentView(textview);
    }
}
