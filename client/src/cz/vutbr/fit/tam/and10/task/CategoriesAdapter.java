package cz.vutbr.fit.tam.and10.task;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import cz.vutbr.fit.tam.and10.R;

public class CategoriesAdapter extends ArrayAdapter<Category> {

	protected Activity activity;
	
	public CategoriesAdapter(Activity activity) {
		super(activity, R.layout.category);
		this.activity = activity;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			v = inflater.inflate(R.layout.category, null);
		}
		
		final Category c = getItem(position);
		if (c != null) {
			c.prepareView(v);
			v.setTag(c);
		}
		return v;
	}
	
}
