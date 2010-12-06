package cz.vutbr.fit.tam.and10.dialogs;

import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.activities.ManageCategories;

public class AddCategoryDialog extends TextDialog {

	public AddCategoryDialog(ManageCategories activity) {
		super(activity, R.string.dialog_add_category_title);
	}
	
	protected void processValue(String text) {
		((ManageCategories)activity).createCategory(text);
	}

}
