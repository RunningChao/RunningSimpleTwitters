package com.codepath.apps.runningsimpletwitters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.runningsimpletwitters.fragments.HomeTimelineFragment;
import com.codepath.apps.runningsimpletwitters.fragments.MentionsTimelineFragment;
import com.codepath.apps.runningsimpletwitters.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class TimelineActivity extends ActionBarActivity {
    private TwitterClient client;
    private User loginUser;
    private final int REQUEST_CODE = 20;
    private SmartFragmentStatePagerAdapter smartAdapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getRestClient();
        //get LoginUser Data
        getLoingProfile();

        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);


        smartAdapt =  new TweetsPagerAdapter(getSupportFragmentManager());

        vpPager.setAdapter(smartAdapt);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent i = new Intent(TimelineActivity.this, PostTwitterActivity.class);
//            i.putExtra("userProfile", loginUser);
//            startActivityForResult(i, REQUEST_CODE);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK  && requestCode == REQUEST_CODE) {

            HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) smartAdapt.getRegisteredFragment(0);
            homeTimelineFragment.clearAll();
            homeTimelineFragment.populateTimeLine(new Long("1"));
//            aTweets.clear();
//            populateTimeLine(sinceId);
        }
    }

    /**
     * get LoginUser Data
     */
    public void getLoingProfile(){
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                loginUser = User.formJSON(json);
                Log.d("DEBUG", loginUser.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter{
        private String tabTitle[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           if(position == 0){
               return new HomeTimelineFragment();
           }else if(position == 1){
               return new MentionsTimelineFragment();
           }else {
               return null;
           }
        }


        @Override
        public CharSequence getPageTitle(int position) {
          return tabTitle[position];
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }

    public void onPostTwitter(MenuItem mi){
            Intent i = new Intent(TimelineActivity.this, PostTwitterActivity.class);
            i.putExtra("userProfile", loginUser);
            startActivityForResult(i, REQUEST_CODE);
    }

    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name", loginUser.getScreenName());
        i.putExtra("id", loginUser.getUid());
        startActivity(i);
    }

}
