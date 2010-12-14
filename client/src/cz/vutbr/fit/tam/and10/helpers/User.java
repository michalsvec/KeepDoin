package cz.vutbr.fit.tam.and10.helpers;

import java.io.IOException;

import android.content.Context;


public class User extends Object {

	private int userId;
	private int rankId;
	private String real_name;
	private String email;
	private String avatar;
	
	
	
	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public User(int id) {
		this.userId = id;
	}

	// piece of crap, but no time!! :(
	// @author: misa
	public void loadData(Context c) throws IOException {
		
		SQLDriver db = new SQLDriver(c);
		User tmp = null;
		tmp = db.getUser(this.userId);

		if(tmp == null) {
			db.closeDB();
			throw new IOException();
		}
		
		this.rankId = tmp.getId();
		this.real_name = tmp.getName();
		this.email = tmp.getEmail();
		
		db.closeDB();
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
	
	public void setRank(int rank) {
		this.rankId = rank; 
	}
}
