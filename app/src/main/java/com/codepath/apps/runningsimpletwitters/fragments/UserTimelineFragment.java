package com.codepath.apps.runningsimpletwitters.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.codepath.apps.runningsimpletwitters.Listener.EndlessScrollListener;
import com.codepath.apps.runningsimpletwitters.R;
import com.codepath.apps.runningsimpletwitters.TwitterApplication;
import com.codepath.apps.runningsimpletwitters.TwitterClient;
import com.codepath.apps.runningsimpletwitters.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserTimelineFragment extends TweetsListFragment{

    private TwitterClient client;

    private long sinceId = 1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeLine(sinceId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        //get Unlimite
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                sinceId += 1;
                populateTimeLine(sinceId);

            }
        });
        return v;
    }


    public static UserTimelineFragment newInstance(String screen_name){
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    private void populateTimeLine(long pagingId){
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess ( int statusCode, Header[] headers, JSONArray json){
                // Log.d("DEBUG", json.toString());
                addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, Throwable
            throwable, JSONObject errorResponse){
                throwable.printStackTrace();
                // Log.d("DEBUG", errorResponse.toString());
            }
        }, pagingId);

    }
}
