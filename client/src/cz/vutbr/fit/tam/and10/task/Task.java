package cz.vutbr.fit.tam.and10.task;

import java.security.InvalidParameterException;
import java.util.Random;

import android.app.Activity;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.dialogs.ChangeTaskTextDialog;
import cz.vutbr.fit.tam.and10.dialogs.DateDialog;
import cz.vutbr.fit.tam.and10.dialogs.RemoveTaskDialog;
import cz.vutbr.fit.tam.and10.dialogs.TextDialog;

public class Task {

	public enum Priority {
		LOW(1), MEDIUM(2), HIGH(3);
	
		private int value;
		
		private Priority(int value) {
			this.value = value;
		}
		
		public int getColor() {
			switch (this.value) {
			case 1:
				return R.color.task_priority_low;
			case 2:
				return R.color.task_priority_medium;
			case 3:
				return R.color.task_priority_high;
			default:
				throw new InvalidParameterException("Unknown value for Priority enum.");
			}
		}
	}
	
	protected String name;
	protected Priority priority;
	protected int category;
	protected int position;
	
	protected int year;
	protected int month;
	protected int day;
	
	protected int currentReward;

	protected Activity activity;
	
	public Task(Activity activity, String name, Priority priority) {
		this.activity = activity;
		this.name = name;
		this.priority = priority;
	}
	
	
	
	/* ACTIONS ********************************************************************************/
	
	public void changeTextDialog() {
		TextDialog d = new ChangeTaskTextDialog(activity, this);
		d.setDefaultValue(name);
		d.show();
	}
	
	public void changeText(String text) {
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
	}
	
	public void changePriorityDialog() {
		
	}
	
	public void changePriority() {
		Toast.makeText(activity, "change priority of " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void changeCategoryDialog() {
		
	}
	
	public void changeCategory() {
		Toast.makeText(activity, "change category of " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void removeDialog() {
		new RemoveTaskDialog(activity, this).show();
	}
	
	public void remove() {
		Toast.makeText(activity, "remove " + getName(), Toast.LENGTH_SHORT).show();
	}

	public void changeDeadlineDialog() {
		new DateDialog(activity, this, year, month, day).show();
	}
	
	public void changeDeadline(int year, int month, int day) {
		Toast.makeText(activity, "new deadline is " + String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day), Toast.LENGTH_SHORT).show();
	}

	public void complete() {
		Toast.makeText(activity, "complete " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void uncomplete() {
		Toast.makeText(activity, "uncomplete " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	
	
	/* GETTERS & SETTERS **********************************************************************/
	
	public int getCurrentReward() {
		// TODO mockup
		return 10 * (new Random().nextInt(1000));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return this.position;
	}
}
