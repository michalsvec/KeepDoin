package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;

public class RemoveCategoryDialog extends ConfirmDialog {

	private Category category;
	
	public RemoveCategoryDialog(Activity activity, Category category) {
		super(activity, R.string.dialog_remove_category_title);
		this.category = category;
	}

	protected void processValue(Boolean result) {
		if (result) {
			category.remove();
		}
	}

}
