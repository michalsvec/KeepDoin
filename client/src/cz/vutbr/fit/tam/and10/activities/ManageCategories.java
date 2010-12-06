package cz.vutbr.fit.tam.and10.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;
import cz.vutbr.fit.tam.and10.category.CategoryAdapter;

public class ManageCategories extends ListActivity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	
	private CategoryAdapter adapter;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final CategoryAdapter a = createAdapter();
        setListAdapter(a);
        adapter = a;
        
        ListView list = getListView();
        list.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> av, View v, int position, long id) {
        		Category c = (Category)a.getItem(position);
        		c.changeText();
        	}
        });
        registerForContextMenu(list);
    }
    
    public CategoryAdapter createAdapter() {
    	// TODO mockup
    	Category[] testValues = new Category[] {
    		new Category(this, "Práce"),
    		new Category(this, "Škola"),
    		new Category(this, "Doma")
		};
    	CategoryAdapter adapter = new CategoryAdapter(this, testValues);
    	return adapter;
    }
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_context_menu, menu);
		menu.setHeaderTitle(R.string.context_menu_title);
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Category category = (Category)adapter.getItem(info.position);
    	
    	switch (item.getItemId()) {
		case R.id.context_menu_change_text:
			category.changeText();
			return true;
		case R.id.context_menu_move_up:
			category.moveUp();
			return true;
		case R.id.context_menu_move_down:
			category.moveDown();
			return true;
		case R.id.context_menu_remove:
			category.remove();
			return true;
		}
		return false;
	}
	
	private MainMenu menu;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = new MainMenu(this, R.menu.main_menu, menu);
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