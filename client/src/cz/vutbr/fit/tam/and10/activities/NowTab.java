package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;
import cz.vutbr.fit.tam.and10.task.Task;
import cz.vutbr.fit.tam.and10.task.Tasks;

public class NowTab extends Activity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	
	private Tasks tasks;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasks = new Tasks(this); // TODO TEST OF "INTERACTIVE VERSION" OF TASKS
        
        // TODO MOCKUP
        tasks.addCategory(new Category(this, "Práce"));
        Task[] items1 = {
        	new Task(this, "Zalít kytičky", Task.Priority.HIGH),
        	new Task(this, "Zavolat babičce a dědovi", Task.Priority.MEDIUM),
        	new Task(this, "Koupit lístek na Karla Plíhala", Task.Priority.LOW)
        };
        tasks.addTasks(items1);
        
        tasks.addCategory(new Category(this, "Škola"));
        Task[] items2 = {
        	new Task(this, "Nakrmit paní učitelku", Task.Priority.HIGH),
        	new Task(this, "Naučit se TAM", Task.Priority.LOW)
        };
        tasks.addTasks(items2);
		// END OF MOCKUP
        
        setContentView(tasks.getView());
    }
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	tasks.onCreateContextMenu(menu, v, menuInfo);
	}
    
    public boolean onContextItemSelected(MenuItem item) {
		return tasks.onContextItemSelected(item);
    }
    
    private MainMenu menu;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = new MainMenu(this, R.menu.main_menu_tasks, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	return this.menu.onOptionsItemSelected(item);
    }
    
    @Override
	public int getAccountId() {
		return accountId;
	}

	@Override
	public String getAccountName() {
		return accountName;
	}

	@Override
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}