package cz.vutbr.fit.tam.and10.category;

import android.app.Activity;
import android.widget.Toast;

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
	
	public void createTask() {
		Toast.makeText(activity, "add task in category " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void changeText() {
		Toast.makeText(activity, "change text of " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void moveUp() {
		Toast.makeText(activity, "move up " + getName(), Toast.LENGTH_SHORT).show();
	}
	
	public void moveDown() {
		Toast.makeText(activity, "move down " + getName(), Toast.LENGTH_SHORT).show();
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
