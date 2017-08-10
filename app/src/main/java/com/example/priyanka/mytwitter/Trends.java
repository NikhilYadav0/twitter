package com.example.priyanka.mytwitter;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by PRIYANKA on 21-07-2017.
 */

public class Trends {
    public Trends(statuses statuses) {
        this.statuses=statuses;
    }

    statuses statuses;

    public Trends.statuses getStatuses() {
        return statuses;
    }

    public void setStatuses(Trends.statuses statuses) {
        this.statuses = statuses;
    }

    public static class statuses
    {
        ArrayList<data> data;
        public static class data
        {
            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public data(String text) {
                this.text = text;
                Log.i("itsHere","itsHere");
            }
            String text;
        };

        public ArrayList<statuses.data> getData() {
            return data;
        }

        public void setData(ArrayList<statuses.data> data) {
            this.data = data;
        }
    };
}
