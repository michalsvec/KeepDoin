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
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class GameModel {

	private static String serverURL = "http://todogame.michalsvec.cz/api/";
	
	
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
		json = this.processGetRequest(requestURL);
		return json;
	}
	
	
	
	public JSONObject getFriendsList() throws ClientProtocolException, IOException, JSONException {
		JSONObject json = new JSONObject();
		String requestURL = serverURL + "users/";
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
	private static JSONObject processGetRequest(String url) throws ClientProtocolException, IOException, JSONException {
		
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
		else {
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
     * @param username The user's username
     * @param password The user's password to be authenticated
     * @param handler The main UI thread's handler instance.
     * @param context The caller Activity's context
     * @return Thread The thread on which the network mOperations are executed.
     */
    public static Thread attemptAuth(final String username, final String password, final Handler handler, final Context context) {
        
    	final Runnable runnable = new Runnable() {
            public void run() {
                authenticate(username, password, handler, context);
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
     * @param password The user's password
     * @param handler The hander instance from the calling UI thread.
     * @param context The context of the calling Activity.
     * @return boolean The boolean result indicating whether the user was
     *         successfully authenticated.
     */
    public static boolean authenticate(String username, String password, Handler handler, final Context context) {

		JSONObject json = new JSONObject();
		String requestURL = serverURL + "login/?gmail="+username+"&password="+password;
		try {
			json = processGetRequest(requestURL);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("ToDoGame", "json loaded");
		sendResult(true, handler, context);
		
		
		return false;

//		
//        final HttpResponse resp;
//
//        final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair(PARAM_USERNAME, username));
//        params.add(new BasicNameValuePair(PARAM_PASSWORD, password));
//        HttpEntity entity = null;
//        try {
//            entity = new UrlEncodedFormEntity(params);
//        } catch (final UnsupportedEncodingException e) {
//            // this should never happen.
//            throw new AssertionError(e);
//        }
//        final HttpPost post = new HttpPost(AUTH_URI);
//        post.addHeader(entity.getContentType());
//        post.setEntity(entity);
//        maybeCreateHttpClient();
//
//        try {
//            resp = mHttpClient.execute(post);
//            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                if (Log.isLoggable(TAG, Log.VERBOSE)) {
//                    Log.v(TAG, "Successful authentication");
//                }
//                sendResult(true, handler, context);
//                return true;
//            } else {
//                if (Log.isLoggable(TAG, Log.VERBOSE)) {
//                    Log.v(TAG, "Error authenticating" + resp.getStatusLine());
//                }
//                sendResult(false, handler, context);
//                return false;
//            }
//        } catch (final IOException e) {
//            if (Log.isLoggable(TAG, Log.VERBOSE)) {
//                Log.v(TAG, "IOException when getting authtoken", e);
//            }
//            sendResult(false, handler, context);
//            return false;
//        } finally {
//            if (Log.isLoggable(TAG, Log.VERBOSE)) {
//                Log.v(TAG, "getAuthtoken completing");
//            }
//        }
    }



    /**
     * Sends the authentication response from server back to the caller main UI
     * thread through its handler.
     * 
     * @param result The boolean holding authentication result
     * @param handler The main UI thread's handler instance.
     * @param context The caller Activity's context.
     */
    private static void sendResult(final Boolean result, final Handler handler,
        final Context context) {
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
