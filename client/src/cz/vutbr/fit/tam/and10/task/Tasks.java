package cz.vutbr.fit.tam.and10.task;

import java.util.List;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.commonsware.cwac.merge.MergeAdapter;

import cz.vutbr.fit.tam.and10.R;

public class Tasks {

	protected MergeAdapter adapter;
	protected View view;
	protected Activity activity;

	public Tasks(Activity a) {
		activity = a;
		adapter = new MergeAdapter();
		
		// TODO MOCKUP
		addCategory(new Category(a, "Práce"));
		TasksAdapter t = new TasksAdapter(activity);
		t.add(new Task(activity, "Zalít v práci kytičky", Task.Priority.HIGH));
		t.add(new Task(activity, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		t.add(new Task(activity, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
		adapter.addAdapter(t);
		
		addCategory(new Category(a, "Škola"));
		t = new TasksAdapter(activity);
		t.add(new Task(activity, "Zalít ve škole kytičky", Task.Priority.HIGH));
		t.add(new Task(activity, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		t.add(new Task(activity, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
		adapter.addAdapter(t);
		
		addCategory(new Category(a, "Doma"));
		t = new TasksAdapter(activity);
		t.add(new Task(activity, "Zalít doma kytičky", Task.Priority.HIGH));
		t.add(new Task(activity, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		t.add(new Task(activity, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
		adapter.addAdapter(t);
		
		a.registerForContextMenu(getListView());
	}
	
	public void addTasks(List<Task> tasks) {
		adapter.addAdapter(new TasksAdapter(activity, tasks));
	}
	
	public void addCategory(final Category c) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View v = inflater.inflate(R.layout.category, null);
		
		ImageButton addTask = (ImageButton)v.findViewById(R.id.add_task);
		addTask.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				c.createTask();
			}
		});
		
		TextView n = (TextView)v.findViewById(R.id.category_name);
		n.setText(c.getName());
		
		adapter.addView(v);
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
		Object item = adapter.getItem(position);
		if (item instanceof Task) {
			return (Task)item;
		}
		return null;
	}
	
	public ListView getListView() {
		return (ListView)getView().findViewById(R.id.tasks);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.task_context_menu, menu);
		menu.setHeaderTitle(R.string.context_menu_title);
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