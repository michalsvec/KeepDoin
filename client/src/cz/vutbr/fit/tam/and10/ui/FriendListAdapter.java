package cz.vutbr.fit.tam.and10.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cz.vutbr.fit.tam.and10.R;
import cz.vutbr.fit.tam.and10.helpers.Gravatar;
import cz.vutbr.fit.tam.and10.helpers.SQLDriver;
import cz.vutbr.fit.tam.and10.helpers.User;

public class FriendListAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<User> friends = null;
	
    public FriendListAdapter(Context c, ArrayList<User> friends) {
        mContext = c;
        this.friends = friends; 
    }

    public int getCount() {
    	return this.friends.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void refreshFriends() throws IOException {
    	SQLDriver sql = new SQLDriver(mContext);
    	this.friends = sql.getFriends();
    	sql.closeDB();
    }
    
    
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	Log.i("KeepDoin", "getView()");
        View view;

        if (convertView == null) {  // if it's not recycled, initialize some attributes

            LayoutInflater li = LayoutInflater.from(mContext);
            view = li.inflate(R.layout.friendicon, null);

            // get first name
            TextView tv = (TextView)view.findViewById(R.id.icon_text);
            String[] nameTokens = this.friends.get(position).getName().split(" ");
            
			tv.setText(nameTokens[0]);

			Log.i("KeepDoin", nameTokens.toString());
			
			// seznam souboru
			String list[] = mContext.fileList();
			for(int i=0; i < list.length ; i++ ) {
				Log.i("KeepDoin", "file list: "+ list[i]);
			}

			Log.i("KeepDoin", nameTokens.toString());
			
			// search local storage for avatar
			String email = this.friends.get(position).getEmail();
			FileInputStream fos = null;
			ImageView iv = (ImageView)view.findViewById(R.id.icon_image);
			try {
				String hash = Gravatar.getGravatarHash(email);
				Log.i("KeepDoin", "opening: "+hash+" for email:"+email);
				fos = mContext.openFileInput(hash);
				iv.setImageBitmap(BitmapFactory.decodeStream(fos));
				fos.close();
			// if file is unavailable - loads gravatar default image
			} catch (FileNotFoundException e) {
				iv.setImageResource(R.drawable.gravatar_default);
			} catch (IOException e) {
				e.printStackTrace();
			} 
        } else {
        	view = (View) convertView;
        }

        return view;
    }

}
