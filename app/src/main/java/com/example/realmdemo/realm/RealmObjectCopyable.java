package com.example.realmdemo.realm;

import io.realm.RealmObject;

public interface RealmObjectCopyable<T extends RealmObject> {
    void copyToRealmObject(T destination);
}