package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;

public class AddTaskDialog extends TextDialog {

	private Category category;
	
	public AddTaskDialog(Activity activity, Category category) {
		super(activity, R.string.dialog_add_task_title);
		this.category = category;
	}
	
	protected void processValue(String text) {
		category.createTask(text);
	}

}
