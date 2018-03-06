package com.noonacademy.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by bannhi on 24/2/18.
 */

public class Subject extends RealmObject{



    @PrimaryKey
    private String subjectName;
    private int id;
    private String imgUri;
    private String subjectIconUrl;
    private String subjectDescription;

    public Subject(){


    }
    public Subject(String subjectName,String subjectDescription){
        this.subjectDescription = subjectDescription;
        this.subjectName = subjectName;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectIconUrl() {
        return subjectIconUrl;
    }

    public void setSubjectIconUrl(String subjectIconUrl) {
        this.subjectIconUrl = subjectIconUrl;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }
}
