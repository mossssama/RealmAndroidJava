package com.example.realmdemo.realm;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmManager<T extends RealmObject & RealmObjectCopyable<T>> {
    private static final Object LOCK = new Object();
    private static RealmManager<?> instance;
    private final Realm realm;

    private RealmManager(Context context, Class<T> realmClass) {
        Realm.init(context);                                                                                                        // Initialize Realm in the constructor
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().name("myrealm.realm").schemaVersion(2).build();           // Create a Realm configuration
        Realm.setDefaultConfiguration(realmConfig);                                                                                 // Set the default Realm configuration
        realm = Realm.getDefaultInstance();                                                                                         // Get the Realm instance
    }

    public static <T extends RealmObject & RealmObjectCopyable<T>> RealmManager<T> getInstance(Context context, Class<T> realmClass) {
        synchronized (LOCK) {
            if (instance == null) instance = new RealmManager<>(context.getApplicationContext(), realmClass);
            else if (!instance.getRealmClass().equals(realmClass)) throw new IllegalStateException("RealmManager instance is already used with a different RealmObject class.");                 // Check if the provided realmClass matches the existing instance's realmClass To ensure the correct RealmManager is returned for each specific RealmObject type.
        }
        return (RealmManager<T>) instance;
    }

    public Realm getRealm() {
        return realm;
    }

    // Additional method to retrieve the class type of the RealmObject used with this manager
    public Class<T> getRealmClass() {
        return (Class<T>) realm.getConfiguration().getRealmObjectClasses().iterator().next();
    }

    public void executeTransactionAsync(Realm.Transaction transaction) {
        new Thread(() -> {
            Realm backgroundRealm = null;
            try {
                backgroundRealm = Realm.getDefaultInstance(); // Obtain a new Realm instance on the background thread
                backgroundRealm.executeTransaction(transaction); // Perform the transaction
            } finally {
                if (backgroundRealm != null) {
                    backgroundRealm.close(); // Close the Realm instance when the transaction is completed
                }
            }
        }).start();
    }


    // Execute the write transaction asynchronously on a background thread

    /* Create */
    public void write(T object) {
        executeTransactionAsync(realm -> realm.copyToRealm(object));
    }

    /* Read */
    public T read(String fieldName,String fieldValue) {
        Realm realm = getRealm();
        return realm.where(getRealmClass()).equalTo(fieldName, fieldValue).findFirst();
    }

    /* Update */
    public void update(String fieldName, String fieldValue, T objectToUpdate) {
        realm.executeTransactionAsync(realm -> {
            T object = realm.where(getRealmClass()).equalTo(fieldName, fieldValue).findFirst();
            if (object != null) {
                objectToUpdate.copyToRealmObject(object);
            }
        });
    }

    /* Delete */
    public void delete(String fieldName, String fieldValue) {
        realm.executeTransactionAsync(realm -> {
            RealmResults<T> results = realm.where(getRealmClass()).equalTo(fieldName, fieldValue).findAll();
            results.deleteAllFromRealm();
        });
    }

    public void clearRealm() {
        realm.executeTransactionAsync(realm -> realm.delete(getRealmClass()));
    }

}
