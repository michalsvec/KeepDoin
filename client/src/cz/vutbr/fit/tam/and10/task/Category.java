package cz.vutbr.fit.tam.and10.task;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.R;

public class Category extends Tasks {

	protected String name;
	protected int order;
	
	public Category(Activity a, String name, int order) {
		super(a);
		this.name = name;
		this.order = order;
	}

	public void prepareView(View v) {
		ListView list = (ListView)v.findViewById(R.id.tasks);
		list.setAdapter(adapter);
		
		TextView t = (TextView)v.findViewById(R.id.category_name);
		t.setText(name);
		
		ImageButton addTask = (ImageButton)v.findViewById(R.id.add_task);
		addTask.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addTask();
			}
		});
	}
	
	public View getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			view = (View)inflater.inflate(R.layout.category, null);
			prepareView(view);
		}
		return view;
	}
	
	public void addTask() {
		Toast.makeText(activity, "add new task in " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}