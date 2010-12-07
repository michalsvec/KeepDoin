package cz.vutbr.fit.tam.and10.dialogs;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.task.Task;

public class ChangePriorityDialog extends Dialog {

	protected Task task;
	protected ArrayList<String> items;
	
	public ChangePriorityDialog(Activity activity, Task task) {
		super(activity);
		dialog.setTitle(R.string.dialog_change_priority);
		this.task = task;
	}
	
	protected void processValue(int item) {
		task.changePriority(item);
	}

	@Override
	protected void prepareDialog(Builder dialog) {
		items = new ArrayList<String>();
		items.add(0, activity.getString(R.string.priority_low));
		items.add(1, activity.getString(R.string.priority_medium));
		items.add(2, activity.getString(R.string.priority_high));
		
		dialog.setItems(items.toArray(new CharSequence[items.size()]), new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        processValue(item);
		    }
		});
	}
}
