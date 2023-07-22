package com.example.realmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.realmdemo.realm.RealmManager;
import com.example.realmdemo.realmPojo.Person;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmManager<Person> realmPerson = RealmManager.getInstance(this, Person.class);

        /* [C] Creating object in Realm */
        Person oldPerson = new Person("LeoMe",90);
        realmPerson.write(oldPerson);

        /* [U] Updating object in Realm*/
        realmPerson.update("name", "LeoMes", new Person("LeoMes",123));

        /* [R] Reading object from Realm */
        Person person = realmPerson.read("name", "LeoMe");
        if (person != null) Log.i("TAG",person.getAge()+"");

        /* [D] Deleting object from Realm */
        realmPerson.delete("name", "LeoMe");
        if (person == null) Log.i("TAG","Deletion");

    }
}
