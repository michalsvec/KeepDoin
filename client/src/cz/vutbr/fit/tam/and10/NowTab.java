package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.task.Tasks;

public class NowTab extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tasks tasks = new Tasks(this);
        setContentView(tasks.getView());
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		TextView view = (TextView)v;
		
		menu.setHeaderIcon(android.R.drawable.ic_menu_more);
		menu.setHeaderTitle("Menu");
		
		menu.add(0, 0, 0, "Hovno");
		menu.add(0, 1, 0, "Sracka");
        menu.add(0, 2, 0, "Masakr sracka");
	}
}