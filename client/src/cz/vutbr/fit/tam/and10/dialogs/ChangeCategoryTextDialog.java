package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;

public class ChangeCategoryTextDialog extends TextDialog {

	private Category category;
	
	public ChangeCategoryTextDialog(Activity activity, Category category) {
		super(activity, R.string.dialog_change_category_text_title);
		this.category = category;
	}
	
	protected void processValue(String text) {
		category.changeText(text);
	}

}
