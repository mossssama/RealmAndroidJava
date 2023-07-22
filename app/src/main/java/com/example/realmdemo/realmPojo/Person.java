package com.example.realmdemo.realmPojo;

import com.example.realmdemo.realm.RealmObjectCopyable;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject implements RealmObjectCopyable<Person> {
    @PrimaryKey
    private String id;

    private String name;
    private int age;

    public Person(String name, int age) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.name = name;
        this.age = age;
    }

    public Person() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void copyToRealmObject(Person destination) {
        destination.setName(this.getName());
        destination.setAge(this.getAge());
    }
}
