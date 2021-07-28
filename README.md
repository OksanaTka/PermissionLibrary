# PermissionLibrary [![](https://jitpack.io/v/OksanaTka/PermissionLibrary.svg)](https://jitpack.io/#OksanaTka/PermissionLibrary)

**Android Library for managing runtime permission.**



Screenshots
-----------
![](app_gif.gif)


Library permissions:
-----------
```java
android.permission.ACCESS_COARSE_LOCATION
android.permission.ACCESS_FINE_LOCATION
android.permission.ACCESS_BACKGROUND_LOCATION
android.permission.CAMERA
android.permission.READ_CONTACTS
android.permission.RECORD_AUDIO
android.permission.READ_CALENDAR
android.permission.READ_SMS
```


Add library to your project:
-----------

### Dependency
Add it in your root ``build.gradle`` at the end of repositories: 
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency:  
```groovy
dependencies{
	implementation 'com.github.OksanaTka:PermissionLibrary:0.1.0'
}
```

### Add permissions to Manifest:
```java
  <uses-permission android:name="android.permission.CAMERA"/>
```


### Implementation in Activity:
Create ``Permission_Manager`` object:
```java
private Permission_Manager permission_manager;
```

Initialize ``Permission_Manager``:
```java
  permission_manager = new Permission_Manager(this, MainActivity.this,requestPermissionLauncher,manuallyPermissionResultLauncher);
```

Add your permissions to permission`s list:  

* to add single permission:
```java
  permission_manager.addPermissionToList(All_Permissions.CAMERA);
```
* to add all permissions:
```java
  permission_manager.setPermissionsList(All_Permissions.ALL_PERMISSIONS);
```

Add Request Permission:
```java
  private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
      new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
            for (Map.Entry<String, Boolean> x : isGranted.entrySet()) {
                permission_manager.checkPermission(x);
            }
   });
```

Add Activity Result:
```java
  private ActivityResultLauncher<Intent> manuallyPermissionResultLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
              @Override
              public void onActivityResult(ActivityResult result) {
              }
   });
```
