package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MeTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("Mam rad hranolky s kecupem a posloucham Frankieho z Litomysle.");
        setContentView(textview);
    }
}
