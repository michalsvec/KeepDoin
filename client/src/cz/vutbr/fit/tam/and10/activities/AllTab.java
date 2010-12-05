package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AllTab extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO debug
        startActivity(new Intent(this, ManageCategories.class));
        
        // TODO mockup
        TextView textview = new TextView(this);
        textview.setText("Seznam vsech ukolu");
        setContentView(textview);
    }
}
