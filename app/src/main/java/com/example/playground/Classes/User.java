package com.example.playground.Classes;

import java.util.List;
import java.util.Map;

public class User {

    private String id;
    private String imageURL;
    private String username;
    private String status;
    private List<String> gamesPlaying;
    private Map<String, Object> friends;

    public User(String id, String imageURL, String userName, String status,
                Map<String, Object> friends, List<String> gamesPlaying) {
        this.id = id;
        this.imageURL = imageURL;
        this.username = userName;
        this.status = status;
        this.friends = friends;
        this.gamesPlaying = gamesPlaying;
    }

    public User() {}

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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Map<String, Object> getFriends() {
        return friends;
    }

    public List<String> getGamesPlaying() { return gamesPlaying; }

    public void setGamesPlaying(List<String> gamesPlaying) { this.gamesPlaying = gamesPlaying; }

    public void setFriends(Map<String, Object> friends) {
        this.friends = friends;
    }

    public void addFriend(String friendUid, String friendName) {
        if (!friends.containsKey(friendUid))
            friends.put(friendUid, friendName);
    }


}
