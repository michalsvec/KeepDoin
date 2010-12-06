package cz.vutbr.fit.tam.and10.dialogs;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.task.Task;

public class DateDialog {
	
	protected Activity activity;
	protected View view;
	protected DatePickerDialog dialog;
	protected Task task;

	public DateDialog(Activity activity, Task task) {
		this.activity = activity;
		this.task = task;
		init(activity, task);
		dialog.setTitle(R.string.dialog_pick_date);
	}
	
	public DateDialog(Activity activity, Task task, int defaultYear, int defaultMonth, int defaultDay) {
		this.activity = activity;
		this.task = task;
		init(activity, task, defaultYear, defaultMonth, defaultDay);
		dialog.setTitle(R.string.dialog_pick_date);
	}
	
	protected void init(Activity activity, Task task) {
		final Calendar c = Calendar.getInstance();
		int defaultDay = c.get(Calendar.DAY_OF_MONTH);
		int defaultMonth = c.get(Calendar.MONTH);
		int defaultYear = c.get(Calendar.YEAR);
		
		dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                processValue(year, month, day);
            }
        }, defaultYear, defaultMonth, defaultDay);
	}
	
	protected void init(Activity activity, Task task, int defaultYear, int defaultMonth, int defaultDay) {
		if (defaultDay == 0 || defaultMonth == 0 || defaultYear == 0) {
			init(activity, task);
		} else {
			dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
	            public void onDateSet(DatePicker view, int year, int month, int day) {
	                processValue(year, month, day);
	            }
	        }, defaultYear, defaultMonth, defaultDay);
		}
	}

	protected void processValue(int year, int month, int day) {
		task.changeDeadline(year, month, day);
	}
	
	public void show() {
		dialog.show();
	}

}
