package cz.vutbr.fit.tam.and10.task;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;
import cz.vutbr.fit.tam.and10.dialogs.ChangeCategoryDialog;
import cz.vutbr.fit.tam.and10.dialogs.ChangePriorityDialog;
import cz.vutbr.fit.tam.and10.dialogs.ChangeTaskTextDialog;
import cz.vutbr.fit.tam.and10.dialogs.DateDialog;
import cz.vutbr.fit.tam.and10.dialogs.RemoveTaskDialog;
import cz.vutbr.fit.tam.and10.dialogs.TextDialog;

public class Task {

	public enum Priority {
		LOW(0), MEDIUM(1), HIGH(2);
	
		private int value;
		
		private Priority(int value) {
			this.value = value;
		}
		
		public int getColor() {
			switch (this.value) {
			case 0:
				return R.color.task_priority_low;
			case 1:
				return R.color.task_priority_medium;
			case 2:
				return R.color.task_priority_high;
			default:
				throw new InvalidParameterException("Unknown value for Priority enum.");
			}
		}
	}
	
	protected String name;
	protected Priority priority;
	protected int categoryId;
	protected Date deadline;
	
	protected int currentReward;
	protected int currentLikes;

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
		new ChangePriorityDialog(activity, this).show();
	}
	
	public void changePriority(int priority) {
		Toast.makeText(activity, "change priority to " + Priority.values()[priority], Toast.LENGTH_SHORT).show();
	}
	
	public void changeCategoryDialog() {
		new ChangeCategoryDialog(activity, this).show();
	}
	
	public void changeCategory(Category newCategory) {
		Toast.makeText(activity, "change category to " + newCategory.getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void removeDialog() {
		new RemoveTaskDialog(activity, this).show();
	}
	
	public void remove() {
		Toast.makeText(activity, "remove " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void like() {
		Toast.makeText(activity, "like " + getName(), Toast.LENGTH_SHORT).show();
	}

	public void changeDeadlineDialog() {
		new DateDialog(activity, this, deadline).show();
	}
	
	public void changeDeadline(Date newDeadline) {
		Toast.makeText(activity, "new deadline is " + DateFormat.getDateInstance().format(newDeadline), Toast.LENGTH_SHORT).show();
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
	
	public int getCurrentLikes() {
		// TODO mockup
		return (new Random().nextInt(20));
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
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public Date getDeadline() {
		// TODO mockup
		if (new Random().nextInt(10) >= 5) {
			return null;
		}
		return new Date(111, new Random().nextInt(12), new Random().nextInt(28) + 1);
	}
	
	public Boolean isLikedByCurrentUser() {
		// TODO mockup
		return (new Random().nextInt(10) >= 5);
	}



	public boolean getIsDone() {
		// TODO: fix
		return true;
	}
}
