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
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.commonsware.cwac.merge.MergeAdapter;

import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;

public class Tasks {

	protected MergeAdapter adapter;
	protected View view;
	protected Activity activity;
	
	protected Boolean previewOnly = false;

	public Tasks(Activity a) {
		activity = a;
		adapter = new MergeAdapter();
	}
	
	public Tasks(Activity a, Boolean previewOnly) {
		this(a);
		this.previewOnly = previewOnly;
	}
	
	public Adapter addTasks(Task[] tasks) {
		TasksAdapter t = new TasksAdapter(activity, tasks);
		t.setPreviewOnly(previewOnly);
		adapter.addAdapter(t);
		return t;
	}
	
	public Adapter addTasks(List<Task> tasks) {
		TasksAdapter t = new TasksAdapter(activity, tasks);
		t.setPreviewOnly(previewOnly);
		adapter.addAdapter(t);
		return t;
	}
	
	public void addCategory(final Category c) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View v = inflater.inflate(R.layout.header, null);
		
		if (previewOnly) {
			v.findViewById(R.id.header_separator).setVisibility(View.GONE);
			v.findViewById(R.id.header_add).setVisibility(View.GONE);
		}
		
		ImageButton addTask = (ImageButton)v.findViewById(R.id.header_add);
		addTask.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				c.createTaskDialog();
			}
		});
		
		TextView n = (TextView)v.findViewById(R.id.header_name);
		n.setText(c.getName());
		
		adapter.addView(v);
	}
	
	public void addCategory(Category c, Task[] tasks) {
		addCategory(c);
		c.setAdapter(addTasks(tasks));
	}
	
	public void addCategory(Category c, List<Task> tasks) {
		addCategory(c);
		c.setAdapter(addTasks(tasks));
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
        if (previewOnly) {
        	inflater.inflate(R.menu.task_context_menu_preview, menu);
        } else {
        	inflater.inflate(R.menu.task_context_menu, menu);
        }
		menu.setHeaderTitle(R.string.context_menu_title);
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Task task = getItem(info.position);
    	
    	switch (item.getItemId()) {
		case R.id.context_menu_change_text:
			task.changeTextDialog();
			return true;
		case R.id.context_menu_change_priority:
			task.changePriorityDialog();
			return true;
		case R.id.context_menu_change_deadline:
			task.changeDeadlineDialog();
			return true;
		case R.id.context_menu_change_category:
			task.changeCategoryDialog();
			return true;
		case R.id.context_menu_remove:
			task.removeDialog();
			return true;
		case R.id.context_menu_like:
			task.like();
			return true;
		}
		return false;
	}
	
}