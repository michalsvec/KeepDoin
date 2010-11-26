package cz.vutbr.fit.tam.and10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class GameModel {

	private static String serverURL = "http://todogame.michalsvec.cz/api/";
	

	private enum RESTMethods { GET, POST, DELETE, UPDATE} 
	
	
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
		String requestURL = serverURL + "user/" + user_id;
		json = processHttpRequest(requestURL, RESTMethods.GET, null);
		return json;
	}
	
	
	
	public JSONObject getFriendsList() throws ClientProtocolException, IOException, JSONException {
		JSONObject json = new JSONObject();
		String requestURL = serverURL + "users/";
		json = processHttpRequest(requestURL, RESTMethods.GET, null);
		return json;
		
	} 



	/**
	 * Process the GET request and return JSON Object
	 * 
	 * @author misa
	 * @source http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
	 * @source http://www.androidsnippets.org/snippets/36/index.html
	 * @param url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException 
	 */
	private static JSONObject processHttpRequest(String url, RESTMethods type, List<NameValuePair> params) throws ClientProtocolException, IOException, JSONException {
		Log.i("KeepDoin", "processHttpRequest("+url+")");
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response;
		int status;

		// TODO: write next REST methods, when needed
		if(type == RESTMethods.POST) {
			Log.i("KeepDoin", "RESTMethods.POST");
			HttpPost request = new HttpPost(url);
			if(params != null)
				request.setEntity(new UrlEncodedFormEntity(params));

			response = httpClient.execute(request);
			status = response.getStatusLine().getStatusCode();
		}
		//else if(type == RESTMethods.GET) {
		else {
			Log.i("KeepDoin", "RESTMethods.GET");
			HttpGet request = new HttpGet(url);

			response = httpClient.execute(request);
			status = response.getStatusLine().getStatusCode();
		}


		// we assume that the response body contains the error message
		if (status == HttpStatus.SC_OK) {
			Log.i("KeepDoin", "HttpStatus == OK");
			
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				Log.i("KeepDoin", "Entity == OK");

				// A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("ToDoGame", result);
 
                // A Simple JSONObject Creation
                JSONObject json=new JSONObject(result);
                Log.i("TodoGame","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

                // Closing the input stream will trigger connection release
                instream.close();

                return json;
				
			}
		}
		else {
			Log.e("KeepDoin", "HttpStatus != OK");
			return null;
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

 
 
    /**
     * Attempts to authenticate the user credentials on the server.
     * 
     * @param accountName google account stored in phone
     * @param type registration or login
     * @param handler The main UI thread's handler instance.
     * @param context The caller Activity's context
     * @return Thread The thread on which the network mOperations are executed.
     */
    public static Thread attemptAuth(final String accountName, final String type, final Handler handler, final Context context) {
    	Log.i("KeepDoin","attemptAuth()");
    	final Runnable runnable = new Runnable() {
            public void run() {
                authenticate(accountName, type, handler, context);
            }
        };
        // run on background thread.
        return GameModel.performOnBackgroundThread(runnable);
    }



    /**
     * Executes the network requests on a separate thread.
     * 
     * @param runnable The runnable instance containing network mOperations to
     *        be executed.
     */
    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }



    /**
     * Connects to the server, authenticates the provided username and password.
     * 
     * @param username The user's username
     * @param type     Registration/login
     * @param handler  The hander instance from the calling UI thread.
     * @param context  The context of the calling Activity.
     * @return boolean The boolean result indicating whether the user was
     *         successfully authenticated.
     */
    public static boolean authenticate(String accountName, String type, Handler handler, final Context context) {
    	Log.i("KeepDoin", "authenticate()");
    	
		JSONObject json = new JSONObject();
		RESTMethods method; 

		// type of request - either login or registration
		String typeParam = "login";
		//List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		
		try {
			if(type.equals("registration")) {
				method = RESTMethods.POST;
				typeParam = "registration";
				Log.i("KeepDoin", "type: POST");
			}
			else {
				method = RESTMethods.GET;
				typeParam = "login";
				Log.i("KeepDoin", "type: GET");
			}

			// params are for future use
			//params.add(new BasicNameValuePair("email", accountName));
			String requestURL = serverURL + typeParam +"/"+accountName;
			Log.d("KeepDoin", "requestUrl:"+requestURL);

			json = processHttpRequest(requestURL, method, null);
		} catch (ClientProtocolException e) {
			Log.e("KeepDoin", "ClientProtocolException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("KeepDoin", " IOException");
			e.printStackTrace();
		} catch (JSONException e) {
			Log.e("KeepDoin", " JSONException");
			e.printStackTrace();
		}

		Log.i("ToDoGame", "authenticate json loaded");
		Log.i("KeepDoin", json.toString());
		
		// return from thread
		if(json != null) {
			Log.i("KeepDoin", "auth json okay");
			
			boolean status = false;
			try {
				status = json.getBoolean("status");
			} catch (JSONException e1) {
				Log.e("KeepDoin", " JSONException status reading");
				e1.printStackTrace();
			}
			
			if(status)
				sendResult(true, handler, context);
			else
				sendResult(false, handler, context);
		}
		else {
			Log.e("KeepDoin", "auth json null");
			sendResult(false, handler, context);
		}

		return false;
    }



    /**
     * Sends the authentication response from server back to the caller main UI
     * thread through its handler.
     * 
     * @param result The boolean holding authentication result
     * @param handler The main UI thread's handler instance.
     * @param context The caller Activity's context.
     */
    private static void sendResult(final Boolean result, final Handler handler, final Context context) {
    	Log.i("ToDoGame", "sendResult()");
    	
        if (handler == null || context == null) {
            return;
        }
        handler.post(new Runnable() {
            public void run() {
                ((KeepDoin) context).onAuthenticationResult(result);
            }
        });
    }
    
}
