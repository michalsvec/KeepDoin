package cz.vutbr.fit.tam.and10.category;

import android.app.Activity;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.dialogs.AddTaskDialog;
import cz.vutbr.fit.tam.and10.dialogs.ChangeCategoryTextDialog;
import cz.vutbr.fit.tam.and10.dialogs.RemoveCategoryDialog;
import cz.vutbr.fit.tam.and10.dialogs.TextDialog;

public class Category {

	protected Activity activity;
	
	protected String name;
	protected int order;

//	private static ArrayList<Category> categories;
	
	public Category(Activity a, String name) {
		this.activity = a;
		this.name = name;
//		this.order = categories.size();
//		categories.add(order, this);
	}
	
	public void createTaskDialog() {
		new AddTaskDialog(activity, this).show();
	}
	
	public void createTask(String text) {
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
	}
	
	public void changeTextDialog() {
		TextDialog d = new ChangeCategoryTextDialog(activity, this);
		d.setDefaultValue(name);
		d.show();
	}
	
	public void changeText(String text) {
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
	}
	
	public void moveUp() {
		Toast.makeText(activity, "move up " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void moveDown() {
		Toast.makeText(activity, "move down " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void removeDialog() {
		new RemoveCategoryDialog(activity, this).show();
	}
	
	public void remove() {
		Toast.makeText(activity, "remove " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public String getName() {
		return this.name;
	}
	
//	public int getOrder() {
//		return order;
//	}

	public String toString() {
		return getName();
	}
}
