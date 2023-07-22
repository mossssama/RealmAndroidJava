# RealmAndroidJava
Simple Documentation for Realm in AndroidJava along with simple demo

# **Usage**
***
[1] **Add the following to the top of build.gradle (Project)**


    buildscript {
        repositories {
            mavenCentral()
        }
        dependencies {
            classpath "io.realm:realm-gradle-plugin:10.11.1"
        }
    }
***

[2] **Add the following after plugins in build.gradle (Module)**


    apply plugin: "realm-android" 
***

[3] **Add the following to the android{ } in build.gradle (Module)**


    realm {
        syncEnabled = true 
    }
***

[4] **Put [RealmManager class](https://github.com/mossssama/RealmAndroidJava/blob/main/app/src/main/java/com/example/realmdemo/realm/RealmManager.java)**
 & **[RealmObjectCopyable interface](https://github.com/mossssama/RealmAndroidJava/blob/main/app/src/main/java/com/example/realmdemo/realm/RealmObjectCopyable.java) in your app folder**

***

[5] **Create Pojo class to represent an object in Realm like the following [Person class](https://github.com/mossssama/RealmAndroidJava/blob/main/app/src/main/java/com/example/realmdemo/realmPojo/Person.java) in your app folder**

***
[6] **Use the following in the place you want to use Realm**; substitute the i/p parameters by their equivalent in your project

    /* Create an instance for every Object*/
    RealmManager<Person> realmPerson = RealmManager.getInstance(this, Person.class);
    // RealmManager<Book> realmBook = RealmManager.getInstance(this, Book.class);                  & so on
  
    /* [C] Creating object in Realm */
    realmPerson.write(new Person("LeoMessi",90));

    /* [U] Updating object in Realm*/
    realmPerson.update("name", "LeoMessi", new Person("LeoMessi",123));

    /* [R] Reading object from Realm */
    Person person = realmPerson.read("name", "LeoMessi");
    if (person != null) Log.i("TAG",person.getAge()+"");

    /* [D] Deleting object from Realm */
    realmPerson.delete("name", "LeoMe");
    if (person == null) Log.i("TAG","Deletion");
- - - - 
