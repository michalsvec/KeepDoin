package cz.vutbr.fit.tam.and10;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private int userId;
	private int rank_id;
	private String real_name;
	private String email;
	
	
	
	public User(int id) {
		
		GameModel model = new GameModel();
		JSONObject json = null;

		try {
			json = model.getUserinfo(1);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		this.userId = id;
		try {
			this.rank_id = json.getInt("rank_id");
			this.real_name = json.getString("real_name");
			this.email = json.getString("email");
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getName() {
		return real_name;
	}

	
	public String getEmail() {
		return email;
	}
	
	
	public String getRank() {
		return Integer.toString(rank_id);
	}
	
	
	
}
