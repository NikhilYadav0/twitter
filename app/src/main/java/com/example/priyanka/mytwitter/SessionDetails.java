package com.example.priyanka.mytwitter;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by PRIYANKA on 22-07-2017.
 */

public class SessionDetails implements Serializable {
    String token;
    String secret;
    String user_name;
    long user_id;

    public SessionDetails(String token, String secret, String user_name, long user_id) {
        this.token = token;
        this.secret = secret;
        this.user_name = user_name;
        this.user_id = user_id;
    }
}
