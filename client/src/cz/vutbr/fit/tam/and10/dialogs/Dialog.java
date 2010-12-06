package cz.vutbr.fit.tam.and10.dialogs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

abstract public class Dialog {

	protected Activity activity;
	protected View view;
	protected Builder dialog;
	
	public Dialog(Activity activity, int layoutResource) {
		this.activity = activity;
		dialog = new Builder(activity);
		
		LayoutInflater inflater = LayoutInflater.from(activity);
		view = inflater.inflate(layoutResource, null);
		dialog.setView(view);
		
		prepareDialog(dialog);
	}
	
	abstract protected void prepareDialog(Builder dialog);
	
	protected static int dip(Context context, int pixels) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}
	
	public void show() {
		dialog.show();
	}

}
