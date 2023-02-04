package com.example.playground.Classes;

import java.util.ArrayList;
import java.util.Map;

public class User {

    private String id;
    private String imageURL;
    private String username;
    private Map<String, String> friends;

    public User(String id, String imageURL, String userName, Map<String, String> friends) {
        this.id = id;
        this.imageURL = imageURL;
        this.username = userName;
        this.friends = friends;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

//    public ArrayList<String> getFriends() { return friends; }

//    public void setFriends(ArrayList<String> friends) { this.friends = friends; }

    public boolean areFriends(String otherUid) {
        if (friends.containsKey(otherUid)) {
            return true;
        }
        return false;
    }

    public Map<String, String> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, String> friends) {
        this.friends = friends;
    }

    public void addFriend(String friendUid, String friendName) {
        if (!friends.containsKey(friendUid))
            friends.put(friendUid, friendName);
    }
}
