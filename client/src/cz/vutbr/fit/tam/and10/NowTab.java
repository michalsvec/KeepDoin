package cz.vutbr.fit.tam.and10;

import android.app.Activity;
import android.os.Bundle;
import cz.vutbr.fit.tam.and10.task.Tasks;

public class NowTab extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tasks tasks = new Tasks(this);
        setContentView(tasks.getView());
    }
}