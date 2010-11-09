package cz.vutbr.fit.tam.and10;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private int userId;
	private int rankId;
	private String real_name;
	private String email;
	
	
	
	public User(int id) {
		this.userId = id;
	}

	
	public void loadData() {
		GameModel model = new GameModel();
		JSONObject json = null;

		try {
			json = model.getUserinfo(this.userId);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			this.rankId = json.getInt("rank_id");
			this.real_name = json.getString("real_name");
			this.email = json.getString("email");
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int getId() {
		return this.userId;
	}


	public String getName() {
		return real_name;
	}
	public void setName(String name) {
		this.real_name = name; 
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email; 
	}

	
	public String getRank() {
		return Integer.toString(rankId);
	}
	public void setName(int rank) {
		this.rankId = rank; 
	}
}
