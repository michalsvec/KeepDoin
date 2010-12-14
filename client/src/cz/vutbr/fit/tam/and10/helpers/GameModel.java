package cz.vutbr.fit.tam.and10.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.math.BigInteger;

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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import cz.vutbr.fit.tam.and10.KeepDoin;
import cz.vutbr.fit.tam.and10.helpers.Encryption;

public class GameModel {

	private static String serverURL = "";
	private static KeepDoin myContext = null;

	private enum RESTMethods { GET, POST, DELETE, UPDATE } 

	private static HttpClient httpClient = new DefaultHttpClient();

	
	private static JSONObject processHttpRequest(String url, RESTMethods type, List<NameValuePair> params) throws ClientProtocolException, IOException, JSONException {
		Log.i("KeepDoin", "processHttpRequest("+url+")");
		
		HttpResponse response;
		Boolean encryptionOff = false;
		int status;

		if (params != null) {
			NameValuePair encryptionValuePair = new BasicNameValuePair("encryptionOff", "true");
			if (params.contains(encryptionValuePair)) {
				Log.i("KeepDoin", "encryptionOff in params!");
				params.remove(params.indexOf(encryptionValuePair));
				encryptionOff = true;
			}
		}
		Log.i("KeepDoin", "encryptionOff: " + encryptionOff);
		
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
                Log.i("KeepDoin", result);
                
                if (!encryptionOff) {
                	String keyString = myContext.getSecret();
                	result = Encryption.decrypt(result, keyString);
                }
                Log.i("KeepDoin", "result: " + result);
 
                // A Simple JSONObject Creation
                JSONObject json = new JSONObject(result);
                Log.i("KeepDoin","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

                // Closing the input stream will trigger connection release
                instream.close();  

                return json;
				
			}
		}
		else {
			Log.e("KeepDoin", "HttpStatus != OK");
			throw new ClientProtocolException("404");
		}
		return null;
	}

	 private static String convertStreamToString(InputStream is) {
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

    public static Thread attemptAuth(final String accountName, final Handler handler, final Context context) {
    	Log.i("KeepDoin","attemptAuth()");
    	final Runnable runnable = new Runnable() {
            public void run() {
                authenticationProcess(accountName, handler, context);
            }
        };

        return GameModel.performOnBackgroundThread(runnable);
    }

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

    public static void authenticationProcess(String accountName, Handler handler, final Context context) {
    	Log.i("KeepDoin", "authenticate()");
    	myContext = (KeepDoin) context;
    	serverURL = myContext.getRemoteAPIUrl();
		
    	Boolean registred = myContext.getRegistred();
    	if (!registred)
    		registration(serverURL, accountName, myContext);
    	
    	String auth = login(serverURL, accountName, myContext);
		if (auth != null) {
			if (!authenticate(serverURL, accountName, myContext, auth)) {
				sendResult(false, handler, context);
				return;
			}
		} else {
			sendResult(false, handler, context);
			return;
		}
		
		sendResult(true, handler, context);
    }

    private static Boolean registration(String serverURL, String accountName, final KeepDoin myContext)
    {
    	RESTMethods method;
    	String requestUrl;
    	JSONObject json = new JSONObject();
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		method = RESTMethods.GET;
		params.add(new BasicNameValuePair("encryptionOff", "true"));
		requestUrl = serverURL + "registration" + "/" + accountName;
		
		BigInteger myInteger, bobInteger, secret;
		myInteger = new BigInteger(20, new Random());
		
		requestUrl += "?alice=" + Encryption.dhGetToSend(myInteger).toString();
		
		try {
			json = processHttpRequest(requestUrl, method, params);
		} catch (ClientProtocolException e) {
			Log.e("KeepDoin", "ClientProtocolException");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.e("KeepDoin", "IOException");
			e.printStackTrace();
			return false;
		} catch (JSONException e) {
			Log.e("KeepDoin", "JSONException");
			e.printStackTrace();
			return false;
		}
		
		boolean status = false;
		int id = 0;
		
		try {
			status = json.getBoolean("status");
			if (status) {
				id = json.getInt("id");
				myContext.setAccountId(id);
				
				bobInteger = new BigInteger(json.getString("bob"));
				secret = Encryption.dhGetSecret(myInteger, bobInteger);
				myContext.setSecret(secret.toString());
			} else {
				return false;
			}
		} catch (JSONException e1) {
			Log.e("KeepDoin", "JSONException");
			e1.printStackTrace();
			return false;
		}
		
		return true;
    }

    private static String login(String serverURL, String accountName, final KeepDoin myContext)
    {
    	RESTMethods method;
    	String requestUrl;
    	JSONObject json = new JSONObject();
		
		method = RESTMethods.GET;
		requestUrl = serverURL + "login" + "/" + accountName;
		
		try {
			json = processHttpRequest(requestUrl, method, null);
		} catch (ClientProtocolException e) {
			Log.e("KeepDoin", "ClientProtocolException");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e("KeepDoin", "IOException");
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			Log.e("KeepDoin", "JSONException");
			e.printStackTrace();
			return null;
		}
		
		boolean status = false;
		String auth = null;
		int id = 0;
		
		try {
			status = json.getBoolean("status");
			if (status) {
				auth = json.getString("auth");
				id = json.getInt("id");
				myContext.setAccountId(id);
			} else {
				return null;
			}
		} catch (JSONException e1) {
			Log.e("KeepDoin", "JSONException");
			e1.printStackTrace();
			return null;
		}
		
		return auth;
    }

    private static Boolean authenticate(String serverURL, String accountName, final KeepDoin myContext, String auth)
    {
    	RESTMethods method;
    	String requestUrl;
    	JSONObject json = new JSONObject();
		
		method = RESTMethods.GET;
		requestUrl = serverURL + "authenticate" + "/" + accountName;
		
		BigInteger authNum = new BigInteger(auth);
		authNum = authNum.multiply(new BigInteger("2"));
		
		requestUrl += "?auth=" + authNum.toString();
		
		try {
			json = processHttpRequest(requestUrl, method, null);
		} catch (ClientProtocolException e) {
			Log.e("KeepDoin", "ClientProtocolException");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.e("KeepDoin", "IOException");
			e.printStackTrace();
			return false;
		} catch (JSONException e) {
			Log.e("KeepDoin", "JSONException");
			e.printStackTrace();
			return false;
		}
		
		boolean status = false;
		
		try {
			status = json.getBoolean("status");
			if (status)
				return true;
		} catch (JSONException e1) {
			Log.e("KeepDoin", "JSONException");
			e1.printStackTrace();
		}
		
		return false;
    }
    
    private static void sendResult(final Boolean result, final Handler handler, final Context context) {
    	Log.i("KeepDoin", "sendResult()");
    	
        if (handler == null || context == null) {
            return;
        }
        handler.post(new Runnable() {
            public void run() {
                ((KeepDoin) context).onAuthenticationResult(result);
            }
        });
    }

	public static JSONObject getApiResult(int accountId, String apiType) throws ClientProtocolException, IOException, JSONException {
		JSONObject json = new JSONObject();
		String requestURL = serverURL + apiType+"/"+accountId;
		json = processHttpRequest(requestURL, RESTMethods.GET, null);
		return json;
	} 

	public static Boolean getFriendship(int accountId, String friendEmail) throws ClientProtocolException, IOException, JSONException {
		JSONObject json = new JSONObject();
		String requestURL = serverURL + "friendship/" + accountId + "?email=" + friendEmail;
		
		json = processHttpRequest(requestURL, RESTMethods.GET, null);
		
		Boolean status = false;
		try {
			status = json.getBoolean("status");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static void postFriendRequest(int user_id, String email) throws ClientProtocolException, IOException, JSONException {
		String requestURL = serverURL + "friendship/" + user_id;
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", email)); 
		
		processHttpRequest(requestURL, RESTMethods.POST, params);
		return;
	}
}
