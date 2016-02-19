# Android SAML Template
---------
Author: Daniel Passos (dpassos@redhat.com, daniel@passos.me)     
Level: Intermediate  
Technologies: Java, Android, RHMAP, SAML  
Summary: A demonstration of how to integrate with the [FeedHenry SAML PoC](https://github.com/feedhenry-templates/saml-cloud-app)  
Community Project : [Feed Henry](http://feedhenry.org/)  
Target Product: RedHat Mobile Application Platform aka RHMAP    
Product Versions: RHMAP 3.8.0+   
Source: https://github.com/feedhenry-templates/pushstarter-android-app  
Prerequisites: fh-android-sdk : 3.0.+, Android Studio : 1.4.0 or newer, Android SDK : 22+ or newer

## What is it?

This application will subscribe to a push service running in a RHMAP instance. The user can send messages to the device using RHMAP and view them on the device.  

If you do not have access to a RHMAP instance, you can sign up for a free instance at [https://openshift.feedhenry.com/](https://openshift.feedhenry.com/).

## How do I run it?  

### RHMAP Studio

You can import this project as a client app inside of RHMAP Studio.  First you will need to create a project based on the  "SAML Example Project" template.  In this project you will add a new client app by importing an existing app.  Select "Native Android" as your app type and import from this git repository. 

### Build instructions for Open Source Development
If you wish to contribute to this template, the following information may be helpful; otherwise, RHMAP and its build facilities are the preferred solution.

## Build instructions
 * Edit [fhconfig.properties](app/src/main/assets/fhconfig.properties) to include the relevant information from RHMAP.  
 * Attach running Android Device with API 16+ running  
 * ./gradlew installDebug  
 
## How does it work?

This project will use the device's native ID and negotiate a SAML session with the remote server.  The SAML related logic can be viewed in [SAMLActivity](app/src/main/java/org/feedhenry/saml/SAMLActivity.java).

### Logging in With SAML

In order to log in with SAML, the application retrieve's a SSO URL from the cloud application.  This URL is loaded in a webview which negotiates the SAML session between the IdP and the cloud application.

The relevant block of code may be found in [SAMLActivity](app/src/main/java/org/feedhenry/saml/SAMLActivity.java)
```java
public void retrieveSSOUrl() {

    displayLoading();

    try {

        JSONObject params = new JSONObject();
        params.put(TOKEN, Device.getDeviceId(getApplicationContext()));

        FHCloudRequest request = FH.buildCloudRequest(
                "sso/session/login_host", "POST", null, params);
        request.executeAsync(new FHActCallback() {
            @Override
            public void success(FHResponse res) {
                Log.d(TAG, "FHCloudRequest (login_host) - success");

                String ssoStringURL = res.getJson().getString("sso");
                Log.d(TAG, "SSO URL = " + ssoStringURL);
                SAMLActivity.this.displayWebView(ssoStringURL);
            }

            @Override
            public void fail(FHResponse res) {
                Log.d(TAG, "FHCloudRequest (login_host) - fail");
                Log.e(TAG, res.getErrorMessage(), res.getError());
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }

}

```
### Loading User Details

After the user is logged in with SAML, the SAML identity will be associated with the device's ID.  In the cloud app the "sso/session/valid" endpoint returns the user information of the current logged in user.

```java
public void retrieveUserData() {

    displayLoading();

    try {

        JSONObject params = new JSONObject();
        params.put(TOKEN, Device.getDeviceId(getApplicationContext()));

        FHCloudRequest request = FH.buildCloudRequest(
                "sso/session/valid", "POST", null, params);
        request.executeAsync(new FHActCallback() {
            @Override
            public void success(FHResponse res) {
                Log.d(TAG, "FHCloudRequest (valid) - success");

                User user = new User();
                user.setFirstName(res.getJson().getString("first_name"));
                user.setLastName(res.getJson().getString("last_name"));
                user.setEmail(res.getJson().getString("email"));
                user.setExpires(res.getJson().getString("expires"));

                Log.d(TAG, user.toString());

                SAMLActivity.this.displayUserData(user);
            }

            @Override
            public void fail(FHResponse res) {
                Log.d(TAG, "FHCloudRequest (valid) - fail");
                Log.e(TAG, res.getErrorMessage(), res.getError());
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }

}
```