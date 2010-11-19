package cz.vutbr.fit.tam.and10.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.R;

public class TasksAdapter extends ArrayAdapter<String> {

	protected Context context; 
	
	public TasksAdapter(Context c) {
		super(c, R.layout.task);
		context = c;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			v = inflater.inflate(R.layout.task, null);
		}

		TextView title = (TextView)v.findViewById(R.id.task_title);
		title.setText(this.getItem(position));
		
		TextView reward = (TextView)v.findViewById(R.id.task_reward);
		reward.setText("2.000");
		
		TextView likesCount = (TextView)v.findViewById(R.id.task_likes_count);
		likesCount.setText("10");
		
		return v;
	}

}
