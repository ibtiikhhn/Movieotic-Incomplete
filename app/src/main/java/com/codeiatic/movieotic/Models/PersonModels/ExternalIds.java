package com.codeiatic.movieotic.Models.PersonModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds{

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("freebase_mid")
    @Expose
    private String freebaseMid;
    @SerializedName("freebase_id")
    @Expose
    private String freebaseId;
    @SerializedName("tvrage_id")
    @Expose
    private Integer tvrageId;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instagram_id")
    @Expose
    private String instagramId;

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFreebaseMid() {
        return freebaseMid;
    }

    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    public String getFreebaseId() {
        return freebaseId;
    }

    public void setFreebaseId(String freebaseId) {
        this.freebaseId = freebaseId;
    }

    public Integer getTvrageId() {
        return tvrageId;
    }

    public void setTvrageId(Integer tvrageId) {
        this.tvrageId = tvrageId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
