package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import cz.vutbr.fit.tam.and10.MainMenu;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.category.Category;
import cz.vutbr.fit.tam.and10.category.CategoryAdapter;

public class ManageCategories extends Activity implements AccountInfoHolder {
	
	private String accountName;
	private int accountId;
	
	private CategoryAdapter adapter;
	private View view;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = createAdapter();
        setContentView(getView());
    }
    
    public View getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(this);
			view = (View) inflater.inflate(R.layout.categories, null);

			ListView list = (ListView)view.findViewById(R.id.categories);
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> av, View v, int position, long id) {
	        		Category c = (Category)adapter.getItem(position);
	        		c.changeTextDialog();
	        	}
	        });
	        registerForContextMenu(list);
	        
	        ImageButton addTask = (ImageButton)view.findViewById(R.id.header_add);
			addTask.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					createCategory();
				}
			});
	        
	        TextView header = (TextView)view.findViewById(R.id.header_name);
	        header.setText(R.string.categories_header);
		}
		return view;
	}
    
    public ListView getListView() {
    	return (ListView)getView().findViewById(R.id.categories);
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
			category.changeTextDialog();
			return true;
		case R.id.context_menu_move_up:
			category.moveUp();
			return true;
		case R.id.context_menu_move_down:
			category.moveDown();
			return true;
		case R.id.context_menu_remove:
			category.removeDialog();
			return true;
		}
		return false;
	}
	
	public void createCategory() {
		Toast.makeText(this, "create new category", Toast.LENGTH_SHORT).show();
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