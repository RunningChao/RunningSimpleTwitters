package com.codepath.apps.runningsimpletwitters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.runningsimpletwitters.models.Tweet;
import com.codepath.apps.runningsimpletwitters.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by running on 8/9/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets){
        super(context, android.R.layout.simple_list_item_1, tweets);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        ivProfileImage.setOnClickListener(null);
        ivProfileImage.setOnClickListener(new clickListener(tweet));
        TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvRelativeTime = (TextView)convertView.findViewById(R.id.tvRelativeTime);

        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvRelativeTime.setText(TimeUtils.getRelativeTimeAgo(tweet.getCreateAt()));
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        return convertView;
    }

    public class clickListener implements View.OnClickListener{
        private Tweet tweet;

        public clickListener(Tweet tweet){
            this.tweet = tweet;
        }

        @Override
        public void onClick(View v) {
            TwitterClient client = TwitterApplication.getRestClient();
            Intent i = new Intent(v.getContext(), ProfileActivity.class);
            i.putExtra("screen_name", tweet.getUser().getScreenName());
            i.putExtra("id", tweet.getUser().getUid());
            v.getContext().startActivity(i);
        }
    }
}
