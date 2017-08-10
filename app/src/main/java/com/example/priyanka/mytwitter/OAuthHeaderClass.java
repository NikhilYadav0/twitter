package com.example.priyanka.mytwitter;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by PRIYANKA on 24-07-2017.
 */

// https://dev.twitter.com/oauth/overview/authorizing-requests imp. link

public class OAuthHeaderClass {
    // ALl the seven key value pairs string
    //HMAC-SHA1 signature...
    static String oauth_consumer_key;
    static String oauth_nonce;
    static  String oauth_signature;
    static  String oauth_signature_method;
    static  String oauth_timestamp;
    static  String oauth_token;
    static  String oauth_version;
    static  String consumersecret;
    static   String authHeader ;
    static  String method;
    static  String baseURL;
    static  String parameterString;
    static   String tokenSecret;
    public static String getAuthHeader() throws UnsupportedEncodingException {
        authHeader="OAuth "+URLEncoder.encode("oauth_consumer_key","UTF-8")+"=\""+URLEncoder.encode(oauth_consumer_key,"UTF-8")+"\", "
                +URLEncoder.encode("oauth_nonce","UTF-8")+"=\""+URLEncoder.encode(oauth_nonce,"UTF-8")+"\", "
                +URLEncoder.encode("oauth_signature","UTF-8")+"=\""+URLEncoder.encode(oauth_signature,"UTF-8")+"\", "
                +URLEncoder.encode("oauth_signature_method","UTF-8")+"=\""+URLEncoder.encode(oauth_signature_method,"UTF-8")+"\", "
                +URLEncoder.encode("oauth_timestamp","UTF-8")+"=\""+URLEncoder.encode(oauth_timestamp,"UTF-8")+"\", "
                +URLEncoder.encode("oauth_token","UTF-8")+"=\""+URLEncoder.encode(oauth_token,"UTF-8")+"\", "
                +URLEncoder.encode("oauth_version","UTF-8")+"=\""+URLEncoder.encode(oauth_version,"UTF-8")+"\"";
        Log.i("oauth_signature",oauth_signature);
        return authHeader;
    }

     public String getOauth_consumer_key() {
        return oauth_consumer_key;
    }
     public String getOauth_nonce() {
        return oauth_nonce;
    }
     public void setMethod(String method) {
        this.method=method;
    }
     public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseString(String method, String baseURL, String parameterString) throws UnsupportedEncodingException {
        String baseString=null;
        baseString=method+"&"+ URLEncoder.encode(baseURL,"UTF-8")+"&"+URLEncoder.encode(parameterString,"UTF-8");
        Log.i("baseString",baseString);
        return baseString;
    }

    public void setParameterString(String parameterString) throws UnsupportedEncodingException {
        parameterString="include_entities=true&oauth_consumer_key="+getOauth_consumer_key()+"&oauth_nonce="
                +getOauth_nonce()+"&oauth_signature_method=HMAC-SHA1&oauth_timestamp="
                +getOauth_timestamp()+"&oauth_token="+getOauth_token()+"&oauth_version="
                +getOauth_version()+"&q=50"; //---------------------------------------------------------------------//
        Log.i("parameterString",parameterString);
        this.parameterString = parameterString;
    }


    public void setOauth_signature() throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        setParameterString(parameterString);
        String baseString=getBaseString(method,baseURL,parameterString);
        String signingKey=getSignatreKey();
        this.oauth_signature=sign(baseString,signingKey);
        Log.i("HEY1: ",oauth_signature);
    }

    // TO FIND OATH_SIGNATURE---------------------
    public static String sign(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        return Base64.encodeToString(mac.doFinal(data.getBytes()),Base64.DEFAULT);
    }

    public String getOauth_timestamp() {
        return oauth_timestamp;
    }
    public String getOauth_token() {
        return oauth_token;
    }

    private String getSignatreKey() throws UnsupportedEncodingException {
        String answer=URLEncoder.encode(consumersecret,"UTF-8")+"&"+URLEncoder.encode(tokenSecret,"UTF-8");
        Log.i("answer",answer);
        return answer;
    }

    public String getOauth_version() {
        return oauth_version;
    }


    public void setOauth_signature_method(String oauth_signature_method) {
        this.oauth_signature_method = oauth_signature_method;
    }

    public  void setTokenSecret(String tokenSecret) {
        OAuthHeaderClass.tokenSecret = tokenSecret;
    }
    public String getConsumersecret() {
        return consumersecret;
    }

    public void setOauth_consumer_key(String oauth_consumer_key) {this.oauth_consumer_key = oauth_consumer_key;}
    public void setOauth_nonce(String oauth_nonce) {this.oauth_nonce = oauth_nonce;}
    public void setOauth_timestamp(String oauth_timestamp) {this.oauth_timestamp = oauth_timestamp;}
    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }
    public void setOauth_version(String oauth_version) {
        this.oauth_version = oauth_version;
    }
    public void setConsumersecret(String consumersecret) {
        this.consumersecret = consumersecret;
    }
    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }
}
