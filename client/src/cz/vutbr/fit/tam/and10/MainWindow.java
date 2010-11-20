package cz.vutbr.fit.tam.and10;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


public class MainWindow extends TabActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources();  // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;            // Reusable TabSpec for each tab
        Intent intent;                   // Reusable Intent for each tab - intent = ucel

        // nastaveni ucelu tabu - tzn. nastaveni tridy, ktera za tab odpovida
        intent = new Intent().setClass(this, NowTab.class);
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("now").setIndicator("Now",			// oznaceni tagem "now" a nastaveni ikony a obsahu(aktivity)
                          res.getDrawable(R.drawable.ico_tab_now))		// nastaveni ikony pro tab
                      .setContent(intent);								// nastaveni obsahu tabu
        tabHost.addTab(spec);


        // tab se vsemi ukoly pro uzivatele
        // zobrazeni kategorii, seznamu atd.
        intent = new Intent().setClass(this, AllTab.class);
        spec = tabHost.newTabSpec("all").setIndicator("All",
                          res.getDrawable(R.drawable.ico_tab_all))
                      .setContent(intent);
        tabHost.addTab(spec);


        // seznam friendu
        // v pripade http://malcolm816.com/blog/wp-content/gallery/forever-alone/text-forever-alone.jpg
        //se vypise jen nejaka dummy message, ze by si mel zacit hledat kamose
        intent = new Intent().setClass(this, FriendsTab.class);
        spec = tabHost.newTabSpec("friends").setIndicator("Friends",
                          res.getDrawable(R.drawable.ico_tab_friends))
                      .setContent(intent);
        tabHost.addTab(spec);


        // profilek uzivatele
        // zonbrazeni udaju o uzivateli, nejaktualnejsi ukoly, seznam badges
        intent = new Intent().setClass(this, MeTab.class);
        spec = tabHost.newTabSpec("me").setIndicator("Me",
                          res.getDrawable(R.drawable.ico_tab_me))
                      .setContent(intent);
        tabHost.addTab(spec);

        // nastaveni defaultniho tabu
        tabHost.setCurrentTabByTag("now");
    }
}
