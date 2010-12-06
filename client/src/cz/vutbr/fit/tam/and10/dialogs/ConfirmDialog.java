package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import cz.vutbr.fit.tam.and10.R;

abstract public class ConfirmDialog extends Dialog {

	public ConfirmDialog(Activity activity, int titleId) {
		super(activity);
		dialog.setTitle(titleId);
	}

	@Override
	protected void prepareDialog(Builder dialog) {
		dialog.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				processValue(true);
			}
		});

		dialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				processValue(false);
				dialog.cancel();
			}
		});
	}
	
	abstract protected void processValue(Boolean result);

}
