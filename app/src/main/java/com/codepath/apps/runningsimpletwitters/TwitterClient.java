package com.codepath.apps.runningsimpletwitters;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "3J0LapKumluNpet06gqFCFj7g";       // Change this
	public static final String REST_CONSUMER_SECRET = "iJIyfSZX4lJAh703NqKPT1Ka5F4PLKFdrNOqpzOlHZ2VSd10wI"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://SimpleTwitterClient_RunningChao"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

//	// CHANGE THIS
//	// DEFINE METHODS for different API endpoints here
//	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
//		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
//		// Can specify query string params directly or through RequestParams.
//		RequestParams params = new RequestParams();
//		params.put("format", "json");
//		client.get(apiUrl, params, handler);
//	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void getUserProfile(AsyncHttpResponseHandler handler, String screenName, Long userId){
		String apiUrl = "https://api.twitter.com/1.1/users/show.json";
		RequestParams params = new RequestParams();
		params.put("user_id ",userId);
		params.put("screen_name", screenName);
		getClient().get(apiUrl, params, handler);
	}

	public void getHomeTimeline(AsyncHttpResponseHandler handler, long sinceId){
		String apiUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";
		RequestParams params = new RequestParams();
		params.put("count", 25);
		//params.put("since_id", 1);
		params.put("page",sinceId);
		getClient().get(apiUrl, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler){
		String apiUrl = "https://api.twitter.com/1.1/account/verify_credentials.json";
		getClient().get(apiUrl, null, handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler, Long page){
		String apiUrl = "https://api.twitter.com/1.1/statuses/user_timeline.json";
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("screen_name", screenName);
		params.put("page", page);
		getClient().get(apiUrl, params, handler);
	}

	public void postNewTwitter(AsyncHttpResponseHandler handler, String content){
		String apiUrl = "https://api.twitter.com/1.1/statuses/update.json";
		RequestParams params = new RequestParams();
		params.put("status", content);
		//getClient().get(apiUrl, params, handler);
		getClient().post(apiUrl, params, handler);
	}

	public void getMentionsTimeLine(AsyncHttpResponseHandler handler, long page){
		String apiUrl = "https://api.twitter.com/1.1/statuses/mentions_timeline.json";
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("page",page);
		getClient().get(apiUrl, params, handler);
	}
}