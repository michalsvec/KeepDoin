package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.task.Task;

public class ChangeTaskTextDialog extends TextDialog {

	private Task task;
	
	public ChangeTaskTextDialog(Activity activity, Task task) {
		super(activity, R.string.dialog_change_task_text_title);
		this.task = task;
	}
	
	protected void processValue(String text) {
		task.changeText(text);
	}

}
