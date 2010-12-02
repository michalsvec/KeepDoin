package cz.vutbr.fit.tam.and10.activities;

import android.app.Activity;
import android.os.Bundle;
import cz.vutbr.fit.tam.and10.task.Categories;

public class NowTab extends Activity {
	
	private Categories taskList;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskList = new Categories(this);
        setContentView(taskList.getView());
    }
    
//    @Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//    	tasks.onCreateContextMenu(menu, v, menuInfo);
//	}
//    
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//		return tasks.onContextItemSelected(item);
//    }
}