package cz.vutbr.fit.tam.and10.helpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cz.vutbr.fit.tam.and10.KDGlobal;
import cz.vutbr.fit.tam.and10.KeepDoin;
import cz.vutbr.fit.tam.and10.activities.FriendsTab;

/**
 * @link: 
 *        http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android
 *        -applications/
 * @author misa
 * 
 */
public class SQLDriver extends SQLiteOpenHelper {

	public SQLiteDatabase db;
	// The Android's default system path of your application database.
	private String DB_PATH = null;
	private static String DB_NAME = "keepdoindb";
	private final Context myContext;

	public SQLDriver(Context context) throws IOException {
		super(context, DB_NAME, null, 1);
		Log.i("KeepDoin", "SQLDriver()");
		this.myContext = context;

		DB_PATH = "/data/data/"+context.getApplicationContext().getPackageName()+"/databases/";

		// temporarily removing database
//		File file = new File(DB_PATH+DB_NAME);
//		if(file.exists()) {
//			Log.i("KeepDoin", "Database exists");
//			file.delete();
//		}
			
		this.createDataBase();
		this.openDataBase();
		
		
		// log files list
		String list[] = myContext.fileList();
		for(int i=0; i < list.length ; i++ ) {
			Log.i("KeepDoin", "file list: "+ list[i]);
		}
	}



	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}



    public void openDataBase() throws SQLException{
    	Log.i("KeepDoin", "openDataBase()");
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
        Log.i("KeepDoin", "path: "+myPath);
    	this.db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }



	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		Log.i("KeepDoin", "createDataBase()");
		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
			Log.i("KeepDoin", "database exists");
		} else {
			Log.i("KeepDoin", "creating database");
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			checkDB = null;
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		Log.i("KeepDoin", "CopyDatabase()");
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}



	/**
	 * Runs custom sql query
	 * 
	 * @author misa
	 * @param sql
	 */
	public void execSQL(String sql) {
		Log.i("KeepDoin", "execSQL()");
		Log.i("KeepDoin", "sql: "+sql);
		db.execSQL(sql);
	}



	public void insertFriend(JSONObject user) {
		Log.i("KeepDoin", "insertFriend()");
		try {
			int id = user.getInt("id");
			String name = user.getString("name");
			String email = user.getString("email");
			String query = "INSERT INTO friends (id, name, email) VALUES ('"+id+"', '"+name+"', '"+email+"');";
			this.execSQL(query);
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			Log.i("KeepDoin", "SQLException()");
			e.printStackTrace();
		}

		return;
	}



	public ArrayList<User> getFriends() {
		Log.i("KeepDoin", "getFriends()");
		KDGlobal global = (KDGlobal) ((FriendsTab) myContext).getApplication();

		ArrayList<User> friends = new ArrayList<User>();
		Cursor cur = null;

		cur = db.rawQuery("SELECT * FROM friends", new String [] {});

		cur.moveToFirst();
		Log.i("KeepDoin", "accoutnId: "+global.accountId);
		while (cur.isAfterLast() == false) {
			Log.i("KeepDoin", "id:"+cur.getInt(cur.getColumnIndex("id")));

			User user = new User(cur.getInt(cur.getColumnIndex("id")));
			
			user.setName(cur.getString(cur.getColumnIndex("name")));
			user.setEmail(cur.getString(cur.getColumnIndex("email")));

			Log.i("KeepDoin", "user: "+user.getName());
			Log.i("KeepDoin", "user: "+user.getEmail());
			if(user.getId() != global.accountId) {
				friends.add(user);
			}

			cur.moveToNext();
		}
		cur.close();
		return friends;
	}



	public User getUser(int id) {
		Log.i("KeepDoin", "getUser()");

		Cursor cur = db.rawQuery("SELECT * FROM friends WHERE id="+id, new String [] {});
		cur.moveToFirst();
		while (cur.isAfterLast() == false) {
			Log.i("KeepDoin", "id:"+cur.getInt(cur.getColumnIndex("id")));

			User user = new User(cur.getInt(cur.getColumnIndex("id")));
			
			user.setName(cur.getString(cur.getColumnIndex("name")));
			user.setEmail(cur.getString(cur.getColumnIndex("email")));

			return user;
		}
		return null;
	} 



	public void truncateTable(String table) {
		String query = "DELETE FROM "+table+";";
		this.execSQL(query);
	}
}
