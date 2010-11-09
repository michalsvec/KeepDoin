package cz.vutbr.fit.tam.and10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GameModel {

	private String serverURL = "http://todogame.michalsvec.cz/api/";
	
	
	/**
	 * Returns informations about user
	 * 
	 * @param user_id
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JSONException 
	 */
	public JSONObject getUserinfo(int user_id) throws ClientProtocolException, IOException, JSONException {
		JSONObject json = new JSONObject();
		String requestURL = this.serverURL + "user/" + user_id;
		json = this.processGetRequest(requestURL);
		return json;
	}
	
	
	
	public JSONObject getFriendsList() throws ClientProtocolException, IOException, JSONException {

		JSONObject json = new JSONObject();
		String requestURL = this.serverURL + "users/";
		json = this.processGetRequest(requestURL);
		return json;
		
	} 



	/**
	 * Process the GET request and return JSON Object
	 * 
	 * @author misa
	 * @source http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException 
	 */
	private JSONObject processGetRequest(String url) throws ClientProtocolException, IOException, JSONException {
		
		HttpClient httpClient = new DefaultHttpClient();

		HttpGet request = new HttpGet(url);
		HttpResponse response = httpClient.execute(request);
		int status = response.getStatusLine().getStatusCode();
		
		// we assume that the response body contains the error message
		if (status == HttpStatus.SC_OK) {
			
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {

				// A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("ToDoGame",result);
 
                // A Simple JSONObject Creation
                JSONObject json=new JSONObject(result);
                Log.i("TodoGame","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

                // Closing the input stream will trigger connection release
                instream.close();
                return json;
				
			}
		} 
		return null;
	}


	/**
	 * http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
	 * @param is
	 * @return
	 */
	 private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
}
