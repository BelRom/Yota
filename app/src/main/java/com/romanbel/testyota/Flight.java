package com.romanbel.testyota;

/**
 * Created by roman on 23.01.18.
 */

public class Flight {

    private String details;
    private String rocketName;
    private int launchDateUnix;
    private String missionPatch;
    private String articleLink;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRocketName() {
        return rocketName;
    }

    public void setRocketName(String rocketName) {
        this.rocketName = rocketName;
    }

    public int getLaunchDateUnix() {
        return launchDateUnix;
    }

    public void setLaunchDateUnix(int launchDateUnix) {
        this.launchDateUnix = launchDateUnix;
    }

    public String getMissionPatch() {
        return missionPatch;
    }

    public void setMissionPatch(String missionPatch) {
        this.missionPatch = missionPatch;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }
}
