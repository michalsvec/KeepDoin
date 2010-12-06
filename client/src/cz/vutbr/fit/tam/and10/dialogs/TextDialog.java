package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.widget.EditText;
import cz.vutbr.fit.tam.and10.R;

abstract public class TextDialog extends Dialog {

	public TextDialog(Activity activity, int titleId) {
		super(activity, R.layout.text_dialog);
		dialog.setTitle(titleId);
	}

	@Override
	protected void prepareDialog(Builder dialog) {
		dialog.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				EditText et = (EditText)view.findViewById(R.id.dialog_input);
				String text = et.getText().toString().trim();
				processValue(text);
			}
		});

		dialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
	}
	
	abstract protected void processValue(String text);

}
