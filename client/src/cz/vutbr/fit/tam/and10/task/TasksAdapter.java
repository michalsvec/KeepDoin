package cz.vutbr.fit.tam.and10.task;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.R;

public class TasksAdapter extends ArrayAdapter<Task> implements OnCreateContextMenuListener {

	protected Activity activity;
	protected Boolean previewOnly = false;
	
	public TasksAdapter(Activity activity) {
		super(activity, R.layout.task);
		this.activity = activity;
	}
	
	public TasksAdapter(Activity activity, List<Task> tasks) {
		super(activity, R.layout.task, tasks);
		this.activity = activity;
	}
	
	public TasksAdapter(Activity activity, Task[] tasks) {
		super(activity, R.layout.task, tasks);
		this.activity = activity;
	}
	
	public void setPreviewOnly(Boolean previewOnly) {
		this.previewOnly = previewOnly;
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
			DecimalFormat df = new DecimalFormat("#,###,###,###");

			((TextView)v.findViewById(R.id.task_name)).setText(t.getName());
			
			((LinearLayout)v.findViewById(R.id.task_priority)).setBackgroundResource(t.getPriority().getColor());
			
			TextView reward = (TextView)v.findViewById(R.id.task_reward);
			if (previewOnly) {
				reward.setVisibility(View.GONE);
				((TextView)v.findViewById(R.id.task_reward_label)).setVisibility(View.GONE);
			} else {
				reward.setText(String.valueOf(df.format(t.getCurrentReward())));
			}
			
			Date d = t.getDeadline();
			if (d != null) {
				((TextView)v.findViewById(R.id.task_deadline)).setText(DateFormat.getDateInstance().format(d));
			}
			
			TextView likes = (TextView)v.findViewById(R.id.task_likes);
			if (previewOnly) {
				likes.setVisibility(View.GONE);
				((TextView)v.findViewById(R.id.task_likes_label)).setVisibility(View.GONE);
			} else {
				likes.setText(String.valueOf(df.format(t.getCurrentLikes())));
			}
			
			if (previewOnly) {
				if (t.isLikedByCurrentUser()) {
					((TextView)v.findViewById(R.id.task_like_this)).setVisibility(View.GONE);
				} else {
					((TextView)v.findViewById(R.id.task_liked)).setVisibility(View.GONE);
				}
			} else {
				((TextView)v.findViewById(R.id.task_like_this)).setVisibility(View.GONE);
				((TextView)v.findViewById(R.id.task_liked)).setVisibility(View.GONE);
			}
			
			CheckBox complete = (CheckBox) v.findViewById(R.id.task_complete);
			if (previewOnly) {
				complete.setVisibility(View.GONE);
			} else {
				complete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						CheckBox checkbox = (CheckBox)v;
						if (checkbox.isChecked()) {
							t.complete();
						} else {
							t.uncomplete();
						}
					}
				});
				}
			
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
