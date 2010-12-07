package cz.vutbr.fit.tam.and10.dialogs;

import java.util.Calendar;
import java.util.Date;

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

	public DateDialog(Activity activity, Task task, Date defaultDate) {
		this.activity = activity;
		this.task = task;
		
		int defaultDay;
		int defaultMonth;
		int defaultYear;
		
		if (defaultDate == null) {
			final Calendar c = Calendar.getInstance();
			defaultDay = c.get(Calendar.DAY_OF_MONTH);
			defaultMonth = c.get(Calendar.MONTH);
			defaultYear = c.get(Calendar.YEAR);
		} else {
			defaultDay = defaultDate.getDay();
			defaultMonth = defaultDate.getMonth();
			defaultYear = defaultDate.getYear() + 1900;
		}
		
		dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                processValue(year, month, day);
            }
        }, defaultYear, defaultMonth, defaultDay);
		
		dialog.setTitle(R.string.dialog_pick_date);
	}
	
	protected void processValue(int year, int month, int day) {
		task.changeDeadline(new Date(year - 1900, month, day));
	}
	
	public void show() {
		dialog.show();
	}

}
