package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.task.Task;
import cz.vutbr.fit.tam.and10.task.Tasks;

public class NowTab extends Activity {
	
	private Tasks tasks;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasks = new Tasks(this);
        setContentView(tasks.getView());
        registerForContextMenu(tasks.getListView());
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_context_menu, menu);
		
		TextView name = (TextView)v.findViewById(R.id.task_name);
		menu.setHeaderIcon(android.R.drawable.ic_menu_more);
		menu.setHeaderTitle(name.getText().toString());
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
    	Task task = tasks.getItem(info.position);
    	
    	switch (item.getItemId()) {
		case R.id.context_menu_change_text:
			task.changeText();
			return true;
		case R.id.context_menu_change_priority:
			task.changePriority();
			return true;
		case R.id.context_menu_change_deadline:
			task.changeDeadline();
			return true;
		case R.id.context_menu_change_category:
			task.changeCategory();
			return true;
		case R.id.context_menu_remove:
			task.remove();
			return true;
		}
		return false;
    }
}