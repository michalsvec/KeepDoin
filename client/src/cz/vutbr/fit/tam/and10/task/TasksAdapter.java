package cz.vutbr.fit.tam.and10.task;

import java.text.DecimalFormat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.R;

public class TasksAdapter extends ArrayAdapter<Task> {

	protected Activity activity; 
	
	public TasksAdapter(Activity activity) {
		super(activity, R.layout.task);
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			v = inflater.inflate(R.layout.task, null);
		}

		Task t = this.getItem(position);
		
		TextView name = (TextView)v.findViewById(R.id.task_name);
		name.setText(t.getName());
		activity.registerForContextMenu(name);
		
		LinearLayout priority = (LinearLayout)v.findViewById(R.id.task_priority);
		priority.setBackgroundResource(t.getPriority().getColor());
		
		TextView reward = (TextView)v.findViewById(R.id.task_reward);
		DecimalFormat df = new DecimalFormat("#,###,###,###");
		reward.setText(String.valueOf(df.format(t.getCurrentReward())));
		
//		TextView likesCount = (TextView)v.findViewById(R.id.task_likes_count);
//		likesCount.setText("10");
		
		return v;
	}

}
