package com.example.priyanka.mytwitter;

import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.AuthHandler;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import org.xml.sax.HandlerBase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;

import static com.twitter.sdk.android.core.internal.oauth.OAuth1aHeaders.HEADER_AUTH_CREDENTIALS;

public class TimeLine extends AppCompatActivity {
    Map<String, String> authHeaders;
    SessionDetails sessionDetails;
    String header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        TwitterAuthConfig twitterAuthConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken twitterAuthToken = twitterSession.getAuthToken();
        sessionDetails = new SessionDetails(twitterAuthToken.token, twitterAuthToken.secret,
                twitterSession.getUserName(), twitterSession.getUserId());

        OAuthSigning oAuthSigning = new OAuthSigning(twitterAuthConfig, twitterAuthToken);

        authHeaders = oAuthSigning.getOAuthEchoHeadersForVerifyCredentials(); // --------
        Iterator it = authHeaders.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            Log.i("authheader", pair.getKey() + " = " + pair.getValue());
        }
        try {
            header = new GetHeader("https://api.twitter.com/1.1/search/tweets.json","GET").header;
            Log.i("header",header);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        fetchMyTweets();
//        fetchMyTweets();
//        fetchWhoToFollow90;
    }

    private void fetchMyTweets() {

//        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//        StatusesService statusesSgit ervice = twitterApiClient.getStatusesService();
//        Call<Tweet> call = statusesService.show(524971209851543553L, null, null, null);
//        call.enqueue(new Callback<Tweet>() {
//                         @Override
//                         public void onResponse(Call<Tweet> call, Response<Tweet> response) {
//                             response.body().
//                         }
//
//                         @Override
//                         public void onFailure(Call<Tweet> call, Throwable t) {
//
//                         }
//                     });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<ArrayList<String>> call1= apiInterface.getUserTimeline(header);
//        call1.enqueue(new Callback<ArrayList<String>>() {
//            @Override
//            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
//                if(response.body()==null){
//                    Log.i("TIMEmp", "FUCKOFF");
//                    return;
//                }
//                for (int i = 0; i < response.body().size(); i++) {
//                    ArrayList<String> list = response.body();
//                    Log.i("TIMEmp", list.get(i) + "\n");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
//                Log.i("TIMEmp", "FAILED");
//            }
//        });

        Call<Trends> call = apiInterface.GetTrends(header,50); //authHeaders.get(HEADER_AUTH_CREDENTIALS)
        Log.i("FinalURL",call.request().url().toString()+"  "+call.request().header("Authorization"));
        call.enqueue(new Callback<Trends>() {
            @Override
            public void onResponse(Call<Trends> call, Response<Trends> response) {
                if(response.errorBody()!=null){
                    try {
                        Log.i("errorBody", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(response.body()==null){
                    Log.i("TRENDS", "GETLOST");
                    return;
                }
                for (int i = 0; i < response.body().getStatuses().getData().size(); i++) {
                    Log.i("TRENDS", response.body().getStatuses().getData().get(i).getText().toString() + "\n");
                }
            }
            @Override
            public void onFailure(Call<Trends> call, Throwable t) {
                    Log.i("TRENDS","FAIL");
//                }
            }
        });
//        call.enqueue(new Callback<Trends>() {
//            @Override
//            public void onResponse(Call<Trends> call, Response<Trends> response) {
//                if(response.body()==null){
//                    Log.i("TRENDS", "FUCKOFF");
//                    return;
//                }
//                for (int i = 0; i < response.body().size(); i++) {
//                    Trends list = response.body();
//                    Log.i("TRENDS", list.get(i).getStat().text.toString() + "\n");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Trends>> call, Throwable t) {
//                Log.i("TRENDS", "FAILED");
//            }
//        });
//            @Override
//            public void onFailure(Call<ArrayList<Trends>> call, Throwable t) {
//                Log.i("fetchb","incorrect");
//            }
//        });
//    }


//    private void fetchRecentTweets() {
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://api.twitter.com/1.1/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
//        Call<ArrayList<Trends> > call=apiInterface.GetTrends();
//        call.enqueue(new Callback<ArrayList<Trends>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Trends>> call, Response<ArrayList<Trends>> response) {
//                if(response.body()==null)return;
//                for(int i=0;i<response.body().size();i++){
//                    Log.i("fetchb",response.body().get(i)+"");
//                }
//            }
//            @Override
//            public void onFailure(Call<ArrayList<Trends>> call, Throwable t) {
//                Log.i("fetchb","incorrect");
//            }
//        });
//    }

    }
}