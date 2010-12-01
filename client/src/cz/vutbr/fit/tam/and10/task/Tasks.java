package cz.vutbr.fit.tam.and10.task;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.R;

public class Tasks {

	protected TasksAdapter adapter;
	private View view;

	protected Activity activity;

	public Tasks(Activity a) {
		activity = a;
		adapter = new TasksAdapter(a);

		// TODO mockup
		adapter.add(new Task(a, "Zalít kytičky", Task.Priority.HIGH));
		adapter.add(new Task(a, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		adapter.add(new Task(a, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
	}
	
	public View getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			view = (View) inflater.inflate(R.layout.tasks, null);

			ListView list = (ListView) view.findViewById(R.id.tasks);
			list.setAdapter(adapter);

			// TODO mockup
			TextView t = (TextView) view.findViewById(R.id.category_name);
			t.setText("Category");
		}
		return view;
	}
	
	public Task getItem(int position) {
		return adapter.getItem(position);
	}
	
//	public Task getItem(ContextMenuInfo menuInfo) {
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
//		
//		// search for the closest task_layout
//		View v = (View)info.targetView;
//		while (v.findViewById(R.id.task_layout) == null) {
//			ViewParent parent = v.getParent();
//			if (parent instanceof View) {
//				v = (View)parent;
//			} else {
//				v = null;
//				break;
//			}
//		}
//		
//		// return task object saved in tag
//		if (v != null) {
//			return (Task)v.getTag();
//		}
//		return null;
//	}

	public ListView getListView() {
		return (ListView)getView().findViewById(R.id.tasks);
	}
}