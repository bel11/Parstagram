package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3rhML70pY2fGj6EJWjHXGpswMxDAYYbpXFKz5319")
                .clientKey("4EXdRr0goug35sa7QBD27qDjMKwXBOEr0TwS7vYB")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
