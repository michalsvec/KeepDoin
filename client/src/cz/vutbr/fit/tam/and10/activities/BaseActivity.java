package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.Synchronization;

public class BaseActivity extends Activity {

	public BaseActivity() {
		// TODO Auto-generated constructor stub
	}

	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
          switch (item.getItemId())
          {
            case R.id.menu_sync:
            	ProgressDialog dialog = ProgressDialog.show(this, "", "Synchronizing...", true);
            	
            	Synchronization synchro = new Synchronization(this);
            	synchro.synchronize();
            	
                dialog.cancel();
                break;
          }
          return true;
    }
	
}
