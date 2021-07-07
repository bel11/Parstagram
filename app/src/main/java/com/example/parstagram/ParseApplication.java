package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3rhML70pY2fGj6EJWjHXGpswMxDAYYbpXFKz5319")
                .clientKey("4EXdRr0goug35sa7QBD27qDjMKwXBOEr0TwS7vYB")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
