package com.codepath.apps.runningsimpletwitters;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.runningsimpletwitters.R;
import com.codepath.apps.runningsimpletwitters.models.LoginUser;
import com.codepath.apps.runningsimpletwitters.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PostTwitterActivity extends ActionBarActivity {
    private ImageView ivProfileImage;
    private TextView tvName;
    private EditText edContent;
    private TextView remaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_twitter);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setupView();
        LoginUser loginUser = (LoginUser) getIntent().getSerializableExtra("userProfile");
        Picasso.with(this).load(loginUser.getProfileImageUrl()).into(ivProfileImage);
        tvName.setText(loginUser.getName());
    }

    private void setupView(){
        ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        tvName = (TextView)findViewById(R.id.tvName);
        edContent = (EditText)findViewById(R.id.edContent);
        remaining = (TextView)findViewById(R.id.remaining);
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                int count = editable.toString().length();
                if(count > 0){
                    int rem = 140 - count;
                    if(rem >= 0){
                        remaining.setText(String.valueOf(rem));
                    }else {
                        remaining.setText(String.valueOf(0));
                    }

                }
            }
        });


    }

    public void cancelAction(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void newPostToTwitter(View view){
        TwitterClient client = TwitterApplication.getRestClient();
        client.postNewTwitter(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
                // Log.d("DEBUG", errorResponse.toString());
            }
        }, edContent.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_twitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
