package com.example.priyanka.mytwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TwitterLoginButton twitterLoginButton;
    SessionDetails sessionDetails;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Twitter.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twitterLoginButton=(TwitterLoginButton) findViewById(R.id.login_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        sessionDetails= new SessionDetails(null,null,null,0);
        sharedPreferences=getSharedPreferences("sessionDetail",MODE_PRIVATE);

        sessionDetails.token=sharedPreferences.getString("token",null);
        if(sessionDetails.token!=null)   // TO RETRIEVE VALUES FROM sharedPreferences
        {
            sessionDetails.secret=sharedPreferences.getString("secret",null);
            sessionDetails.user_id=sharedPreferences.getLong("user_id",0);
            sessionDetails.user_name=sharedPreferences.getString("username",null);
            setActiveSession();
        }
        saveInSharedPreference();

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Intent i=new Intent(MainActivity.this,TimeLine.class);
                startActivity(i);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.i("onFailure","onFailure");
            }
        });


//        FAB OF NO USE RIGHT NOW
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "LOGIN TO CONTINUE", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void setActiveSession() {
        TwitterAuthToken twitterAuthToken=new TwitterAuthToken(sessionDetails.token,sessionDetails.secret);
        TwitterSession twitterSession=new TwitterSession(twitterAuthToken,sessionDetails.user_id,sessionDetails.user_name);
        TwitterCore.getInstance().getSessionManager().setActiveSession(twitterSession);
    }

    private void saveInSharedPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", sessionDetails.token);
        editor.putString("secret", sessionDetails.secret);
        editor.putLong("user_id",sessionDetails.user_id);
        editor.putString("username",sessionDetails.user_name);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode,resultCode,data);
    }


}
