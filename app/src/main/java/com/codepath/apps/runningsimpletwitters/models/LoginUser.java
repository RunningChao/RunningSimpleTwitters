package com.codepath.apps.runningsimpletwitters.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by running on 8/10/15.
 */
public class LoginUser implements Serializable{
    private String name;
    private String profileImageUrl;

    public LoginUser(){}

    public LoginUser(JSONObject json){
        try{
            this.name = json.getString("name");
            this.profileImageUrl = json.getString("profile_image_url");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public String toString(){
        return "name : " + name + " ; profileImageUrl : " + this.profileImageUrl;
    }


}
