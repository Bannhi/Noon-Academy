package com.noonacademy.model.dbhandler;

import com.noonacademy.model.Subject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bannhi on 24/2/18.
 */

public class SubjectsDbHandler {

    Subject subject;
    static SubjectsDbHandler newInstance = new SubjectsDbHandler();

    public static SubjectsDbHandler getInstance() {
        if (newInstance == null)
            newInstance = new SubjectsDbHandler();
        return newInstance;
    }

    private SubjectsDbHandler() {

    }

    public boolean addSubject(String name,String desc, String imgUrl,String imgURI){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Subject subject = new Subject();
        subject.setSubjectDescription(desc);
        subject.setSubjectName(name);
        subject.setSubjectIconUrl(imgUrl);
        subject.setImgUri(imgURI);
        Number id = realm
                .where(Subject.class)
                .max("id");
        int nextId = (id == null) ? 1 : id.intValue() + 1;
        subject.setId(nextId);
        Realm.getDefaultInstance().copyToRealmOrUpdate(subject);
        realm.commitTransaction();
        return true;

    }
    public RealmResults<Subject> getSubjects(Realm realm){
        RealmResults<Subject> subjects = realm.where(Subject.class)
                .findAll();
        return subjects;
    }
    public void deleteSubject(final Subject subject,Realm realm){
        Subject result = realm.where(Subject.class).
                equalTo("id", subject.getId()).findFirst();
        if (result.isValid()) {
            result.deleteFromRealm();
        }
    }

}
