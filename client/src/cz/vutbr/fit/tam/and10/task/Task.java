package cz.vutbr.fit.tam.and10.task;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.Random;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.R;

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
	
	public void changeText() {
		Toast.makeText(activity, "change text of " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void changePriority() {
		Toast.makeText(activity, "change priority of " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void changeCategory() {
		Toast.makeText(activity, "change category of " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void remove() {
		Toast.makeText(activity, "remove " + getName(), Toast.LENGTH_SHORT).show();
	}

	public void changeDeadline() {
		Toast.makeText(activity, "change deadline of " + getName(), Toast.LENGTH_SHORT).show();
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
