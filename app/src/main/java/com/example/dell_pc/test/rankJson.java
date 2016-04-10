package com.example.dell_pc.test;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sword on 2016/4/10 0010.
 */
public class rankJson {

    /**
     * success : 1
     * message : Score successfully update.
     * 0 : {"topUsername":"qazwsx","topScore":"0"}
     * 1 : {"topUsername":"li si","topScore":"0"}
     * 2 : {"topUsername":"madan","topScore":"0"}
     * myRank : 0
     */


    private String message;
    @SerializedName("0")
    private three first;
    @SerializedName("1")
    private three second;
    @SerializedName("2")
    private three third;
    private int success;
    private String myRank;

    public class three{
        String topUsername;
        String topScore;

        public String getTopScore() {
            return topScore;
        }

        public String getTopUsername() {
            return topUsername;
        }
    }

    public three getFirst() {
        return first;
    }

    public String getMessage() {
        return message;
    }

    public String getMyRank() {
        return myRank;
    }

    public three getSecond() {

        return second;
    }

    public int getSuccess() {
        return success;
    }

    public three getThird() {
        return third;
    }
}

