package com.example.githubutilizer;

public class RepoDataModel {
    String profileUrl;
    String username;
    String repoLink;

    public String getProfileUrl() {
        return profileUrl;
    }
    public String getRepoLink(){
        return repoLink;
    }


    public String getUsername() {
        return username;
    }



    public String getRepoDesc() {
        return repoDesc;
    }



    String repoDesc;

    public RepoDataModel(String profileUrl, String username, String repoDesc,String repoLink) {
        this.profileUrl = profileUrl;
        this.username = username;
        this.repoDesc = repoDesc;
        this.repoLink = repoLink;
    }

}
