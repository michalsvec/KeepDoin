package cz.vutbr.fit.tam.and10.category;

import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;

public class CategoryAdapter extends ArrayAdapter<Category> {

	protected Activity activity;
	
	public CategoryAdapter(Activity activity) {
		super(activity, android.R.layout.simple_list_item_1);
		this.activity = activity;
	}
	
	public CategoryAdapter(Activity activity, List<Category> categories) {
		super(activity, android.R.layout.simple_list_item_1, categories);
		this.activity = activity;
	}
	
	public CategoryAdapter(Activity activity, Category[] categories) {
		super(activity, android.R.layout.simple_list_item_1, categories);
		this.activity = activity;
	}
	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		return super.getView(position, convertView, parent);
//	}

}
