package com.example.priyanka.mytwitter;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;

/**
 * Created by PRIYANKA on 24-07-2017.
 */

public class GetHeader {

    String URL;
    String method;
    public String header;

    public GetHeader(String URL,String method) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        OAuthHeaderClass oAuthHeaderClass=new OAuthHeaderClass();
        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = twitterSession.getAuthToken();

        this.URL=URL;
        this.method=method;
        oAuthHeaderClass.setOauth_signature_method("HMAC-SHA1");
        oAuthHeaderClass.setMethod(method);
        oAuthHeaderClass.setOauth_consumer_key("r5tYYhpcjTn7BHkpv2Lz5F69m");
        oAuthHeaderClass.setOauth_token("886093060517449728-Fe0hjxypBgpgrzQOyuq0WcVbyGYRFuw");
        oAuthHeaderClass.setConsumersecret("u3W1X7TQq2pfygMtx8DudwkrTUNEIGrNODHUAL5Iw37XwJit5q");
        oAuthHeaderClass.setTokenSecret("RAtNY2JHoejNqzhNmWot4YRpr7RNNaCchCl4AUBWrRGca");
        oAuthHeaderClass.setOauth_version("1.0");
        oAuthHeaderClass.setOauth_timestamp(System.currentTimeMillis()/1000+"");
        oAuthHeaderClass.setOauth_nonce(nounce_random_string());
        oAuthHeaderClass.setBaseURL(URL);
        oAuthHeaderClass.setOauth_signature();
        header=oAuthHeaderClass.getAuthHeader();
    }

    String nounce_random_string(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
