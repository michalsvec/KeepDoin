package cz.vutbr.fit.tam.and10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseAdapter {
	private Context mContext;
	User friends[] = null;
	
    public FriendListAdapter(Context c, User friends[]) {
        mContext = c;
        this.friends = friends; 
    }

    public int getCount() {
   		return friends.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {  // if it's not recycled, initialize some attributes

            LayoutInflater li = LayoutInflater.from(mContext);
            view = li.inflate(R.layout.friendicon, null);

            TextView tv = (TextView)view.findViewById(R.id.icon_text);
            String[] nameTokens = this.friends[position].getName().split(" ");
			tv.setText(nameTokens[0]);

			ImageView iv = (ImageView)view.findViewById(R.id.icon_image);
			iv.setImageResource(mThumbIds[position]);
        } else {
        	view = (View) convertView;
        }

        return view;
    }


    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };

}
