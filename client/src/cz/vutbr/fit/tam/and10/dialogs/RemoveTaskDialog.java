package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.task.Task;

public class RemoveTaskDialog extends ConfirmDialog {

	private Task task;
	
	public RemoveTaskDialog(Activity activity, Task task) {
		super(activity, R.string.dialog_remove_task_title);
		this.task = task;
	}

	protected void processValue(Boolean result) {
		if (result) {
			task.remove();
		}
	}

}
