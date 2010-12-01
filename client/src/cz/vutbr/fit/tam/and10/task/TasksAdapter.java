package cz.vutbr.fit.tam.and10.task;

import java.text.DecimalFormat;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.R;

public class TasksAdapter extends ArrayAdapter<Task> implements OnCreateContextMenuListener {

	protected Activity activity; 
	
	public TasksAdapter(Activity activity) {
		super(activity, R.layout.task);
		this.activity = activity;
	}
	
	@Override
	public void add(Task task) {
		super.add(task);
		task.setPosition(getCount() - 1);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			v = inflater.inflate(R.layout.task, null);
		}
		
		final Task t = getItem(position);
		if (t != null) {
			TextView name = (TextView)v.findViewById(R.id.task_name);
			name.setText(t.getName());
			
			LinearLayout priority = (LinearLayout)v.findViewById(R.id.task_priority);
			priority.setBackgroundResource(t.getPriority().getColor());
			
			TextView reward = (TextView)v.findViewById(R.id.task_reward);
			DecimalFormat df = new DecimalFormat("#,###,###,###");
			reward.setText(String.valueOf(df.format(t.getCurrentReward())));
			
	//		TextView likesCount = (TextView)v.findViewById(R.id.task_likes_count);
	//		likesCount.setText("10");
			
			v.setOnCreateContextMenuListener(this);
			v.setTag(t);
		}
		return v;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// empty implementation
	}

}
