package cz.vutbr.fit.tam.and10.task;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import cz.vutbr.fit.tam.and10.R;

public class Tasks {

	protected TasksAdapter adapter;
	protected View view;
	protected Activity activity;

	public Tasks(Activity a) {
		activity = a;
		adapter = new TasksAdapter(a);
		a.registerForContextMenu(getListView());
	}
	
	public void add(Task t) {
		adapter.add(t);
	}
	
	public View getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			view = (View) inflater.inflate(R.layout.tasks, null);

			ListView list = (ListView)view.findViewById(R.id.tasks);
			list.setAdapter(adapter);
		}
		return view;
	}
	
	public Task getItem(int position) {
		return adapter.getItem(position);
	}
	
	public ListView getListView() {
		return (ListView)getView().findViewById(R.id.tasks);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.task_context_menu, menu);
		
		TextView name = (TextView)v.findViewById(R.id.task_name);
		menu.setHeaderTitle(name.getText().toString());
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Task task = getItem(info.position);
    	
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