package cz.vutbr.fit.tam.and10.task;

import java.security.InvalidParameterException;
import java.util.Random;

import cz.vutbr.fit.tam.and10.R;

public class Task {

	public static final int CONTEXT_MENU_ID = 0;
	
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
	
	protected int year;
	protected int month;
	protected int day;
	
	protected int currentReward;
	
	public Task(String name, Priority priority) {
		this.name = name;
		this.priority = priority;
	}

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
	
}
