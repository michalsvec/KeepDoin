package cz.vutbr.fit.tam.and10.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cz.vutbr.fit.tam.and10.R;

public class Tasks {
	
	protected ArrayAdapter<String> adapter;
	private ListView view;
	
	protected Context context;
	
	public Tasks(Context c) {
		context = c;
		adapter = new TasksAdapter(c);
		
		// TEST
		adapter.add("test1");
		adapter.add("test2");
		adapter.add("test3");
	}

	public ListView getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			view = (ListView)inflater.inflate(R.layout.tasks, null);
			view.setAdapter(adapter);
		}
		return view;
	}
}