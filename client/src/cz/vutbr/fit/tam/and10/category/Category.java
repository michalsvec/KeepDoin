package cz.vutbr.fit.tam.and10.category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import cz.vutbr.fit.tam.and10.dialogs.AddTaskDialog;
import cz.vutbr.fit.tam.and10.dialogs.ChangeCategoryTextDialog;
import cz.vutbr.fit.tam.and10.dialogs.RemoveCategoryDialog;
import cz.vutbr.fit.tam.and10.dialogs.TextDialog;
import cz.vutbr.fit.tam.and10.helpers.SQLDriver;
import cz.vutbr.fit.tam.and10.task.Task;

public class Category {

	protected Activity activity;
	
	protected int id;
	
	protected String name;
	protected int order;
	protected List<Task> tasks = new ArrayList<Task>();

	public Category(Activity a, String name) {
		this.activity = a;
		this.name = name;
	}
	
	public void createTaskDialog() {
		new AddTaskDialog(activity, this).show();
	}
	
	public void createTask(String text) {
		Task task = new Task(activity, text, Task.Priority.MEDIUM);
		task.setCategoryId(id);

		try {
			new SQLDriver(activity).saveTask(task);
		} catch (IOException e) {
			Toast.makeText(activity, "Unable to save new task.", Toast.LENGTH_SHORT).show();
			Log.e("KeepDoin", "SaveTask()", e);
		}
	}
	
	public void changeTextDialog() {
		TextDialog d = new ChangeCategoryTextDialog(activity, this);
		d.setDefaultValue(name);
		d.show();
	}
	
	public void addTasks(List<Task> tasks) {
		this.tasks.addAll(tasks);
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getOrder() {
		return order;
	}

	public String toString() {
		return getName();
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
}
