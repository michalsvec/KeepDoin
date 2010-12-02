package cz.vutbr.fit.tam.and10.task;

import cz.vutbr.fit.tam.and10.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


public class Categories {

	protected CategoriesAdapter adapter;
	protected View view;
	protected Activity activity;
	
	public Categories(Activity a) {
		activity = a;
		adapter = new CategoriesAdapter(a);
		
		// TODO mockup
		Category c = new Category(a, "Práce", 0);
		c.add(new Task(a, "Zalít v práci kytičky", Task.Priority.HIGH));
		c.add(new Task(a, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		c.add(new Task(a, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
		adapter.add(c);
		
		c = new Category(a, "Škola", 1);
		c.add(new Task(a, "Zalít ve škole kytičky", Task.Priority.HIGH));
		c.add(new Task(a, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		c.add(new Task(a, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
		adapter.add(c);
		
		c = new Category(a, "Doma", 2);
		c.add(new Task(a, "Zalít doma kytičky", Task.Priority.HIGH));
		c.add(new Task(a, "Zavolat babičce a dědovi", Task.Priority.MEDIUM));
		c.add(new Task(a, "Koupit lístek na Karla Plíhala", Task.Priority.LOW));
		adapter.add(c);
	}
	
	public View getView() {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			view = (View) inflater.inflate(R.layout.categories, null);

			ListView list = (ListView)view.findViewById(R.id.categories);
			list.setAdapter(adapter);
		}
		return view;
	}
	
	public Category getItem(int position) {
		return adapter.getItem(position);
	}
	
	public ListView getListView() {
		return (ListView)getView().findViewById(R.id.categories);
	}
	
}