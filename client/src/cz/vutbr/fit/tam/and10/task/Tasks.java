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
		adapter.add(new Task("Zalít kytičky", Task.Priority.HIGH));
		adapter.add(new Task("Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		adapter.add(new Task("Koupit lístek na Karla Plíhala, abychom si poslechli bleděmodrý krásný koncert", Task.Priority.LOW));
	}

	public View getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			view = (View)inflater.inflate(R.layout.tasks, null);
			
			ListView list = (ListView)view.findViewById(R.id.tasks);
			list.setAdapter(adapter);
						
			// TODO mockup
			TextView t = (TextView)view.findViewById(R.id.category_name);
			t.setText("Category");
		}
		return view;
	}
	
	public ListView getListView() {
		return (ListView)getView().findViewById(R.id.tasks);
	}
}