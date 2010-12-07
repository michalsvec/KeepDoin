package cz.vutbr.fit.tam.and10.dialogs;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;
import cz.vutbr.fit.tam.and10.task.Task;

public class ChangeCategoryDialog extends Dialog {

	protected Task task;
	protected ArrayList<Category> items;
	
	public ChangeCategoryDialog(Activity activity, Task task) {
		super(activity);
		dialog.setTitle(R.string.dialog_change_category);
		this.task = task;
	}
	
	protected void processValue(int item) {
		task.changeCategory(items.get(item));
	}

	@Override
	protected void prepareDialog(Builder dialog) {
		items = new ArrayList<Category>();
		// TODO mockup
		items.add(new Category(activity, "Práce"));
		items.add(new Category(activity, "Škola"));
		items.add(new Category(activity, "Doma"));
		
		ArrayList<String> texts = new ArrayList<String>();
		for (Iterator<Category> iterator = items.iterator(); iterator.hasNext();) {
			Category c = iterator.next();
			texts.add(c.toString());
		}
		dialog.setItems(texts.toArray(new CharSequence[texts.size()]), new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        processValue(item);
		    }
		});
	}
}
